/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

private val QuackDividerThickness = 1.dp
private val QuackDividerColor = QuackColor.Gray3

/**
 * 덕키의 구분선 입니다.
 * 부모 컴포넌트의 가로 길이에 꽉 차게 그려집니다.
 *
 * @return Unit
 */
@Composable
@NonRestartableComposable
public fun QuackDivider(): Unit = Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(
            height = QuackDividerThickness,
        )
        .background(
            color = QuackDividerColor.composeColor,
        )
)
