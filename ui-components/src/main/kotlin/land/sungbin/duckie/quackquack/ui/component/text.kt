/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [text.kt] created by Ji Sungbin on 22. 8. 14. 오후 11:49
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.ui.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import land.sungbin.duckie.quackquack.ui.typography.QuackTextStyle

// TODO: AnimatedContent 를 사용해서 텍스트 변경 애니메이션 지원
@Composable
internal fun QuackText(
    modifier: Modifier = Modifier,
    text: String,
    style: QuackTextStyle,
) {
    BasicText(
        modifier = modifier,
        text = text,
        style = style.toComposeStyle()
    )
}