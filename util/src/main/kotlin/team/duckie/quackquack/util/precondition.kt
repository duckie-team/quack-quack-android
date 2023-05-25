/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalContracts::class)
@file:Suppress("KotlinConstantConditions")

package team.duckie.quackquack.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * 주어진 값이 null인지 검증합니다.
 *
 * @param value 검증할 값
 * @param lazyMessage 주어진 값이 null이 아닐 때 throw할 검증 실패 메시지
 */
public inline fun <T : Any> requireNull(
  value: T?,
  lazyMessage: () -> Any = { "Required value was not null." },
): T? {
  contract { returns() implies (value == null) }

  if (value != null) {
    val message = lazyMessage()
    throw IllegalArgumentException(message.toString())
  } else {
    return value
  }
}
