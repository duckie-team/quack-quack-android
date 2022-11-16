/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import team.duckie.quackquack.ui.color.QuackColor

/**
 * QuackTextField 및 QuackTextArea 에서 사용할 커서 색상을 제공합니다.
 *
 * 기본값은 [QuackColor.Black] 이며, [QuackTheme] 를 통해 덕키 테마로 제공됩니다.
 */
internal val LocalQuackTextFieldColors: ProvidableCompositionLocal<QuackTextFieldColors> =
    staticCompositionLocalOf {
        DefaultTextFieldColors
    }

/**
 * 꽥꽥에서 사용하는 컴포저블 테마를 제공합니다.
 * 이 테마에서는 다음과 같을 작업을 진행합니다.
 *
 * 1. OverscrollEffect 제거
 * 2. QuackTextField 및 QuackTextArea 에서 사용할 색상 테마 제공
 *
 * @param content 꽥꽥 디자인에 맞게 표시할 컴포저블 컨텐츠
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun QuackTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = QuackColor.DuckieOrange.composeColor,
            backgroundColor = QuackColor.DuckieOrange.change(
                alpha = 0.2f,
            ).composeColor,
        ),
        LocalQuackTextFieldColors provides DuckieTextFieldColors,
        content = content,
    )
}

/**
 * QuackTextField 에서 사용될 색상을 제공합니다.
 */
internal interface QuackTextFieldColors {
    /**
     * QuackTextField 및 QuackTextArea 에서 사용할 커서 색상을 정의합니다.
     */
    val textFieldCursorColor: QuackColor
}

/**
 * [QuackTextFieldColors] 의 기본값을 구현합니다.
 */
private object DefaultTextFieldColors : QuackTextFieldColors {
    /**
     * QuackTextField 및 QuackTextArea 에서 사용할 커서 색상
     */
    override val textFieldCursorColor = QuackColor.Black
}

/**
 * [QuackTextFieldColors] 를 덕키 테마에 맞게 구현합니다.
 */
private object DuckieTextFieldColors : QuackTextFieldColors {
    /**
     * QuackTextField 및 QuackTextArea 에서 사용할 커서 색상
     */
    override val textFieldCursorColor = QuackColor.DuckieOrange
}
