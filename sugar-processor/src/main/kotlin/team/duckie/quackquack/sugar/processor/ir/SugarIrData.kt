/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.processor.ir

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import org.jetbrains.kotlin.builtins.isFunctionOrSuspendFunctionType
import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.descriptors.toIrBasedKotlinType
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.isMarkedNullable
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.ir.util.isFunction
import org.jetbrains.kotlin.ir.util.isSuspendFunction
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext.getClassFqNameUnsafe
import org.jetbrains.kotlin.utils.addToStdlib.applyIf
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.sugar.material.Imports
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.util.backend.kotlinc.unsafeClassName

/**
 * [SugarIrVisitor]에서 IR을 방문하면서 수집할 정보들을 관리합니다.
 *
 * @param owner IR이 제공된 함수
 * @param referFqn IR이 제공된 함수의 [fully-qualified name][FqName]. [owner]에서 직접
 * 가져오는 방식보다 안전한 방식으로 fqn이 제공됩니다.
 * @param kdocGetter IR이 제공된 함수의 Sugared-KDoc을 계산하는 람다. 사용된 Sugar Token의
 * 리터럴을 람다 인자로 제공해야 합니다.
 * @param sugarName 생성할 sugar component의 네이밍 규칙. [`@SugarToken`][SugarName]
 * 값을 가져옵니다.
 * @param sugarToken 생성할 sugar component의 Sugar Token에 해당하는 [인자][IrValueParameter].
 * [`@SugarToken`][SugarToken]이 달린 인자를 가져옵니다.
 * @param tokenFqExpressions Sugar Token의 expression 모음. 예를 들면 다음과 같습니다.
 *
 * ```
 * package team.duckie.theme
 *
 * @JvmInline
 * value class Theme(val index: Int) {
 *   companion object {
 *     val Default = Theme(1)
 *     val Dark = Theme(2)
 *     val Light = Theme(3)
 *     val System = Theme(4)
 *   }
 * }
 *
 * // ["team.duckie.theme.Theme.Default", "team.duckie.theme.Theme.Dark", "team.duckie.theme.Theme.Light", "team.duckie.theme.Theme.System"]
 * ```
 *
 * @param parameters IR이 제공된 함수의 인자 모음. sugar component 생성에 필요한 정보만 수집합니다.
 * 자세한 수집 정보는 [SugarParameter]를 확인하세요.
 */
internal data class SugarIrData(
  val owner: IrFunction,
  val referFqn: FqName,
  val kdocGetter: (usedTokenLiteral: String) -> String,
  val sugarName: String?,
  val sugarToken: IrValueParameter,
  val tokenFqExpressions: List<String>,
  val parameters: List<SugarParameter>,
  val optins: List<IrConstructorCall>,
) {
  /** [parameters]에서 Sugar Token을 제외한 [요소][SugarParameter]만 불러옵니다. */
  val parametersWithoutToken: List<SugarParameter> = parameters.toMutableList().apply {
    removeIf(SugarParameter::isToken)
  }

  override fun toString(): String {
    return """
      |owner: ${owner.name.asString()}
      |referFqn: ${referFqn.asString()}
      |kdoc: ${kdocGetter("SugarToken")}
      |sugarName: $sugarName
      |sugarToken: ${sugarToken.name.asString()}
      |tokenExpressions: $tokenFqExpressions
      |parameters: ${parameters.joinToString("\n\n", prefix = "\n")}
      |optins: ${optins.joinToString(transform = IrConstructorCall::toFqnStringOrEmpty)}
    """.trimMargin()
  }
}

/** 주어진 어노테이션의 fqn을 조회하여 반환하고, 만약 조회에 실패했다면 공백을 반환합니다. */
internal fun IrConstructorCall.toFqnStringOrEmpty() =
  symbol.owner.parentAsClass.fqNameWhenAvailable?.asString().orEmpty()

