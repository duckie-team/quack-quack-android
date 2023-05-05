/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

fun String.removePackageLine(): String {
    return this
        .split("\n")
        .toMutableList()
        .apply {
            removeIf { line ->
                line.startsWith("package ")
            }
        }
        .joinToString("\n")
}
