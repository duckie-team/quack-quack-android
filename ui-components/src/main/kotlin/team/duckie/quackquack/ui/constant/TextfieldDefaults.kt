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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

@Immutable
object QuackTextFieldDefaults {
    internal val height = 44.dp

    internal val iconSpacing = 8.dp
    internal val smallIconSpacing = 4.dp
    internal val errorTextSpacing = 4.dp
    internal val lengthTextSpacing = 2.dp

    internal val iconHorizontalPadding = 12.dp
    internal val iconVerticalPadding = 14.dp

    private val horizontalPadding = 0.dp
    private val topPadding = 18.dp
    private val bottomPadding = 8.dp

    private val errorIndicatorThickness = 1.dp

    internal val TextFieldShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = RectangleShape

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
