/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UNCHECKED_CAST")

package team.duckie.quackquack.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import kotlin.math.roundToInt
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.toSp

/**
 * [TextAlign]의 [TwoWayConverter]를 구현합니다.
 *
 * [TextAlign]의 생성자와 필드가 다 `internal`이기에 [TextAlign]의 인자 값으로 될 수 있는
 * 1~6까지의 [Int] 값을 애니메이션하여 각각 값에 맞는 [TextAlign]을 찾아서 생성하는 식으로
 * 구현됩니다.
 */
public val TextAlign.Companion.VectorConverter: TwoWayConverter<TextAlign, AnimationVector1D>
  get() = object : TwoWayConverter<TextAlign, AnimationVector1D> {
    val values = values()

    override val convertFromVector: (vector: AnimationVector1D) -> TextAlign
      get() = { vector ->
        // TextAlign의 값이 1 부터 시작
        val index = vector.value.roundToInt().coerceIn(1, 6)
        values[index]
      }

    override val convertToVector: (value: TextAlign) -> AnimationVector1D
      get() = { value ->
        // TextAlign의 값이 1 부터 시작해서 +1
        AnimationVector1D(values.indexOf(value) + 1f)
      }
  }

/**
 * [QuackTypography]에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * [QuackTypography.color], [QuackTypography.size], [QuackTypography.weight],
 * [QuackTypography.letterSpacing], [QuackTypography.lineHeight], [QuackTypography.textAlign]에
 * 애니메이션을 적용합니다.
 *
 * 현재 weight 애니메이션이 적용되지 않습니다. weight는 100 단위로 증가하기 때문에
 * 100~n00으로 애니메이션되는 weight가 구현돼 있지 않아 생기는 문제입니다.
 *
 * @param targetValue 변경을 감지할 [QuackTypography]
 * @param animationSpec 애니메이션에 적용할 [애니메이션 스펙][AnimationSpec]
 * @param label Animation Inspector에서 이 애니메이션을 구분할 별칭
 * @param colorAnimationFinishedListener [QuackTypography.color]의 애니메이션이
 * 끝날 때 호출될 콜백
 * @param sizeAnimationFinishedListener [QuackTypography.size]의 애니메이션이
 * 끝날 때 때 호출될 콜백
 * @param letterSpacingAnimationFinishedListener [QuackTypography.letterSpacing]의
 * 애니메이션이 끝날 때 호출될 콜백
 * @param lineHeightAnimationFinishedListener [QuackTypography.lineHeight]의
 * 애니메이션이 끝날 때 호출될 콜백
 * @param textAlignAnimationFinishedListener [QuackTypography.textAlign]의
 * 애니메이션이 끝날 때 호출될 콜백
 *
 * @return 애니메이션이 적용되고 있는 [QuackTypography] 객체
 */
@Composable
public fun animatedQuackTypographyAsState(
  targetValue: QuackTypography,
  animationSpec: AnimationSpec<Any> = quackTween(),
  label: String = "QuackTypography",
  colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
  sizeAnimationFinishedListener: ((size: Float) -> Unit)? = null,
  letterSpacingAnimationFinishedListener: ((letterSpacing: Float) -> Unit)? = null,
  lineHeightAnimationFinishedListener: ((lineHeight: Float) -> Unit)? = null,
  textAlignAnimationFinishedListener: ((textAlign: TextAlign) -> Unit)? = null,
): QuackTypography {
  val targetColorAnimationState by animateQuackColorAsState(
    targetValue = targetValue.color,
    animationSpec = animationSpec as AnimationSpec<QuackColor>,
    label = label,
    finishedListener = colorAnimationFinishedListener,
  )
  val targetSizeAnimationState by animateFloatAsState(
    targetValue = targetValue.size.value,
    animationSpec = animationSpec as AnimationSpec<Float>,
    label = label,
    finishedListener = sizeAnimationFinishedListener,
  )
  val targetLetterSpacingAnimationState by animateFloatAsState(
    targetValue = targetValue.letterSpacing.value,
    animationSpec = animationSpec as AnimationSpec<Float>,
    label = label,
    finishedListener = letterSpacingAnimationFinishedListener,
  )
  val targetLineHeightAnimationState by animateFloatAsState(
    targetValue = targetValue.lineHeight.value,
    animationSpec = animationSpec as AnimationSpec<Float>,
    label = label,
    finishedListener = lineHeightAnimationFinishedListener,
  )
  val targetTextAlignAnimationState by animateValueAsState(
    targetValue = targetValue.textAlign,
    typeConverter = TextAlign.VectorConverter,
    animationSpec = animationSpec as AnimationSpec<TextAlign>,
    label = label,
    finishedListener = textAlignAnimationFinishedListener,
  )

  return QuackTypography(
    color = targetColorAnimationState,
    size = targetSizeAnimationState.toSp(),
    weight = targetValue.weight,
    letterSpacing = targetLetterSpacingAnimationState.toSp(),
    lineHeight = targetLineHeightAnimationState.toSp(),
    textAlign = targetTextAlignAnimationState,
  )
}
