/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@QuackDsl
@Immutable
internal interface NumberBuilder<T : Number> {
  var value: T
  operator fun plus(number: T): T
  operator fun minus(number: T): T
}

@Stable
internal fun buildFloat(builder: NumberBuilder<Float>.() -> Unit): Float {
  val scope = object : NumberBuilder<Float> {
    override var value = 0f

    override fun minus(number: Float): Float {
      value -= number
      return value
    }

    override fun plus(number: Float): Float {
      value += number
      return value
    }
  }
    .also(builder)

  return scope.value
}

@Stable
internal fun buildInt(builder: NumberBuilder<Int>.() -> Unit): Int {
  val scope = object : NumberBuilder<Int> {
    override var value = 0

    override fun minus(number: Int): Int {
      value -= number
      return value
    }

    override fun plus(number: Int): Int {
      value += number
      return value
    }
  }
    .also(builder)

  return scope.value
}
