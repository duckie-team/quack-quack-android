/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import kotlin.reflect.KClass
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.utils.addToStdlib.applyIf

// TODO: 문서 제공
internal data class CoreSugarParameter(
    val name: KSName,
    val type: TypeName,
    val import: KClass<*>? = null,
    val defaultValue: KtExpression? = null,
    val sugarToken: String? = null,
) {
    fun toParameterSpec(): ParameterSpec {
        return ParameterSpec
            .builder(name.asString(), type)
            .applyIf(defaultValue != null) {
                defaultValue("%L", defaultValue!!)
            }
            .build()
    }
}
