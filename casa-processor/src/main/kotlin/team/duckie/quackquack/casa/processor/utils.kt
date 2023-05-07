/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

// copied from kotlin-stdlib
internal fun <T> Sequence<T>.singleOrNullStrict(predicate: (T) -> Boolean): T? {
    var single: T? = null
    val iterator = iterator()
    while (iterator.hasNext()) {
        val element = iterator.next()
        if (predicate(element)) {
            require(single == null) { "Sequence contains more than one matching element." }
            single = element
        }
    }
    return single
}

internal fun StringBuilder.appendLineWithIndent(
    value: String?,
    indentSize: Int = 2,
): StringBuilder {
    return appendLine("${" ".repeat(indentSize)}$value")
}
