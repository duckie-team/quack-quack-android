/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.utils.addToStdlib.applyIf

private val stringTypeName = String::class.asTypeName()

// TODO: 문서 제공
internal data class CoreSugarParameter(
    val name: KSName,
    val type: TypeName,
    val import: Array<KClass<*>>? = null,
    val defaultValue: KtExpression? = null,
    val sugarToken: String? = null,
) {
    fun toParameterSpec(): ParameterSpec {
        return ParameterSpec
            .builder(name.asString(), type)
            .applyIf(defaultValue != null) {
                val defaultValueType = if (type == stringTypeName) "%S" else "%L"
                defaultValue(defaultValueType, defaultValue!!)
            }
            .build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CoreSugarParameter) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (import != null) {
            if (other.import == null) return false
            if (!import.contentEquals(other.import)) return false
        } else if (other.import != null) return false
        if (defaultValue != other.defaultValue) return false
        if (sugarToken != other.sugarToken) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (import?.contentHashCode() ?: 0)
        result = 31 * result + (defaultValue?.hashCode() ?: 0)
        result = 31 * result + (sugarToken?.hashCode() ?: 0)
        return result
    }
}
