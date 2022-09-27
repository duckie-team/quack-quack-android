/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */
@file:Suppress("KDocFields") // 리팩토링 하면서 재거

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList


@Immutable
class QuackTagItem(
    val isSelected: Boolean,
    val text: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuackTagItem

        if (isSelected != other.isSelected) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isSelected.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}

@Composable
fun QuackTagRow(
    items: PersistentList<QuackTagItem>,
    onClickItem: (item: QuackTagItem) -> Unit,
) {
    FlowRow(
        contentSpacing = 8.dp,
    ) {
        items.forEach { item: QuackTagItem ->
            QuackTagRowItem(
                item = item,
                onClickItem = onClickItem,
            )
        }
    }
}

@Composable
private fun QuackTagRowItem(
    item: QuackTagItem,
    onClickItem: (item: QuackTagItem) -> Unit,
) {
    QuackSimpleTag(
        isSelected = item.isSelected,
        text = item.text,
        onClick = {
            onClickItem(item)
        },
    )
}

@Composable
private fun FlowRow(
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

