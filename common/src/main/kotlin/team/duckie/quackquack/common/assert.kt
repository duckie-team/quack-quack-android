/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [assert.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:55
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.common

@PublishedApi
internal val DefaultMessage = """
    A runtime error occurred in quack-quack. Please report it through GitHub issues.
    https://github.com/sungbinland/quack-quack/issues/
""".trimIndent()

inline fun runtimeCheck(value: Boolean, lazyMessage: () -> String = { DefaultMessage }) {
    if (!value) throw IllegalStateException(lazyMessage())
}

inline fun npe(lazyMessage: () -> String = { DefaultMessage }): Nothing =
    throw NullPointerException(lazyMessage())
