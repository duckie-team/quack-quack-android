/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import land.sungbin.kotlin.dataclass.nocopy.NoCopy

/**
 * 덕키에서 사용할 텍스트 스타일을 정의합니다.
 *
 * copy를 이용한 값 변경은 덕키 스타일 가이드의 텍스트 스타일 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class가 아닌 class를 사용합니다.
 *
 * @param color 텍스트 색상. 기본값은 [QuackColor.Black] 입니다.
 * @param size 텍스트 한 글자 크기
 * @param weight 텍스트 굵기
 * @param letterSpacing 텍스트 자간
 * @param lineHeight 텍스트 줄 크기
 * @param textAlign 텍스트 align. 기본값은 Start 입니다.
 */
@NoCopy
@Immutable
public data class QuackTypography(
    public val color: QuackColor = QuackColor.Black,
    public val size: TextUnit,
    public val weight: FontWeight,
    public val letterSpacing: TextUnit,
    public val lineHeight: TextUnit,
    public val textAlign: TextAlign = TextAlign.Start,
) {
    @Stable
    public val suit: FontFamily = FontFamily(
        Font(resId = R.font.quack_suit_bold, weight = FontWeight.Bold),
        Font(resId = R.font.quack_suit_medium, weight = FontWeight.Medium),
        Font(resId = R.font.quack_suit_regular, weight = FontWeight.Regular),
    )

    /**
     * [QuackTypography]을 컴포즈 Text 컴포넌트에 사용하기 위해
     * [TextStyle]로 변환합니다.
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
     * 이를 대응하기 위해 [QuackTypography]에서 변경을 허용하는 필드만 변경하여
     * 새로운 [QuackTypography]를 반환하는 API를 구현합니다.
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

    public companion object {
        @Stable
        public val Large1: QuackTypography = QuackTypography(
            size = 24.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Left,
        )

        @Stable
        public val HeadLine1: QuackTypography = QuackTypography(
            size = 20.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 26.sp,
        )

        @Stable
        public val HeadLine2: QuackTypography = QuackTypography(
            size = 16.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        @Stable
        public val Title1: QuackTypography = QuackTypography(
            size = 16.sp,
            weight = FontWeight.Regular,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp,
        )

        @Stable
        public val Title2: QuackTypography = QuackTypography(
            size = 14.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        @Stable
        public val Subtitle: QuackTypography = QuackTypography(
            size = 14.sp,
            weight = FontWeight.Medium,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        @Stable
        public val Subtitle2: QuackTypography = QuackTypography(
            size = 12.sp,
            weight = FontWeight.Bold,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        @Stable
        public val Body1: QuackTypography = QuackTypography(
            size = 14.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 20.sp,
        )

        @Stable
        public val Body2: QuackTypography = QuackTypography(
            size = 12.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
        )

        @Stable
        public val Body3: QuackTypography = QuackTypography(
            size = 10.sp,
            weight = FontWeight.Regular,
            letterSpacing = 0.sp,
            lineHeight = 13.sp,
        )
    }
}

/**
 * [FontWeight]에 Regular를 정의합니다.
 *
 * [FontWeight.Normal]는 `FontWeight.Regular`와 동일합니다.
 */
@Stable
public inline val FontWeight.Companion.Regular: FontWeight get() = Normal

/**
 * [Float]를 [sp]로 변환합니다.
 */
@Suppress("NOTHING_TO_INLINE")
@Stable
public inline fun Float.toSp(): TextUnit = TextUnit(value = this, type = TextUnitType.Sp)

/**
 * [List]의 component 정의가 5까지만 있어서 6번째 component를 추가로 정의합니다.
 */
@Suppress("MagicNumber")
public operator fun <T> List<T>.component6(): T = get(index = 5)
