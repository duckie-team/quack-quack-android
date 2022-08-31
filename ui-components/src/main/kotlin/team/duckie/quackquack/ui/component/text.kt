/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [text.kt] created by Ji Sungbin on 22. 8. 14. 오후 11:49
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import team.duckie.quackquack.ui.typography.QuackTextStyle

/**
 * 주어진 조건에 따라 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param text 표시할 텍스트 내용
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 */
@Composable
internal fun QuackText(
    modifier: Modifier = Modifier,
    text: String,
    style: QuackTextStyle,
) {
    BasicText(
        modifier = modifier,
        text = text,
        style = style.asComposeStyle(),
    )
}
