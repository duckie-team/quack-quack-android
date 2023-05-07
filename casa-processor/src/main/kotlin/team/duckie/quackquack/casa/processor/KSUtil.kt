/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile

internal val KSDeclaration.requireContainingFile: KSFile
    get() = requireNotNull(containingFile) {
        "This($simpleName) symbol didn't come from the source file. " +
                "Is that symbol in the class file?"
    }

internal val Dependencies.Companion.Empty: Dependencies
    get() = Dependencies(aggregating = false)
