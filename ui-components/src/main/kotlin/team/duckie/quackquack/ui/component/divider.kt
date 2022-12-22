/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

/**
 * QuackDivider 를 그리기 위한 리소스들을 정의합니다.
 */
private object QuackDividerDefaults {
    val Color = QuackColor.Gray3
    val Height = 1.dp
}

/**
 * 덕키에서 사용되는 구분선(divider)을 그립니다.
 *
 * @param modifier 이 컴포넌트에 사용할 [Modifier]
 */
@Composable
public fun QuackDivider(modifier: Modifier = Modifier) {
    with(QuackDividerDefaults) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(Height)
                .background(color = Color.composeColor)
        )
    }
}
