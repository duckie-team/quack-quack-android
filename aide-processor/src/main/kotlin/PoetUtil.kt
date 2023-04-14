/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

internal fun Collection<*>.toLiteralListString(): String {
    return joinToString(
        prefix = "listOf(",
        postfix = ")",
        transform = { "\"$it\"" },
    )
}