/**
 * [IrValueParameter]에서 sugar component 생성에 필요한 정보를 관리합니다.
 *
 * @param name 인자의 이름
 * @param type 인자의 타입
 * @param isToken 인자가 [Sugar Token][SugarToken]인지 여부
 * @param isComposable 인자에 [`@Composable`][ComposableFqn] 어노테이션이 달려있는지
 * 여부
 * @param imports [type] 외에 추가로 import가 필요한 클래스의 [fully-qualified name][FqName]으로
 * 구성된 목록. 자세한 정보는 [`@Imports`][Imports] 어노테이션을 확인하세요.
 * @param casaValueLiteral 만약 인자에 [`@CasaValue`][CasaValue] 어노테이션이 달려있다면
 * [CasaValue.literal]로 제공된 값
 * @param defaultValue 인자의 기본 값
 */
internal data class SugarParameter(
  val owner: IrFunction,
  val name: Name,
  val type: IrType,
  val isToken: Boolean,
  val isComposable: Boolean,
  val imports: List<FqName>,
  val casaValueLiteral: String?,
  val defaultValue: IrExpressionBody?,
) {
  /** 제공된 정보를 [ParameterSpec]으로 변환합니다. */
  fun toParameterSpec(): ParameterSpec {
    val parameterTypedBuilder = ParameterSpec
      .builder(
        name = name.asString(),
        type = when {
          type.isFunction() || type.isSuspendFunction() -> {
            val funArguments = type.toIrBasedKotlinType().arguments

            /*
             * maintainer notes: 모든 **함수형 타입**은 `Function`으로 처리되며 이는 `IrFunction`과는
             * 다른 유형임. 코틀린의 **함수 정의**는 `IrFunction`으로 해석되고, **함수형 타입**은 코틀린
             * 네이티브 타입인 `KotlinType`으로 해석됨.
             *
             * `Function`은 value parameter의 타입과 return의 타입을 generic으로 받는 인터페이스임.
             * 즉, `Function`은 메타데이터가 없어서 컴파일 시점에서는 인자의 타입만 조회 가능함.
             * 따라서 람다의 인자명 정책으로 `P{$index}`를 사용함.
             *
             * receiver extension은 **첫 번째** value parameter로 치환됨.
             * > `String.() -> Unit` == `(String) -> Unit`
             *
             * return type은 **마지막** value parameter로 치환됨.
             */
            val referLambdaParameters =
              if (funArguments.size >= 2) {
                funArguments.dropLast(1).mapIndexed { index, argument ->
                  require(!argument.type.isFunctionOrSuspendFunctionType) {
                    NotSupportedError.nestedFunctionalType("${owner.name.asString()}#${name.asString()}")
                  }

                  val argumentTypeFqn = argument.type.constructor.getClassFqNameUnsafe()
                  val argumentTypeCn = ClassName.bestGuess(argumentTypeFqn.toString())

                  ParameterSpec
                    .builder(
                      name = "P$index",
                      type = argumentTypeCn,
                    )
                    .build()
                }
              } else {
                emptyList()
              }
            val referLambdaReturnTypeFqn = funArguments.last().type.constructor.getClassFqNameUnsafe()
            val referLambdaReturnTypeCn = ClassName.bestGuess(referLambdaReturnTypeFqn.toString())

            LambdaTypeName
              .get(
                parameters = referLambdaParameters,
                returnType = referLambdaReturnTypeCn,
              )
              .copy(suspending = type.isSuspendFunction())
          }
          else -> {
            type.unsafeClassName
          }
        }.copy(nullable = type.isMarkedNullable()),
      )

    return parameterTypedBuilder
      .applyIf(casaValueLiteral != null) {
        addAnnotation(
          AnnotationSpec
            .builder(CasaValueCn)
            .addMember("%S", casaValueLiteral!!)
            .build(),
        )
      }
      .applyIf(isComposable) {
        addAnnotation(
          AnnotationSpec
            .builder(ComposableCn)
            .build(),
        )
      }
      .applyIf(defaultValue != null) {
        defaultValue("%L()", "sugar")
      }
      .build()
  }

  override fun toString(): String {
    return """
      |owner: ${owner.name.asString()}
      |name: ${name.asString()}
      |type: ${type.asString()}
      |isToken: $isToken
      |isComposable: $isComposable
      |imports: ${imports.joinToString(transform = FqName::asString)}
      |casaValueLiteral: $casaValueLiteral
      |defaultValue: ${defaultValue?.dumpKotlinLike()}
    """.trimMargin()
  }
}
