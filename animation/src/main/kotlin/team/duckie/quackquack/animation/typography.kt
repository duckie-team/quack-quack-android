/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.animation

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
import team.duckie.quackquack.util.AllowMagicNumber

/**
 * [TextAlign]의 [TwoWayConverter]를 구현합니다.
 *
 * [TextAlign]의 생성자와 필드가 다 internal 이기에
 * [TextAlign]의 인자 값으로 될 수 있는 1~6 까지의 [Int] 값을
 * 애니메이션하여 각각 값에 맞는 [TextAlign]을 찾아서 생성하는
 * 식으로 구현하였습니다.
 */
@Suppress("PrivatePropertyName")
private val TextAlignVectorConverter = object : TwoWayConverter<TextAlign, AnimationVector1D> {
    val values = TextAlign.values()
    override val convertFromVector: (vector: AnimationVector1D) -> TextAlign
        get() = { vector ->
            @AllowMagicNumber(because = "TextAlign 의 range 를 나타냅니다.")
            val index = vector.value.roundToInt().coerceIn(
                // TextAlign 의 값이 1 부터 시작
                range = 1..6,
            )
            values[index]
        }
    override val convertToVector: (value: TextAlign) -> AnimationVector1D
        get() = { value ->
            // TextAlign 의 값이 1 부터 시작해서 +1
            AnimationVector1D(values.indexOf(value) + 1f)
        }
}

/**
 * [QuackTypography]에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * [QuackTypography.color], [QuackTypography.size], [QuackTypography.weight],
 * [QuackTypography.letterSpacing], [QuackTypography.lineHeight], [QuackTypography.textAlign]
 * 에 애니메이션을 적용합니다.
 *
 * 현재 weight 애니메이션이 적용되지 않습니다. weight는 100 단위로 증가하기
 * 때문에 100~n00으로 애니메이션되는 weight가 구현돼 있지 않아 생기는 이슈입니다.
 *
 * @param targetValue 변경을 감지할 [QuackTypography]
 * @param label Android Studio Animation Inspecter에서 해당 애니메이션을 나타낼 레이블
 * @param colorAnimationFinishedListener [QuackTypography.color]의 애니메이션이 끝났을 때 호출될 콜백
 * @param sizeAnimationFinishedListener [QuackTypography.size]의 애니메이션이 끝났을 때 호출될 콜백
 * @param letterSpacingAnimationFinishedListener [QuackTypography.letterSpacing]의 애니메이션이 끝났을 때 호출될 콜백
 * @param lineHeightAnimationFinishedListener [QuackTypography.lineHeight]의 애니메이션이 끝났을 때 호출될 콜백
 * @param textAlignAnimationFinishedListener [QuackTypography.textAlign]의 애니메이션이 끝났을 때 호출될 콜백
 *
 * @return 애니메이션이 적용되고 있는 [QuackTypography] 객체
 */
@Composable
public fun animatedQuackTextStyleAsState(
    targetValue: QuackTypography,
    label: String = "animatedQuackTextStyleAsState",
    colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
    sizeAnimationFinishedListener: ((size: Float) -> Unit)? = null,
    letterSpacingAnimationFinishedListener: ((letterSpacing: Float) -> Unit)? = null,
    lineHeightAnimationFinishedListener: ((lineHeight: Float) -> Unit)? = null,
    textAlignAnimationFinishedListener: ((textAlign: TextAlign) -> Unit)? = null,
): QuackTypography {
    val targetColorAnimationState by animateQuackColorAsState(
        targetValue = targetValue.color,
        label = label,
        finishedListener = colorAnimationFinishedListener,
    )
    val targetSizeAnimationState by animateFloatAsState(
        targetValue = targetValue.size.value,
        animationSpec = QuackAnimationSpec(),
        label = label,
        finishedListener = sizeAnimationFinishedListener,
    )
    val targetLetterSpacingAnimationState by animateFloatAsState(
        targetValue = targetValue.letterSpacing.value,
        animationSpec = QuackAnimationSpec(),
        label = label,
        finishedListener = letterSpacingAnimationFinishedListener,
    )
    val targetLineHeightAnimationState by animateFloatAsState(
        targetValue = targetValue.lineHeight.value,
        animationSpec = QuackAnimationSpec(),
        label = label,
        finishedListener = lineHeightAnimationFinishedListener,
    )
    val targetTextAlignAnimationState by animateValueAsState(
        targetValue = targetValue.textAlign,
        typeConverter = TextAlignVectorConverter,
        animationSpec = QuackAnimationSpec(),
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
