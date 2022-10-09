/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

/**
 * [condition] 이 참이면 [run] 을 실행합니다.
 *
 * @param condition 실행할 조건 [Boolean] 값
 * @param run [condition] 이 참일 때 실행할 람다
 *
 * @return [condition] 이 참일 때 [run] 을 실행한 결과
 */
internal inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else this

/**
 * [condition] 이 true 이면 [run] 을 실행합니다.
 *
 * @param condition 실행할 조건 [Boolean] 값을 반환하는 람다
 * @param run [condition] 이 참일 때 실행할 람다
 *
 * @return [condition] 이 참일 때 [run] 을 실행한 결과
 */
internal inline fun <T> T.runIfBuilder(condition: (T) -> Boolean, run: T.() -> T) =
    if (condition(this)) {
        run()
    } else this
