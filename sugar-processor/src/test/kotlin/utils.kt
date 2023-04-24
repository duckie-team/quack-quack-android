/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import org.intellij.lang.annotations.Language
import strikt.api.Assertion
import strikt.assertions.isEqualTo

fun Assertion.Builder<String>.isKtEqualToWithoutPackage(@Language("kotlin") code: String): Assertion.Builder<String> {
    val withoutPackage = subject
        .split("\n")
        .toMutableList()
        .apply {
            removeIf { line ->
                line.startsWith("package ")
            }
        }
        .joinToString("\n")
    return get { withoutPackage }.isEqualTo(code.trimIndent())
}
