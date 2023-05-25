/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

/** builder api와 유사하게 [Number]를 build합니다. */
internal interface NumberBuilder<T : Number> {
  /** build할 [Number]의 초기 값 */
  var value: T

  /** [value]에 더하기 연산을 적용합니다. */
  operator fun plus(number: T): T

  /** [value]에 빼기 연산을 적용합니다. */
  operator fun minus(number: T): T
}

/** [NumberBuilder]의 [Float] 버전을 구현합니다. 초기 값으로 0f를 사용합니다. */
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

/** [buildFloat] 연산에 [Int] 더하기 연산을 허용합니다. */
internal operator fun NumberBuilder<Float>.plus(number: Int): Float {
  value += number
  return value
}

/** [buildFloat] 연산에 [Int] 빼기 연산을 허용합니다. */
internal operator fun NumberBuilder<Float>.minus(number: Int): Float {
  value -= number
  return value
}

/** [NumberBuilder]의 [Int] 버전을 구현합니다. 초기 값으로 0를 사용합니다. */
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
