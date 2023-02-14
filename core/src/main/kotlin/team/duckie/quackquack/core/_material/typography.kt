/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.core._material

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import team.duckie.quackquack.core.R
import team.duckie.quackquack.core.util.AllowMagicNumber

/**
 * 덕키에서 사용할 텍스트 스타일을 정의합니다.
 *
 * copy를 통한 값 변경은 덕키 스타일 가이드의 텍스트 스타일 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class가 아닌 class를 사용합니다.
 *
 * @param color 텍스트 색상. 기본값은 [QuackColor.Black] 입니다.
 * @param size 텍스트 한 글자 크기
 * @param weight 텍스트 굵기
 * @param letterSpacing 텍스트 자간
 * @param lineHeight 텍스트 줄 크기
 * @param textAlign 텍스트 align. 기본값은 Start 입니다.
 */
@Immutable
public class QuackTypography(
    public val color: QuackColor = QuackColor.Black,
    public val size: TextUnit,
    public val weight: FontWeight,
    public val letterSpacing: TextUnit,
    public val lineHeight: TextUnit,
    public val textAlign: TextAlign = TextAlign.Start,
) {
    private val suit = FontFamily(
        Font(resId = R.font.quack_suit_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quack_suit_medium, weight = FontWeight.Medium),
        Font(resId = R.font.quack_suit_regular, weight = FontWeight.Regular),
    )

    /**
     * [QuackTypography] 을 컴포즈 Text 컴포넌트에 사용하기 위해
     * [TextStyle] 로 변환합니다.
     */
    @Stable
    public fun asComposeStyle(): TextStyle {
        return TextStyle(
            color = color.value,
            fontSize = size,
            fontFamily = suit,
            fontWeight = weight,
            letterSpacing = letterSpacing,
            textAlign = textAlign,
            lineHeight = lineHeight,
        )
    }

    /**
     * 정해진 [QuackTypography]에서 일부 값만 변경이 필요할 때가 있습니다.
     * 이를 대응하기 위해 [QuackTypography]에서 변경을 허용하는
     * 필드만 변경하여 새로운 [QuackTypography]를 반환하는 API를 구현합니다.
     *
     * 색상과 정렬의 변경은 고정된 디자인의 목적을 해치지 않을 것으로 예상하여
     * public으로 노출합니다.
     *
     * @param color 변경할 색상
     * @param textAlign 변경할 textAlign
     *
     * @return 새로운 스타일이 적용된 [QuackTypography]
     */
    @Stable
    public fun change(
        color: QuackColor = this.color,
        textAlign: TextAlign = this.textAlign,
    ): QuackTypography {
        return if (color == this.color && textAlign == this.textAlign) {
            this
        } else {
            QuackTypography(
                color = color,
                size = size,
                weight = weight,
                letterSpacing = letterSpacing,
                lineHeight = lineHeight,
                textAlign = textAlign,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QuackTypography) return false

        if (color != other.color) return false
        if (size != other.size) return false
        if (weight != other.weight) return false
        if (letterSpacing != other.letterSpacing) return false
        if (lineHeight != other.lineHeight) return false
        if (textAlign != other.textAlign) return false
        if (suit != other.suit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + letterSpacing.hashCode()
        result = 31 * result + lineHeight.hashCode()
        result = 31 * result + textAlign.hashCode()
        result = 31 * result + suit.hashCode()
        return result
    }

    public companion object {
        public val Large1: QuackTypography = QuackTypography(
            size = 24.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Left,
        )

        public val HeadLine1: QuackTypography = QuackTypography(
            size = 20.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 26.sp,
        )

        public val HeadLine2: QuackTypography = QuackTypography(
            size = 16.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        public val Title1: QuackTypography = QuackTypography(
            size = 16.sp,
            weight = FontWeight.Regular,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        public val Title2: QuackTypography = QuackTypography(
            size = 14.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        public val Subtitle: QuackTypography = QuackTypography(
            size = 14.sp,
            weight = FontWeight.Medium,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        public val Subtitle2: QuackTypography = QuackTypography(
            size = 12.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        public val Body1: QuackTypography = QuackTypography(
            size = 14.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        public val Body2: QuackTypography = QuackTypography(
            size = 12.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        public val Body3: QuackTypography = QuackTypography(
            size = 10.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 13.sp,
        )
    }
}

/**
 * [FontWeight] 에 Regular를 정의합니다.
 *
 * [FontWeight.Normal] 는 `FontWeight.Regular`와 동일합니다.
 */
public inline val FontWeight.Companion.Regular: FontWeight get() = Normal

/**
 * Float를 Sp로 변환합니다.
 */
@OptIn(ExperimentalUnitApi::class)
@Suppress("NOTHING_TO_INLINE") // JVM 최적화를 위해 인라인
private inline fun Float.toSp() = TextUnit(value = this, type = TextUnitType.Sp)

/**
 * [List]에 대한 component 정의가 5까지만 있어서 6번째 component를 추가로 정의합니다.
 */
@AllowMagicNumber(because = "6번째 요소는 항상 5번째 인덱스를 갖기 때문에 5로 고정합니다.")
private operator fun <T> List<T>.component6() = get(index = 5)

/**
 * [TextAlign]의 [TwoWayConverter]를 구현합니다.
 *
 * [TextAlign]의 생성자와 필드가 다 internal 이기 때문에
 * [TextAlign]의 인자 값으로 될 수 있는 1~6 까지의 [Int] 값을
 * 애니메이션하여 각각 값에 맞는 [TextAlign]을 찾아서 생성하는
 * 식으로 구현하였습니다.
 */
@Suppress("PrivatePropertyName")
private val TextAlign.Companion.VectorConverter
    get() = object : TwoWayConverter<TextAlign, AnimationVector1D> {
        override val convertFromVector: (vector: AnimationVector1D) -> TextAlign
            get() = { vector ->
                @AllowMagicNumber(because = "TextAlign 의 range 를 나타냅니다.")
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
 * [QuackTypography]에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * [QuackTypography.color], [QuackTypography.size], [QuackTypography.weight],
 * [QuackTypography.letterSpacing], [QuackTypography.lineHeight], [QuackTypography.textAlign]
 * 에 애니메이션을 적용합니다.
 *
 * 현재 weight 애니메이션이 적용되지 않습니다. weight는 100 단위로 증가하기
 * 때문에 100~n00 으로 애니메이션 되는 weight가 구현돼 있지 않아 생기는 이슈 입니다.
 *
 * animationSpec으로 항상 [QuackAnimationSpec]을 사용합니다.
 *
 * @param targetValue 변경을 감지할 [QuackTypography]
 * @param colorAnimationFinishedListener [QuackTypography.color] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param sizeAnimationFinishedListener [QuackTypography.size] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param letterSpacingAnimationFinishedListener [QuackTypography.letterSpacing] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param lineHeightAnimationFinishedListener [QuackTypography.lineHeight] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 * @param textAlignAnimationFinishedListener [QuackTypography.textAlign] 에 대한 애니메이션이 끝났을 때 호출될 콜백
 *
 * @return 애니메이션이 적용되고 있는 [QuackTypography] 객체
 */
@Composable
public fun animatedQuackTextStyleAsState(
    targetValue: QuackTypography,
    colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
    sizeAnimationFinishedListener: ((size: Float) -> Unit)? = null,
    letterSpacingAnimationFinishedListener: ((letterSpacing: Float) -> Unit)? = null,
    lineHeightAnimationFinishedListener: ((lineHeight: Float) -> Unit)? = null,
    textAlignAnimationFinishedListener: ((textAlign: TextAlign) -> Unit)? = null,
): QuackTypography {
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

    return QuackTypography(
        color = targetColorAnimationState,
        size = targetSizeAnimationState.toSp(),
        weight = targetValue.weight,
        letterSpacing = targetLetterSpacingAnimationState.toSp(),
        lineHeight = targetLineHeightAnimationState.toSp(),
        textAlign = targetTextAlignAnimationState,
    )
}
