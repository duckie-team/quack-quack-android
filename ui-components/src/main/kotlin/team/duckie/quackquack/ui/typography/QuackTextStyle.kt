/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackTextStyle.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:37
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")
@file:OptIn(ExperimentalUnitApi::class)

package team.duckie.quackquack.ui.typography

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import team.duckie.quackquack.common.AllowMagicNumber
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.animation.animateQuackAsState
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState

val LocalQuackFontScale = compositionLocalOf { 1L }

/**
 * 덕키에서 사용할 텍스트 스타일을 정의합니다. 추상화를 위해 컴포즈의
 * [TextStyle] 를 그대로 사용하는게 아닌 이 클래스를 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 텍스트 스타일 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * @param color 텍스트 색상. 색상 역시 추상화를 위해 [QuackColor] 를 사용합니다.
 * 기본값은 검정색 입니다.
 * @param size 텍스트 한 글자 크기
 * @param weight 텍스트 굵기
 * @param letterSpacing 텍스트 자간
 * @param lineHeight 텍스트 줄 크기
 * @param textAlign 텍스트 align. 기본값은 Center 입니다.
 */
// animateQuackTextStyleAsState() 있어서 internal constructor
@Immutable
internal class QuackTextStyle(
    val color: QuackColor = QuackColor.Black,
    val size: TextUnit,
    val weight: FontWeight,
    val letterSpacing: TextUnit,
    val lineHeight: TextUnit,
    val textAlign: TextAlign = TextAlign.Center,
) {
    @Stable
    private val suit = FontFamily(Font(R.font.suit_variable))

    @Stable
    @Composable
    internal fun asComposeStyle() = TextStyle(
        color = color.value,
        fontSize = size * LocalQuackFontScale.current,
        fontFamily = suit,
        fontWeight = weight,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        lineHeight = lineHeight,
    )

    companion object {
        @Stable
        val HeadLine1 = QuackTextStyle(
            size = 20.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 26.sp,
        )

        @Stable
        val HeadLine2 = QuackTextStyle(
            size = 16.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        @Stable
        val Title1 = QuackTextStyle(
            size = 16.sp,
            weight = FontWeight.Normal,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        @Stable
        val Title2 = QuackTextStyle(
            size = 14.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 20.sp
        )

        @Stable
        val Subtitle = QuackTextStyle(
            size = 14.sp,
            weight = FontWeight.Medium,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        @Stable
        val Body1 = QuackTextStyle(
            size = 14.sp,
            weight = FontWeight.Normal,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        @Stable
        val Body2 = QuackTextStyle(
            size = 12.sp,
            weight = FontWeight.Normal,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        @Stable
        val Body3 = QuackTextStyle(
            size = 10.sp,
            weight = FontWeight.Normal,
            letterSpacing = 0.sp,
            lineHeight = 13.sp,
        )
    }

    /**
     * 텍스트 스타일에서 일부 값만 변경이 필요할 때가 있습니다.
     * 이를 대응하기 위해 현재 텍스트 스타일에서 변경을 허용하는
     * 필드만 변경하여 새로운 텍스트 스타일을 반환합니다.
     *
     * @param newColor 변경할 색상
     * @param newWeight 변결할 weight
     *
     * @return 새로운 색상이 적용된 [QuackTextStyle]
     */
    @Stable
    internal fun change(
        newColor: QuackColor = color,
        newWeight: FontWeight = weight,
    ) = QuackTextStyle(
        color = newColor,
        size = size,
        weight = newWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
    )
}

/**
 * TextUnit 에 Long 곱셈을 구현합니다.
 *
 * @receiver 곱셈의 대상이 될 원본 TextUnit
 * @param times 곱할 소숫점 값
 * @return receiver 로 받은 [TextUnit] 의 값에
 * [times] 를 곱한 새로운 [TextUnit] 값
 */
@Stable
private operator fun TextUnit.times(times: Long) = TextUnit(
    value = (value.toDouble() * times.toDouble()).toFloat(),
    type = type,
)

/**
 * Float 를 Sp 로 변환합니다.
 *
 * @receiver Sp 로 변환할 [Float]
 * @return receiver 로 받은 [Float] 를 [TextUnit] 중 Sp 로 변환한 값
 */
@Stable
private fun Float.toSp() = TextUnit(
    value = this,
    type = TextUnitType.Sp,
)

/**
 * TextAlign 의 TwoWayConverter 를 구현합니다.
 *
 * TextAlign 의 생성자와 필드가 다 internal 로 돼있어서
 *
 * ```kotlin
 * value class TextAlign internal constructor(internal val value: Int)
 * ```
 *
 * TextAlign 의 인자 값으로 될 수 있는 1 ~ 6 까지의 Int 값을
 * 애니메이션 하여 각각 값에 맞는 TextAlign 을 찾아서 생성하는
 * 식으로 구현하였습니다.
 */
private val TextAlign.Companion.VectorConverter
    get() = object : TwoWayConverter<TextAlign, AnimationVector1D> {
        override val convertFromVector: (vector: AnimationVector1D) -> TextAlign
            get() = { vector ->
                @AllowMagicNumber
                val index = vector.value.roundToInt().coerceIn(
                    // TextAlign 의 값이 1 부터 시작
                    range = 1..6,
                )
                values()[index]
            }
        override val convertToVector: (value: TextAlign) -> AnimationVector1D
            get() = { value ->
                // TextAlign 의 값이 1 부터 시작해서 +1
                AnimationVector1D(values().indexOf(value) + 1f)
            }
    }

/**
 * [List] 에 대한 component 정의가 5 까지만 있어서
 * 6번째 component 를 추가로 정의합니다.
 *
 * @receiver 구조분해 할당의 대상이 될 Array 객체
 * @return receiver 로 받은 [List] 의 6번째 요소
 */
@AllowMagicNumber
private operator fun <T> List<T>.component6() = get(5)

/**
 * [QuackTextStyle] 에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * 현재 weight 애니메이션이 적용되지 않습니다. weight 는
 * 100 단위로 증가하기 때문에 100 ~ n00 으로 애니메이션 되는
 * weight 가 구현돼 있지 않아 생기는 이슈 입니다. 이를 해결하는건
 * 불가능한 것으로 보입니다.
 *
 * @param targetValue 변경을 감지할 [QuackTextStyle]
 * @param animationSpec 변경을 감지했을 때 적용할 애니메이션 스팩
 *
 * @return Typography 가 변경됐을 때 변경되는 애니메이션의 [State] 객체
 */
@Suppress("UNCHECKED_CAST")
@Composable
internal fun animateQuackTextStyleAsState(
    targetValue: QuackTextStyle,
    animationSpec: AnimationSpec<Any> = quackTween(),
): State<QuackTextStyle> {
    val targetColorAnimationState = animateQuackColorAsState(
        targetValue = targetValue.color,
        animationSpec = animationSpec as AnimationSpec<QuackColor>,
    )
    val targetSizeAnimationState = animateFloatAsState(
        targetValue = targetValue.size.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    )
    val targetWeightAnimationState = animateIntAsState(
        targetValue = targetValue.weight.weight,
        animationSpec = animationSpec as AnimationSpec<Int>,
    )
    val targetLetterSpacingAnimationState = animateFloatAsState(
        targetValue = targetValue.letterSpacing.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    )
    val targetLineHeightAnimationState = animateFloatAsState(
        targetValue = targetValue.lineHeight.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    )
    val targetTextAlignAnimationState = animateValueAsState(
        targetValue = targetValue.textAlign,
        typeConverter = TextAlign.VectorConverter,
        animationSpec = animationSpec as AnimationSpec<TextAlign>,
    )

    return animateQuackAsState(
        initialValue = targetValue,
        animationStates = listOf(
            targetColorAnimationState,
            targetSizeAnimationState,
            targetWeightAnimationState,
            targetLetterSpacingAnimationState,
            targetLineHeightAnimationState,
            targetTextAlignAnimationState,
        ),
        targetBuilder = { animateValues ->
            val (
                color,
                size,
                weight,
                letterSpacing,
                lineHeight,
                textAlign,
            ) = animateValues
            QuackTextStyle(
                color = color as QuackColor,
                size = (size as Float).toSp(),
                weight = FontWeight(weight as Int),
                letterSpacing = (letterSpacing as Float).toSp(),
                lineHeight = (lineHeight as Float).toSp(),
                textAlign = textAlign as TextAlign,
            )
        }
    )
}
