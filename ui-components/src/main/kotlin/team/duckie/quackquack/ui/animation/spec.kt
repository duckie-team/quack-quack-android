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
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable

@Stable
private const val DefaultAnimationMills = 250

@Stable
internal fun <T> quackTween() = tween<T>(
    durationMillis = DefaultAnimationMills,
    easing = FastOutSlowInEasing,
)
