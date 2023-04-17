/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

// copied from kotlin-stdlib
internal inline fun <T, V> Sequence<T>.singleWithTransform(predicate: (T) -> Pair<Boolean, V?>): V {
    var single: V? = null
    var found = false
    for (element in this) {
        val (find, transform) = predicate(element)
        if (find) {
            if (found) {
                throw IllegalArgumentException("Sequence contains more than one matching element.")
            }
            single = transform ?: error("The element cannot be transformed to null.")
            found = true
        }
    }
    if (!found) {
        throw NoSuchElementException("Sequence contains no element matching the predicate.")
    }
    return single!!
}

// copied from kotlin-stdlib
internal fun <T> Sequence<T>.singleOrNullStrict(): T? {
    val iterator = iterator()
    if (!iterator.hasNext()) {
        return null
    }
    val single = iterator.next()
    if (iterator.hasNext()) {
        throw IllegalArgumentException("Sequence contains more than one matching element.")
    }
    return single
}

internal fun StringBuilder.appendLineWithIndent(
    value: String?,
    indentSize: Int = 4,
): StringBuilder {
    return appendLine("${" ".repeat(indentSize)}$value")
}
