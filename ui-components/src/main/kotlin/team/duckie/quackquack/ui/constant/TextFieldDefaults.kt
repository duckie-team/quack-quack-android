/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TextFieldDefaults.kt] created by Ji Sungbin on 22. 8. 21. 오후 5:48
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.constant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

@Stable
interface QuackTextFieldColors {
    @Composable
    fun textColor(): QuackColor

    @Composable
    fun backgroundColor(): QuackColor

    @Composable
    fun placeholderColor(): QuackColor

    @Composable
    fun trailingIconColor(): QuackColor

    @Composable
    fun cursorColor(): QuackColor

    @Composable
    fun indicatorColor(
        isError: Boolean,
    ): QuackColor

    @Composable
    fun changeBackgroundColor(
        backgroundColor: QuackColor,
    ): QuackTextFieldColors
}

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

    internal val InnerTextFieldShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = SmallShape

    @Composable
    internal fun textFieldColors(
        textColor: QuackColor = QuackColor.Black,
        backgroundColor: QuackColor = QuackColor.White,
        placeholderColor: QuackColor = QuackColor.Greyish,
        trailingIconColor: QuackColor = QuackColor.Greyish,
        cursorColor: QuackColor = QuackColor.DuckieOrange,
        indicatorColor: QuackColor = QuackColor.Unspecified,
        errorIndicatorColor: QuackColor = QuackColor.OrangeRed,
    ): QuackTextFieldColors =
        QuackDefaultTextFieldColors(
            textColor = textColor,
            backgroundColor = backgroundColor,
            placeholderColor = placeholderColor,
            trailingIconColor = trailingIconColor,
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
        isError: Boolean
    ): BorderStroke = BorderStroke(
        width = width,
        color = colors.indicatorColor(
            isError = isError
        ).value
    )
}

@Immutable
private class QuackDefaultTextFieldColors(
    private val textColor: QuackColor,
    private val backgroundColor: QuackColor,
    private val placeholderColor: QuackColor,
    private val trailingIconColor: QuackColor,
    private val cursorColor: QuackColor,
    private val indicatorColor: QuackColor,
    private val errorIndicatorColor: QuackColor,
) : QuackTextFieldColors {

    @Composable
    override fun textColor(): QuackColor = textColor

    @Composable
    override fun backgroundColor(): QuackColor = backgroundColor

    @Composable
    override fun placeholderColor(): QuackColor = placeholderColor

    @Composable
    override fun trailingIconColor(): QuackColor = trailingIconColor

    @Composable
    override fun cursorColor(): QuackColor = cursorColor

    @Composable
    override fun indicatorColor(isError: Boolean): QuackColor {
        return if (isError) {
            errorIndicatorColor
        } else {
            indicatorColor
        }
    }

    @Composable
    override fun changeBackgroundColor(
        backgroundColor: QuackColor,
    ): QuackTextFieldColors =
        QuackDefaultTextFieldColors(
            textColor = textColor,
            backgroundColor = backgroundColor,
            placeholderColor = placeholderColor,
            trailingIconColor = trailingIconColor,
            cursorColor = cursorColor,
            indicatorColor = indicatorColor,
            errorIndicatorColor = errorIndicatorColor,
        )
}
