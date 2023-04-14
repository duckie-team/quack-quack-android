/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.util

/**
 * [condition]이 참이면 [run]을 실행합니다.
 *
 * @return [condition]이 참일 때 [run]을 실행한 결과
 */
public inline fun <T> T.runIf(
    condition: Boolean,
    run: T.() -> T,
): T {
    return if (condition) {
        run()
    } else {
        this
    }
}

/**
 * [conditionBuilder]이 참이면 [run]을 실행합니다.
 *
 * @return [conditionBuilder]이 참일 때 [run]을 실행한 결과
 */
public inline fun <T> T.runIf(
    conditionBuilder: (receiver: T) -> Boolean,
    run: T.() -> T,
): T {
    return if (conditionBuilder(this)) {
        run()
    } else {
        this
    }
}
