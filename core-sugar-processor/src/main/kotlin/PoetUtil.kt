/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

internal fun FileSpec.Builder.addImports(imports: Imports): FileSpec.Builder {
    return apply {
        imports.forEach { import ->
            addImport(
                packageName = import.qualifiedName!!.substringBeforeLast("."),
                import.simpleName!!,
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
