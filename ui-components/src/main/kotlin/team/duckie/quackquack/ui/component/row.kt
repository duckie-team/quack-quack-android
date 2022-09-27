/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */
@file:Suppress("KDocFields") // 리팩토링 하면서 재거

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun FlowLayout(
    modifier: Modifier = Modifier,
    contentSpacing: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    val measurePolicy = flowRowMeasurePolicy(
        contentSpacing = contentSpacing,
    )
    Layout(
        measurePolicy = measurePolicy,
        content = content,
        modifier = modifier,
    )
}

//measureables 측정할 element, 여기서는 내부 tag들
//constraints, 부모로부터 받은 min/max의 width, height 범위
private fun flowRowMeasurePolicy(
    contentSpacing: Dp,
) = MeasurePolicy { measurables: List<Measurable>, constraints: Constraints ->
    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        val placeables = measurables.map { measurable -> //각 Composable의 size를 측정하여 List로 저장
            measurable.measure(
                constraints = constraints,
            )
        }
        var yPosition = 0
        var xPosition = 0
        var maxY = 0
        placeables.forEach { placeable ->
            if (xPosition + placeable.width > constraints.maxWidth) {
                xPosition = 0
                yPosition += maxY
                maxY = 0
            }
            placeable.placeRelative(
                x = xPosition,
                y = yPosition
            )
            xPosition += (placeable.width + contentSpacing.roundToPx())
            maxY = maxY.coerceAtLeast(
                minimumValue = placeable.height,
            )
        }
    }
}

