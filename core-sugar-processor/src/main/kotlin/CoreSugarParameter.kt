/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass
import org.jetbrains.kotlin.utils.addToStdlib.applyIf

private val stringTypeName = String::class.asTypeName()

// TODO: 문서 제공
internal data class CoreSugarParameter(
    val name: KSName,
    val type: TypeName,
    val imports: Array<KClass<*>>? = null,
    val defaultValue: String? = null,
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
        if (imports != null) {
            if (other.imports == null) return false
            if (!imports.contentEquals(other.imports)) return false
        } else if (other.imports != null) return false
        if (defaultValue != other.defaultValue) return false
        if (sugarToken != other.sugarToken) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (imports?.contentHashCode() ?: 0)
        result = 31 * result + (defaultValue?.hashCode() ?: 0)
        result = 31 * result + (sugarToken?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return """
            name: ${name.asString()}
            type: $type
            imports: ${imports?.joinToString { it.simpleName!! }}
            defaultValue: $defaultValue
            sugarToken: $sugarToken
        """.trimIndent()
    }
}
