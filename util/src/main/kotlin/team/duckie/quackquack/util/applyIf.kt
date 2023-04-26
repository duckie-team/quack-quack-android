/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")
@file:OptIn(ExperimentalContracts::class)

package team.duckie.quackquack.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * [condition]이 참이면 [run]을 실행합니다.
 *
 * @return [condition]이 참일 때 [run]을 실행한 결과
 */
public inline fun <T> T.applyIf(condition: Boolean, run: T.() -> T): T {
    contract { callsInPlace(run, InvocationKind.EXACTLY_ONCE) }
    return if (condition) run() else this
}

/**
 * [conditionBuilder]이 참이면 [run]을 실행합니다.
 *
 * @return [conditionBuilder]이 참일 때 [run]을 실행한 결과
 */
public inline fun <T> T.applyIf(conditionBuilder: (receiver: T) -> Boolean, run: T.() -> T): T {
    contract {
        callsInPlace(conditionBuilder, InvocationKind.EXACTLY_ONCE)
        callsInPlace(run, InvocationKind.EXACTLY_ONCE)
    }
    return if (conditionBuilder(this)) run() else this
}
