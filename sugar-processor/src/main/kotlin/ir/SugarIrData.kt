/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterSpec
import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import org.jetbrains.kotlin.ir.util.isFunction
import org.jetbrains.kotlin.kdoc.psi.api.KDoc
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.addToStdlib.applyIf
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.sugar.material.Imports
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken

/**
 * [SugarIrVisitor]에서 IR을 방문하면서 수집할 정보들을 관리합니다.
 *
 * @param file IR이 제공된 파일
 * @param referFqn IR이 제공된 함수의 [fully-qualified name][FqName]
 * @param kdoc IR이 제공된 함수의 KDoc. [default section][KDoc.getDefaultSection] 값을 가져옵니다.
 * @param sugarName 생성할 sugar component의 네이밍 규칙. [`@SugarToken`][SugarName] 값을 가져옵니다.
 * @param sugarToken 생성할 sugar component의 Sugar Token에 해당하는 [인자][IrValueParameter].
 * [`@SugarToken`][SugarToken]이 달린 인자를 가져옵니다.
 * @param tokenFqExpressions Sugar Token의 expression 모음. 예를 들면 다음과 같습니다.
 * ```
 * package team.duckie.theme
 *
 * @JvmInline
 * value class Theme(val index: Int) {
 *     companion object {
 *         val Default = Theme(1)
 *         val Dark = Theme(2)
 *         val Light = Theme(3)
 *         val System = Theme(4)
 *     }
 * }
 *
 * // ["team.duckie.theme.Theme.Default", "team.duckie.theme.Theme.Dark", "team.duckie.theme.Theme.Light", "team.duckie.theme.Theme.System"]
 * ```
 * @param parameters IR이 제공된 함수의 인자 모음. sugar component 생성에 필요한 정보만
 * 수집합니다. 자세한 수집 정보는 [SugarParameter]를 확인하세요.
 */
internal data class SugarIrData(
    val file: IrFile,
    val referFqn: FqName,
    val kdoc: String,
    val sugarName: String?,
    val sugarToken: IrValueParameter,
    val tokenFqExpressions: List<String>,
    val parameters: List<SugarParameter>,
) {
    /**
     * [parameters]에서 Sugar Token을 제외한 [요소][SugarParameter]만 불러옵니다.
     */
    val parametersWithoutToken: List<SugarParameter> = parameters.toMutableList().apply {
        removeIf(SugarParameter::isToken)
    }

    override fun toString(): String {
        return """
            |file: ${file.name}
            |referFqn: ${referFqn.asString()}
            |kdoc: $kdoc
            |sugarName: $sugarName
            |sugarToken: ${sugarToken.name.asString()}
            |tokenExpressions: $tokenFqExpressions
            |parameters: ${parameters.joinToString("\n\n", prefix = "\n")}
        """.trimMargin()
    }
}

/**
 * [IrValueParameter]에서 sugar component 생성에 필요한 정보만 관리합니다.
 *
 * *주의*: 현재 함수형 타입은 지원되지 않습니다. 함수형 타입엔 generic type erasure가 적용되어
 * 컴파일단에서는 `Function` 까지만 조회됩니다. 즉, `Function`의 `T` 값을 유추할 수 없기에
 * 별도 대응이 필요합니다. 대응 일정은 정해지지 않았습니다.
 *
 * @param name 인자명
 * @param type 인자의 타입
 * @param isToken 인자가 [Sugar Token][SugarToken]인지 여부
 * @param imports [type] 외에 추가로 import가 필요한 클래스의 [fully-qualified name][FqName]으로 구성된 목록.
 * 자세한 정보는 [Imports]를 확인하세요.
 * @param casaValueLiteral [CasaValue.literal] 값
 * @param defaultValue 인자의 기본 값
 */
internal data class SugarParameter(
    val name: Name,
    val type: IrType,
    val isToken: Boolean,
    val imports: List<FqName>,
    val casaValueLiteral: String?,
    val defaultValue: IrExpressionBody?,
) {
    init {
        if (type.isFunction()) {
            error(NotSupportedError.functionalType(name.asString()))
        }
    }

    /**
     * 현재 정보들을 kotlinpoet의 [ParameterSpec]으로 제공합니다.
     */
    fun toParameterSpec(): ParameterSpec {
        return ParameterSpec
            .builder(
                name = name.asString(),
                type = ClassName.bestGuess(type.unsafeFqn),
            )
            .applyIf(casaValueLiteral != null) {
                addAnnotation(
                    annotationSpec = AnnotationSpec
                        .builder(CasaValueCn)
                        .addMember("%S", casaValueLiteral!!)
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
            |name: ${name.asString()}
            |type: ${type.asString()}
            |isToken: $isToken
            |imports: ${imports.joinToString(transform = FqName::asString)}
            |casaValueLiteral: $casaValueLiteral
            |defaultValue: ${defaultValue?.dumpKotlinLike()}
        """.trimMargin()
    }
}
