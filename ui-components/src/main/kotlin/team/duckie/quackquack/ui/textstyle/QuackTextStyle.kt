/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")
@file:OptIn(ExperimentalUnitApi::class)

package team.duckie.quackquack.ui.textstyle

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.animation.animateQuackAsState
import team.duckie.quackquack.ui.animation.quackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.util.AllowMagicNumber

/**
 * 덕키에서 사용할 [QuackTextStyle] 의 기본 font scale
 *
 * Playground 에서 [QuackFontScale] 편집 후 기본값으로 되돌리고
 * 싶을 때 기본값을 참조하기 위해 public 으로 설정함
 */
const val QuackDefaultFontScale = 1.0

/**
 * 덕키에서 사용할 [QuackTextStyle] 의 font scale
 *
 * Playground 에서 자유로운 font scale 편집으로 쉬운 디버깅을 위해 public 으로 설정함
 */
var QuackFontScale by mutableStateOf(QuackDefaultFontScale)

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
class QuackTextStyle internal constructor(
    val color: QuackColor = QuackColor.Black,
    val size: TextUnit,
    val weight: FontWeight,
    val letterSpacing: TextUnit,
    val lineHeight: TextUnit,
    val textAlign: TextAlign = TextAlign.Center,
) {
    @Stable
    private val suit = FontFamily(Font(R.font.suit_variable))

    /**
     * [QuackTextStyle] 을 컴포즈 Text 컴포넌트에 사용하기 위해
     * [TextStyle] 로 변환합니다.
     *
     * @return 변환된 [TextStyle]
     */
    @Stable
    internal fun asComposeStyle() = TextStyle(
        color = color.value,
        fontSize = size * QuackFontScale,
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
            lineHeight = 20.sp,
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
     * @param color 변경할 색상
     * @param weight 변결할 weight
     * @param textAlign 변경할 textAlign
     *
     * @return 새로운 스타일이 적용된 [QuackTextStyle]
     */
    @Stable
    internal fun change(
        color: QuackColor = this.color,
        weight: FontWeight = this.weight,
        textAlign: TextAlign = this.textAlign,
    ) = QuackTextStyle(
        color = color,
        size = size,
        weight = weight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
    )
}

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
 * [List] 에 대한 component 정의가 5 까지만 있어서
 * 6번째 component 를 추가로 정의합니다.
 *
 * @receiver 구조분해 할당의 대상이 될 Array 객체
 * @return receiver 로 받은 [List] 의 6번째 요소
 */
@AllowMagicNumber(
    because = "6번째 요소는 항상 5번째 인덱스를 갖기 때문에 5로 고정합니다.",
)
private operator fun <T> List<T>.component6() = get(5)

/**
 * [QuackTextStyle] 에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * [QuackTextStyle.color], [QuackTextStyle.size] 에 애니메이션을 적용합니다.
 *
 * @param targetValue 변경을 감지할 [QuackTextStyle]
 * @param animationSpec 변경을 감지했을 때 적용할 애니메이션 스팩
 *
 * @return [targetValue] 가 변경됐을 때 변경되는 애니메이션의 [State] 객체
 */
// FIXME: [QuackTextStyle.color], [QuackTextStyle.size] 의외에 애니메이션을 적용하면
//        애니메이션이 정상적으로 진행되지 않을 뿐더러, 올바른 리컴포지션 타이밍에
//        값 갱신이 되지 않고, 추후 두 번째 리컴포지션 때 값 변경이 반영됩니다.
//        따라서 현재는 [QuackTextStyle.color], [QuackTextStyle.size] 에만 애니메이션을 적용합니다.
//        예전 구현: https://gist.github.com/jisungbin/397da79ffecdc264a6524c1720779e6e
@Suppress("UNCHECKED_CAST")
@Composable
internal fun animateQuackTextStyleAsState(
    targetValue: QuackTextStyle,
    animationSpec: AnimationSpec<Any> = quackAnimationSpec(),
): State<QuackTextStyle> {
    val targetColorAnimationState = animateQuackColorAsState(
        targetValue = targetValue.color,
        animationSpec = animationSpec as AnimationSpec<QuackColor>,
    )
    val targetSizeAnimationState = animateFloatAsState(
        targetValue = targetValue.size.value,
        animationSpec = animationSpec as AnimationSpec<Float>,
    )

    return animateQuackAsState(
        initialValue = targetValue,
        animationStates = listOf(
            targetColorAnimationState,
            targetSizeAnimationState,
        ),
        targetBuilder = { animateValues ->
            val (
                color,
                size,
            ) = animateValues
            QuackTextStyle(
                color = color as QuackColor,
                size = (size as Float).toSp(),
                weight = targetValue.weight,
                letterSpacing = targetValue.letterSpacing,
                lineHeight = targetValue.lineHeight,
                textAlign = targetValue.textAlign,
            )
        },
    )
}
