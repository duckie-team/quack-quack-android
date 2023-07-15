/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.hosted.codegen

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import org.jetbrains.kotlin.builtins.isFunctionOrSuspendFunctionType
import org.jetbrains.kotlin.ir.descriptors.toIrBasedKotlinType
import org.jetbrains.kotlin.ir.types.isMarkedNullable
import org.jetbrains.kotlin.ir.util.isFunction
import org.jetbrains.kotlin.ir.util.isSuspendFunction
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext.getClassFqNameUnsafe
import org.jetbrains.kotlin.utils.addToStdlib.applyIf
import team.duckie.quackquack.sugar.hosted.error.NotSupportedError
import team.duckie.quackquack.sugar.hosted.names.CasaValueCn
import team.duckie.quackquack.sugar.hosted.names.ComposableCn
import team.duckie.quackquack.sugar.hosted.names.QuackComponentPrefix
import team.duckie.quackquack.sugar.hosted.names.SugarTokenName
import team.duckie.quackquack.sugar.hosted.node.SugarComponentNode
import team.duckie.quackquack.sugar.hosted.node.SugarParameter
import team.duckie.quackquack.util.backend.kotlinc.unsafeClassName

internal fun String.bestGuessToKotlinPackageName(): String {
  // make sure testable
  // require(contains("src/main/kotlin")) { "The given package is not a Kotlin package." }
  return substringAfterLast("src/main/kotlin/").replace("/", ".")
}

// TODO: Testing
internal fun SugarComponentNode.toSugarComponentName(tokenFqExpression: String): String {
  val tokenExpression = tokenFqExpression
    .substringAfterLast(".")
    .replaceFirstChar(Char::titlecase)
  return sugarName?.replace(SugarTokenName, tokenExpression)
    ?: referFqn
      .shortName()
      .asString()
      .toMutableList()
      .apply { addAll(QuackComponentPrefix.length, tokenExpression.toList()) }
      .joinToString("")
}

/** 제공된 정보를 [ParameterSpec]으로 변환합니다. */
internal fun SugarParameter.toParameterSpec(): ParameterSpec {
  val parameterTypedBuilder =
    ParameterSpec
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
