/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package poet

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import ir.QuackComponentPrefix
import ir.SugarIrData
import ir.SugarTokenName
import org.jetbrains.kotlin.name.FqName

internal fun String.bestGuessToKotlinPackageName(): String {
    return substringAfter("src/main/kotlin/").replace("/", ".")
}

internal fun SugarIrData.toSugarComponentName(tokenFqExpression: String): String {
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

internal fun FileSpec.Builder.addImports(imports: List<FqName>): FileSpec.Builder {
    return apply {
        imports.forEach { import ->
            addImport(
                packageName = import.parent().asString(),
                import.shortName().asString(),
            )
        }
    }
}

internal fun FileSpec.Builder.addFunctions(functions: List<FunSpec>): FileSpec.Builder {
    return apply {
        functions.forEach { function ->
            addFunction(function)
        }
    }
}
