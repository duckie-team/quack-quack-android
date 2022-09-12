/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
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

/**
 * 주어진 값이 참인지 확인합니다. QuackQuack 에 맞는 기본 에러 메시지를 사용하기 위해 구현됐습니다.
 *
 * @param value 참인지 확인할 값
 * @param lazyMessage [value] 가 참이 아니라면 에러로 사용될 메시지를 반환하는 람다
 * 기본값은 [DefaultMessage] 상수 참고
 *
 * @throw [value] 가 참이 아니라면 [lazyMessage] 값으로 [IllegalArgumentException]
 */
inline fun runtimeCheck(value: Boolean, lazyMessage: () -> String = { DefaultMessage }) {
    if (!value) throw IllegalStateException(lazyMessage())
}

/**
 * 주어진 메시지로 NPE 을 발생시킵니다.
 *
 * 주어진 값이 null 이라면 해당 구문을 NPE 과 함께 break 하는데 사용됩니다.
 * QuackQuack 에 맞는 기본 에러 메시지를 사용하기 위해 구현됐습니다.
 *
 * ```kotlin
 * val value: String? = null
 * print(value ?: npe())
 * ```
 *
 * @param lazyMessage NPE 에 사용될 메시지를 반환하는 람다
 * 기본값은 [DefaultMessage] 상수 참고
 *
 * @throw [lazyMessage] 의 반환값을 메시지로 사용하는 [NullPointerException]
 */
inline fun npe(lazyMessage: () -> String = { DefaultMessage }): Nothing =
    throw NullPointerException(lazyMessage())
