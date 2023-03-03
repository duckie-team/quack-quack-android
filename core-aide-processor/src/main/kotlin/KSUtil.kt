/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile

internal fun Collection<*>.toLiteralListString(): String {
    return joinToString(
        prefix = "listOf(",
        postfix = ")",
        transform = { "\"$it\"" },
    )
}

internal val KSDeclaration.requiredContainingFile: KSFile
    get() = requireNotNull(containingFile) {
        "This($simpleName) symbol didn't come from the source file. " +
                "Is that symbol in the class file?"
    }

internal val Dependencies.Companion.Empty: Dependencies
    get() = Dependencies(aggregating = false)
