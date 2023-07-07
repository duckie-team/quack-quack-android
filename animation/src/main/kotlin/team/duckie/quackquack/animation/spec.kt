/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable

/** 꽥꽥에서 기본으로 사용할 애니메이션의 지속 시간 */
public const val QuackDefaultAnimationMillis: Int = 150

/** 꽥꽥에서 기본으로 사용할 애니메이션의 [Easing] */
public val QuackDefaultAnimationEasing: Easing = LinearEasing

/** 꽥꽥에서 기본으로 사용할 애니메이션의 [AnimationSpec] */
@Stable
public fun <T> quackTween(): TweenSpec<T> =
  tween(
    durationMillis = QuackDefaultAnimationMillis,
    easing = QuackDefaultAnimationEasing,
  )
