/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalContracts::class)
@file:Suppress("NOTHING_TO_INLINE", "KotlinConstantConditions")

package team.duckie.quackquack.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

public inline fun <T : Any> requireNull(value: T?): T {
  contract { returns() implies (value == null) }
  return requireNotNull(value) { "Required value was not null." }
}

public inline fun <T : Any> requireNull(value: T?, lazyMessage: () -> Any): T? {
  contract { returns() implies (value == null) }

  if (value != null) {
    val message = lazyMessage()
    throw IllegalArgumentException(message.toString())
  } else {
    return value
  }
}
