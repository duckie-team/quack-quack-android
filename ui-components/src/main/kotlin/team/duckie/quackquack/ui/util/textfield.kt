/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import team.duckie.quackquack.ui.util.TextSelectionColors as QuackTextSelectionColors
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import team.duckie.quackquack.ui.color.QuackColor

/**
 * [ProvideTextSelectionColors] 와 함께 제공될 색상들 입니다.
 */
internal interface TextSelectionColors {
    /**
     * QuackTextField 및 QuackTextArea 에서 사용할 커서 색상을 정의합니다.
     * 꽥꽥에서는 커서의 색상으로 항상 [QuackColor.DuckieOrange] 를 사용합니다.
     */
    val textFieldCursorColor: QuackColor
}

/**
 * [TextSelectionColors] 를 구현합니다.
 */
private object DefaultTextSelectionColors : QuackTextSelectionColors {
    /**
     * QuackTextField 및 QuackTextArea 에서 사용할 커서 색상
     */
    override val textFieldCursorColor = QuackColor.DuckieOrange
}

/**
 * [LocalTextSelectionColors] 를 정해진 값으로 제공하는 컨테이너 입니다.
 *
 * @param content 제공된 [LocalTextSelectionColors] 와 함께 보여줄 컨텐츠.
 * 추가 색상 설정을 위해 [QuackTextSelectionColors] 을 함께 제공합니다.
 */
@Composable
internal fun ProvideTextSelectionColors(
    content: @Composable QuackTextSelectionColors.() -> Unit,
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = QuackColor.DuckieOrange.composeColor,
            backgroundColor = QuackColor.DuckieOrange.changeAlpha(
                alpha = 0.2f,
            ).composeColor,
        ),
    ) {
        DefaultTextSelectionColors.content()
    }
}
