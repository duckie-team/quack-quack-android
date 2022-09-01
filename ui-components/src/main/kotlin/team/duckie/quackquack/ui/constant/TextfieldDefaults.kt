/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TextfieldDefaults.kt] created by EvergreenTree97 on 22. 8. 21. 오후 5:48
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.constant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.applyQuackSize

/**
 * 여러 상태의 텍스트 필드에 사용되는 spacing, padding, shape color 등의
 * 기본 값을 가지고 있는 object 입니다.
 *
 * @see QuackTextFieldColors
 */
@Immutable
object QuackTextFieldDefaults {
    /**
     * TextField에 사용되는 기본 높이입니다.
     */
    internal val height = 44.dp

    /**
     * TextField에 사용되는 기본 icon 간격입니다.
     */
    internal val iconSpacing = 8.dp

    /**
     * TextField에 사용되는 small Icon에 대한 간격입니다.
     */
    internal val smallIconSpacing = 4.dp

    /**
     * TextField에 사용되는 errorText와 TextField에 대한 간격입니다.
     */
    internal val errorTextSpacing = 4.dp

    /**
     * TextField에 사용되는 lengthText들의 간격입니다.
     */
    internal val lengthTextSpacing = 2.dp

    /**
     * TextField에 사용되는 icon의 horizontal padding 입니다.
     */
    internal val iconHorizontalPadding = 12.dp

    /**
     * TextField에 사용되는 icon의 vertical padding 입니다.
     */
    internal val iconVerticalPadding = 14.dp

    /**
     * TextField 내부 컨텐츠의 기본 horizontal padding 입니다.
     */
    private val horizontalPadding = 0.dp

    /**
     * TextField 내부 컨텐츠의 기본 top padding 입니다.
     */
    private val topPadding = 18.dp

    /**
     * TextField 내부 컨텐츠의 기본 bottom padding 입니다.
     */
    private val bottomPadding = 8.dp

    /**
     * TextField이 에러일 때 표시되는 bottom line의 굵기 입니다.
     */
    private val errorIndicatorThickness = 1.dp


    /**
     * TextField의 배경에 사용되는 기본 모양입니다.
     */
    internal val TextFieldShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = RectangleShape

    /**
     * TextField에 사용되는 기본 입력 텍스트, 배경 및 내용 등의 색상을 나타내는
     * 텍스트 필드 색상을 작성합니다.
     * @see QuackTextFieldColors
     */
    @Composable
    internal fun textFieldColors(
        textColor: QuackColor = QuackColor.Black,
        unFocusedColor: QuackColor = QuackColor.Gray2,
        errorTextColor: QuackColor = QuackColor.OrangeRed,
        backgroundColor: QuackColor = QuackColor.White,
        placeholderColor: QuackColor = QuackColor.Gray2,
        trailingIconColor: QuackColor = QuackColor.DuckieOrange,
        unFocusedTrailingIconColor: QuackColor = QuackColor.Gray2,
        cursorColor: QuackColor = QuackColor.DuckieOrange,
        indicatorColor: QuackColor = QuackColor.Unspecified,
        errorIndicatorColor: QuackColor = QuackColor.OrangeRed,
    ): QuackTextFieldColors =
        QuackTextFieldColors(
            textColor = textColor,
            unFocusedTextColor = unFocusedColor,
            errorTextColor = errorTextColor,
            backgroundColor = backgroundColor,
            placeholderColor = placeholderColor,
            trailingIconColor = trailingIconColor,
            unFocusedTrailingIconColor = unFocusedTrailingIconColor,
            cursorColor = cursorColor,
            indicatorColor = indicatorColor,
            errorIndicatorColor = errorIndicatorColor,
        )

    /**
     * TextField 내부 레이블이 존재할 때, 기본 padding이 적용됩니다.
     *
     * 만약, [top]이 레이블의 마지막 기준선 보다 작으면, 텍스트필드 상단과의 공백이 없습니다.
     * @see PaddingValues
     */
    @Composable
    internal fun textFieldPadding(
        start: Dp = horizontalPadding,
        end: Dp = horizontalPadding,
        top: Dp = topPadding,
        bottom: Dp = bottomPadding,
    ): PaddingValues = PaddingValues(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
    )

    /**
     * TextField 지시선의 width와 color를 제공하는 메서드 입니다.
     *
     * [isError] 여부에 따라 다른 색상을 제공할 수 있습니다.
     *
     * @param width TextField의 지시선 두께
     * @param colors TextField의 지시선 색상
     * @param isError TextField의 값이 현재 오류인지 여부
     */
    @Composable
    internal fun indicatorStroke(
        width: Dp = errorIndicatorThickness,
        colors: QuackTextFieldColors = textFieldColors(),
        isError: Boolean,
    ): BorderStroke = BorderStroke(
        width = width,
        color = colors.indicatorColor(
            isError = isError,
        ).value
    )
}

@Immutable
internal class QuackTextFieldColors(
    private val textColor: QuackColor,
    private val unFocusedTextColor: QuackColor,
    private val errorTextColor: QuackColor,
    private val placeholderColor: QuackColor,
    private val trailingIconColor: QuackColor,
    private val unFocusedTrailingIconColor: QuackColor,
    private val backgroundColor: QuackColor,
    private val cursorColor: QuackColor,
    private val indicatorColor: QuackColor,
    private val errorIndicatorColor: QuackColor,
) {
    @Composable
    fun textColor(
        isFocused: Boolean,
    ): QuackColor {
        return if (isFocused) {
            textColor
        } else {
            unFocusedTextColor
        }
    }

    @Composable
    fun errorTextColor() = errorTextColor

    @Composable
    fun placeholderColor() = placeholderColor

    @Composable
    fun trailingIconColor(
        isFocused: Boolean = false,
    ): QuackColor {
        return if (isFocused) {
            trailingIconColor
        } else {
            unFocusedTrailingIconColor
        }
    }

    @Composable
    fun backgroundColor() = backgroundColor

    @Composable
    fun cursorColor() = cursorColor

    @Composable
    fun indicatorColor(
        isError: Boolean,
    ): QuackColor {
        return if (isError) {
            errorIndicatorColor
        } else {
            indicatorColor
        }
    }
}
