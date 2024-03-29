/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UNCHECKED_CAST")

package team.duckie.quackquack.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor

/**
 * [QuackBorder]의 변화에 애니메이션을 적용합니다. [QuackBorder.thickness]와
 * [QuackBorder.color]의 변화에 애니메이션이 적용됩니다.
 *
 * @param targetValue 애니메이션을 적용할 [QuackBorder]
 * @param animationSpec 애니메이션에 적용할 [애니메이션 스펙][AnimationSpec]
 * @param label Animation Inspector에서 이 애니메이션을 구분할 별칭
 * @param widthAnimationfinishedListener [QuackBorder.thickness]의 애니메이션이
 * 끝나면 호출될 콜백
 * @param colorAnimationFinishedListener [QuackBorder.color]의 애니메이션이 끝나면
 * 호출될 콜백
 *
 * @return 애니메이션이 적용되고 있는 [QuackBorder] 객체
 */
@Composable
public fun animatedQuackBorderAsState(
  targetValue: QuackBorder,
  animationSpec: AnimationSpec<Any> = quackTween(),
  label: String = "QuackBorder",
  widthAnimationfinishedListener: ((dp: Dp) -> Unit)? = null,
  colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
): QuackBorder {
  val widthAnimationState by animateDpAsState(
    targetValue = targetValue.thickness,
    animationSpec = animationSpec as AnimationSpec<Dp>,
    label = label,
    finishedListener = widthAnimationfinishedListener,
  )
  val colorAnimationState by animateQuackColorAsState(
    targetValue = targetValue.color,
    animationSpec = animationSpec as AnimationSpec<QuackColor>,
    label = label,
    finishedListener = colorAnimationFinishedListener,
  )
  return QuackBorder(thickness = widthAnimationState, color = colorAnimationState)
}
