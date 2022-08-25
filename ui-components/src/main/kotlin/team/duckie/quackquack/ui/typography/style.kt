/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [style.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:37
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

// copy 사용을 방지하기 위해 data class 미사용
@Immutable
class QuackTextStyle private constructor(
    val color: QuackColor = QuackColor.Black,
    val size: TextUnit,
    val weight: FontWeight,
    val letterSpacing: TextUnit,
    val lineHeight: TextUnit,
) {
    @Stable
    private val suit = FontFamily(Font(R.font.suit_variable))

    @Stable
    internal fun toComposeStyle(
        textAlign: TextAlign = TextAlign.Center
    ) = TextStyle(
        color = color.value,
        fontSize = size,
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
            lineHeight = 26.sp
        )

        @Stable
        val HeadLine2 = QuackTextStyle(
            size = 16.sp,
            weight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp
        )

        @Stable
        val Title1 = QuackTextStyle(
            size = 16.sp,
            weight = FontWeight.Normal,
            letterSpacing = (-0.01).sp,
            lineHeight = 22.sp
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

    // TODO: 색상 변경 애니메이션 지원
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
