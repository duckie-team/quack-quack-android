/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [spec.kt] created by Ji Sungbin on 22. 8. 14. 오후 8:13
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable

/**
 * 덕키에서 사용할 애니메이션의 지속 시간
 */
private const val DefaultAnimationMills = 250

/**
 * 덕키에서 사용할 애니메이션 중 [Tween][TweenSpec] 의 기본 스팩
 */
@Stable
internal fun <T> quackTween() = tween<T>(
    durationMillis = DefaultAnimationMills,
    easing = FastOutSlowInEasing,
)
