/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")
@file:OptIn(ExperimentalUnitApi::class)

package team.duckie.quackquack.ui.textstyle

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
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
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.util.AllowMagicNumber

/**
 * 덕키에서 사용할 텍스트 스타일을 정의합니다. 추상화를 위해 컴포즈의
 * [TextStyle] 를 그대로 사용하는게 아닌 이 클래스를 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 텍스트 스타일 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * @param color 텍스트 색상. 기본값은 검정색 입니다.
 * @param size 텍스트 한 글자 크기
 * @param weight 텍스트 굵기
 * @param letterSpacing 텍스트 자간
 * @param lineHeight 텍스트 줄 크기
 * @param textAlign 텍스트 align. 기본값은 Center 입니다.
 */
// animatedQuackTextStyleAsState 있어서 internal constructor
public class QuackTextStyle internal constructor(
    public val color: QuackColor = QuackColor.Black,
    public val size: TextUnit,
    public val weight: FontWeight,
    public val letterSpacing: TextUnit,
    public val lineHeight: TextUnit,
    public val textAlign: TextAlign = TextAlign.Start,
) {
    private val suit = FontFamily(
        Font(
            resId = R.font.quack_suit_variable,
        ),
    )

    /**
     * [QuackTextStyle] 을 컴포즈 Text 컴포넌트에 사용하기 위해
     * [TextStyle] 로 변환합니다.
     *
     * @return 변환된 [TextStyle]
     */
    @Stable
    public fun asComposeStyle(): TextStyle = TextStyle(
        color = color.composeColor,
        fontSize = size,
        fontFamily = suit,
        fontWeight = weight,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        lineHeight = lineHeight,
    )

    /**
     * 정해진 [QuackTextStyle] 에서 일부 값만 변경이 필요할 때가 있습니다.
     * 이를 대응하기 위해 [QuackTextStyle] 에서 변경을 허용하는
     * 필드만 변경하여 새로운 [QuackTextStyle] 을 반환하는 API 를 구현합니다.
     *
     * 색상과 정렬의 변경은 고정된 디자인의 목적을 해치지 않을 것으로 예상하여
     * public 으로 노출합니다.
     *
     * @param color 변경할 색상
     * @param textAlign 변경할 textAlign
     *
     * @return 새로운 스타일이 적용된 [QuackTextStyle]
     */
    @Stable
    public fun change(
        color: QuackColor = this.color,
        textAlign: TextAlign = this.textAlign,
    ): QuackTextStyle {
        return if (color == this.color && textAlign == this.textAlign) {
            this
        } else {
            QuackTextStyle(
                color = color,
                size = size,
                weight = weight,
                letterSpacing = letterSpacing,
                lineHeight = lineHeight,
                textAlign = textAlign,
            )
        }
    }

    public companion object {
        /**
         * 덕키 스플래시 화면의 슬로건에 쓰이는 특이 케이스의 TextStyle
         */
        public val SplashSlogan: QuackTextStyle = QuackTextStyle(
            size = 24.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Left,
        )

        public val HeadLine1: QuackTextStyle = QuackTextStyle(
            size = 20.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 26.sp,
        )

        public val HeadLine2: QuackTextStyle = QuackTextStyle(
            size = 16.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        public val Title1: QuackTextStyle = QuackTextStyle(
            size = 16.sp,
            weight = FontWeight.Regular,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        public val Title2: QuackTextStyle = QuackTextStyle(
            size = 14.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        public val Subtitle: QuackTextStyle = QuackTextStyle(
            size = 14.sp,
            weight = FontWeight.Medium,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        public val Subtitle2: QuackTextStyle = QuackTextStyle(
            size = 12.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        public val Body1: QuackTextStyle = QuackTextStyle(
            size = 14.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        public val Body2: QuackTextStyle = QuackTextStyle(
            size = 12.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        public val Body3: QuackTextStyle = QuackTextStyle(
            size = 10.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 13.sp,
        )
    }
}

/**
 * [FontWeight] 에 Regular 를 정의합니다.
 *
 * [FontWeight.Normal] 는 FontWeight.Regular 와 동일합니다.
 */
private inline val FontWeight.Companion.Regular get() = Normal

/**
 * Float 를 Sp 로 변환합니다.
 *
 * @receiver Sp 로 변환할 [Float]
 *
 * @return receiver 로 받은 [Float] 를 [TextUnit] 중 Sp 로 변환한 값
 */
@Suppress("NOTHING_TO_INLINE") // JVM 최적화를 위해 인라인
private inline fun Float.toSp() = TextUnit(
    value = this,
    type = TextUnitType.Sp,
)

/**
 * [List] 에 대한 component 정의가 5 까지만 있어서
 * 6번째 component 를 추가로 정의합니다.
 *
 * @receiver 구조분해 할당의 대상이 될 Array 객체
 *
 * @return receiver 로 받은 [List] 의 6번째 요소
 */
@AllowMagicNumber(
    because = "6번째 요소는 항상 5번째 인덱스를 갖기 때문에 5로 고정합니다.",
)
private operator fun <T> List<T>.component6() = get(
    index = 5,
)

/**
 * [TextAlign] 의 [TwoWayConverter] 를 구현합니다.
 *
 * [TextAlign] 의 생성자와 필드가 다 internal 이기 때문에
 * [TextAlign] 의 인자 값으로 될 수 있는 1 ~ 6 까지의 [Int] 값을
 * 애니메이션하여 각각 값에 맞는 [TextAlign] 을 찾아서 생성하는
 * 식으로 구현하였습니다.
 */
private val TextAlign.Companion.VectorConverter
    get() = object : TwoWayConverter<TextAlign, AnimationVector1D> {
        override val convertFromVector: (vector: AnimationVector1D) -> TextAlign
            get() = { vector ->
                @AllowMagicNumber(
                    because = "TextAlign 의 range 를 나타냅니다.",
                )
                val index = vector.value.roundToInt().coerceIn(
                    // TextAlign 의 값이 1 부터 시작
                    range = 1..6,
                )
                values()[index]
            }
        override val convertToVector: (value: TextAlign) -> AnimationVector1D
            get() = { value ->
                // TextAlign 의 값이 1 부터 시작해서 +1
                AnimationVector1D(
                    initVal = values().indexOf(
                        element = value,
                    ) + 1f,
                )
            }
    }

/**
 * [QuackTextStyle] 에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * [QuackTextStyle.color], [QuackTextStyle.size], [QuackTextStyle.weight],
 * [QuackTextStyle.letterSpacing], [QuackTextStyle.lineHeight], [QuackTextStyle.textAlign]
 * 에 애니메이션을 적용합니다.
 *
 * 현재 weight 애니메이션이 적용되지 않습니다. weight 는 100 단위로 증가하기
 * 때문에 100 ~ n00 으로 애니메이션 되는 weight 가 구현돼 있지 않아 생기는 이슈 입니다.
 *
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param targetValue 변경을 감지할 [QuackTextStyle]
 * @param colorAnimationFinishedListener [QuackTextStyle.color] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param sizeAnimationFinishedListener [QuackTextStyle.size] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param letterSpacingAnimationFinishedListener [QuackTextStyle.letterSpacing] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param lineHeightAnimationFinishedListener [QuackTextStyle.lineHeight] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param textAlignAnimationFinishedListener [QuackTextStyle.textAlign] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 *
 * @return 애니메이션이 적용되고 있는 [QuackTextStyle] 객체
 */
@Composable
public fun animatedQuackTextStyleAsState(
    targetValue: QuackTextStyle,
    colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
    sizeAnimationFinishedListener: ((size: Float) -> Unit)? = null,
    letterSpacingAnimationFinishedListener: ((letterSpacing: Float) -> Unit)? = null,
    lineHeightAnimationFinishedListener: ((lineHeight: Float) -> Unit)? = null,
    textAlignAnimationFinishedListener: ((textAlign: TextAlign) -> Unit)? = null,
): QuackTextStyle {
    val targetColorAnimationState by animateQuackColorAsState(
        targetValue = targetValue.color,
        finishedListener = colorAnimationFinishedListener,
    )
    val targetSizeAnimationState by animateFloatAsState(
        targetValue = targetValue.size.value,
        animationSpec = QuackAnimationSpec(),
        finishedListener = sizeAnimationFinishedListener,
    )
    val targetLetterSpacingAnimationState by animateFloatAsState(
        targetValue = targetValue.letterSpacing.value,
        animationSpec = QuackAnimationSpec(),
        finishedListener = letterSpacingAnimationFinishedListener,
    )
    val targetLineHeightAnimationState by animateFloatAsState(
        targetValue = targetValue.lineHeight.value,
        animationSpec = QuackAnimationSpec(),
        finishedListener = lineHeightAnimationFinishedListener,
    )
    val targetTextAlignAnimationState by animateValueAsState(
        targetValue = targetValue.textAlign,
        typeConverter = TextAlign.VectorConverter,
        animationSpec = QuackAnimationSpec(),
        finishedListener = textAlignAnimationFinishedListener,
    )

    return QuackTextStyle(
        color = targetColorAnimationState,
        size = targetSizeAnimationState.toSp(),
        weight = targetValue.weight,
        letterSpacing = targetLetterSpacingAnimationState.toSp(),
        lineHeight = targetLineHeightAnimationState.toSp(),
        textAlign = targetTextAlignAnimationState,
    )
}
