/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState

/**
 * QuackTextField 에 under bar 를 그리기 위해 사용됩니다.
 *
 * @param width under bar 의 두께
 * @param color under bar 의 색상
 * @param topPadding under bar 의 상단 여백
 *
 * @return under bar 를 그리기 위한 [drawWithContent] 가 적용된 [Modifier]
 */
internal fun Modifier.drawUnderBarWithAnimation(
    width: Dp,
    color: QuackColor,
    topPadding: Dp = Dp.Hairline,
) = composed {
    if (width == Dp.Hairline) return@composed this
    val animatedBrush by animateQuackColorAsState(
        targetValue = color,
    )
    drawWithContent {
        drawContent()
        val strokeWidth = width.toPx()
        val y = size.height - strokeWidth / 2 + topPadding.toPx()
        drawLine(
            brush = animatedBrush.toBrush(),
            start = Offset(
                x = 0f,
                y = y,
            ),
            end = Offset(
                x = size.width,
                y = y,
            ),
            strokeWidth = strokeWidth,
        )
    }
}
