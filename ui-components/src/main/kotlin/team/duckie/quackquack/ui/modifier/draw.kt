/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [draw.kt] created by EvergreenTree97 on 22. 8. 25. 오후 11:25
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect

@Stable
internal fun Modifier.bottomIndicatorLine(
    indicatorBorder: BorderStroke
): Modifier = drawWithContent {
    drawContent()
    clipRect {
        val strokeWidth = indicatorBorder.width.value * density
        val y = size.height
        drawLine(
            brush = indicatorBorder.brush,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square,
            start = Offset.Zero.copy(y = y),
            end = Offset(x = size.width, y = y)
        )
    }
}
