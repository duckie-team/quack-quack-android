/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package poet

import QuackComponentPrefix
import SugarTokenName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import ir.SugarIrData
import org.jetbrains.kotlin.name.FqName

internal typealias FilePath = String

internal fun FilePath.bestGuessToKotlinPackageName(): String {
    return substringAfter("src/main/kotlin/")
}

internal fun SugarIrData.toSugarComponentName(tokenFqExpression: String): String {
    val tokenExpression = tokenFqExpression.substringAfterLast(".").replaceFirstChar(Char::titlecase)
    return sugarName?.replace(SugarTokenName, tokenExpression)
        ?: refer
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
