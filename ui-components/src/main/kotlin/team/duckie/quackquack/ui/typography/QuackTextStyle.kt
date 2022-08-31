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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.combine
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState

val LocalQuackFontScale = staticCompositionLocalOf { 1L }

/**
 * 덕키에서 사용할 텍스트 스타일을 정의합니다. 추상화를 위해 컴포즈의 [TextStyle] 를 그대로 사용하는게 아닌
 * 이 클래스를 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 텍스트 스타일 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * @param color 텍스트 색상. 색상 역시 추상화를 위해 [QuackColor] 를 사용합니다.
 * @param size 텍스트 한 글자 크기
 * @param weight 텍스트 굵기
 * @param letterSpacing 텍스트 자간
 * @param lineHeight 텍스트 줄 크기
 */
@Immutable
class QuackTextStyle internal constructor(
    val color: QuackColor = QuackColor.Black,
    val size: TextUnit,
    val weight: FontWeight,
    val letterSpacing: TextUnit,
    val lineHeight: TextUnit,
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
        textAlign = TextAlign.Center,
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
     * 텍스트 스타일에서 색상 값만 변경이 필요할 때가 있습니다.
     * 이를 대응하기 위해 현재 텍스트 스타일에서 텍스트 색상만 변경하여
     * 새로운 텍스트 스타일을 반환합니다.
     *
     * @param newColor 변경할 색상
     *
     * @return 새로운 색상이 적용된 [QuackTextStyle]
     */
    @Stable
    internal fun changeColor(
        newColor: QuackColor,
    ) = QuackTextStyle(
        color = newColor,
        size = size,
        weight = weight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
    )
}

@Stable
private operator fun TextUnit.times(times: Long) = TextUnit(
    value = value * times,
    type = type,
)

/**
 * Float 를 Sp 로 변환합니다.
 */
@Stable
private fun Float.toSp() = TextUnit(
    value = this,
    type = TextUnitType.Sp,
)

/**
 * State 를 Flow 로 변환합니다.
 *
 * @receiver 기존 State<T> 객체
 * @return Flow 로 바뀐 State<T> 객체
 */
private fun <T> State<T>.toFlow() = snapshotFlow { this }

/**
 * [QuackTextStyle] 에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * @param targetValue 변경을 감지할 [QuackTextStyle]
 * @param animationSpec 변경을 감지했을 때 적용할 애니메이션 스팩
 *
 * @return Typography 가 변경됐을 때 변경되는 애니메이션의 [State] 객체
 *
 * // FIXME: 현재 weight 애니메이션이 제대로 적용되지 않습니다.
 */
@Suppress("UNCHECKED_CAST")
@Composable
fun animateQuackTextStyleAsState(
    targetValue: QuackTextStyle,
    animationSpec: AnimationSpec<Any> = quackTween(),
): State<QuackTextStyle> {
    val targetColorAnimationFlow = animateQuackColorAsState(
        targetValue = targetValue.color,
        animationSpec = animationSpec as AnimationSpec<QuackColor>,
    ).toFlow()
    val targetSizeAnimationFlow = animateFloatAsState(
        targetValue = targetValue.size.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    ).toFlow()
    val targetWeightAnimationFlow = animateIntAsState(
        targetValue = targetValue.weight.weight,
        animationSpec = animationSpec as AnimationSpec<Int>,
    ).toFlow()
    val targetLetterSpacingAnimationFlow = animateFloatAsState(
        targetValue = targetValue.letterSpacing.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    ).toFlow()
    val targetLineHeightAnimationFlow = animateFloatAsState(
        targetValue = targetValue.lineHeight.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    ).toFlow()

    return combine(
        targetColorAnimationFlow,
        targetSizeAnimationFlow,
        targetWeightAnimationFlow,
        targetLetterSpacingAnimationFlow,
        targetLineHeightAnimationFlow,
    ) { animationFlows ->
        val (color, size, weight, letterSpacing, lineHeight) = animationFlows
        QuackTextStyle(
            color = color.value as QuackColor,
            size = (size.value as Float).toSp(),
            weight = FontWeight(weight.value as Int),
            letterSpacing = (letterSpacing.value as Float).toSp(),
            lineHeight = (lineHeight.value as Float).toSp(),
        )
    }.collectAsState(initial = targetValue)
}
