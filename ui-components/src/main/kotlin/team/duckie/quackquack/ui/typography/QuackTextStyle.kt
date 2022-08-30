/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackTextStyle.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:37
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.quackquack.ui.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.color.QuackColor

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
class QuackTextStyle private constructor(
    val color: QuackColor = QuackColor.Black,
    val size: TextUnit,
    val weight: FontWeight,
    val letterSpacing: TextUnit,
    val lineHeight: TextUnit,
) {
    private val suit = FontFamily(Font(R.font.suit_variable))

    @Stable
    internal fun asComposeStyle() = TextStyle(
        color = color.value,
        fontSize = size,
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
