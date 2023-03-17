/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import ComposableFqn
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterSpec
import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.addToStdlib.applyIf

// TODO: 문서 제공
internal data class SugarIrData(
    val refer: String,
    val kdoc: String,
    val sugarName: String?,
    val sugarToken: IrValueParameter,
    val tokenFqExpressions: List<String>,
    val parameters: List<SugarParameter>,
) {
    override fun toString(): String {
        return """
            |refer: $refer
            |kdoc: $kdoc
            |sugarName: $sugarName
            |sugarToken: ${sugarToken.name.asString()}
            |tokenExpressions: $tokenFqExpressions
            |parameters: ${parameters.joinToString("\n\n", prefix = "\n")}
        """.trimMargin()
    }
}

internal data class SugarParameter(
    val name: Name,
    val type: IrType,
    val isToken: Boolean,
    val isComposable: Boolean,
    val imports: List<FqName>,
    val defaultValue: IrExpressionBody?,
) {
    fun toParameterSpec(): ParameterSpec {
        return ParameterSpec
            .builder(
                name = name.asString(),
                type = ClassName.bestGuess(type.unsafeFqn),
            )
            .applyIf(isComposable) {
                addAnnotation(ClassName.bestGuess(ComposableFqn))
            }
            .build()
    }

    override fun toString(): String {
        return """
            |name: ${name.asString()}
            |type: ${type.asString()}
            |isToken: $isToken
            |isComposable: $isComposable
            |imports: ${imports.joinToString(transform = FqName::asString)}
            |defaultValue: ${defaultValue?.dumpKotlinLike()}
        """.trimMargin()
    }
}
