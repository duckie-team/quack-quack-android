/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */
@file:Suppress("SpellCheckingInspection", "KDocFields") // 리팩토링 하면서 재거

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

val QuackTitleFlowRowSpacing = 12.dp

val QuackTagRowLineSpacing = 8.dp
val QuackTagRowContentSpacing = 8.dp

/**
 * [QuackTagRow] 내부의 [QuackSimpleTag] 의 Argument 로 제공될 Class 입니다.
 *
 * @param isSelected Tag 가 선택되었는지 여부
 * @param text Tag에 표시될 Text
 *
 * @constructor [isSelected], [text]를 초기화 하면서 생성합니다.
 */
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

/**
 * 덕키의 TagRow 를 구현합니다.
 *
 * [items] 의 원소 개수만큼 [QuackTagRowItem] 을 그립니다.
 * [QuackFlowRow] 에 배치되기 때문에 부모의 width 를 넘어가면
 * 다음 행에 [QuackTagRowItem] 를 배치합니다.
 *
 * @param title QuackTagRow 상단에 표시될 제목 Text
 * @param items Tag 에 표시될 Text의 리스트
 * @param onClickItem 사용자가 Tag를 클릭했을 때 호출되는 콜백
 */
@Composable
fun QuackTagRow(
    title: String,
    items: PersistentList<QuackTagItem>,
    onClickItem: (
        item: QuackTagItem,
    ) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = QuackTitleFlowRowSpacing,
        ),
    ) {
        QuackTitle2(
            text = title,
        )
        QuackFlowRow(
            contentSpacing = QuackTagRowContentSpacing,
            rowSpacing = QuackTagRowLineSpacing,
        ) {
            items.forEach { item: QuackTagItem ->
                QuackTagRowItem(
                    item = item,
                    onClickItem = onClickItem,
                )
            }
        }
    }

}

/**
 * [QuackTagRow] 의 내부에 배치될 Composable 입니다.
 * [QuackSimpleTag] 를 Deligate 합니다.
 *
 * @param item Tag 에 표시될 Text의 리스트
 * @param onClickItem 사용자가 Tag를 클릭했을 때 호출되는 콜백
 */
@Composable
@NonRestartableComposable
private fun QuackTagRowItem(
    item: QuackTagItem,
    onClickItem: (
        item: QuackTagItem,
    ) -> Unit,
) = QuackSimpleTag(
    isSelected = item.isSelected,
    text = item.text,
    onClick = {
        onClickItem(item)
    },
)

/**
 * Duckie 컴포넌트에 사용될 FlowLayout 입니다.
 *
 * [contentSpacing] 과 [rowSpacing] 을 지정하여 요소들의 간격과,
 * 행 간격을 조절할 수 있습니다.
 *
 * @param contentSpacing FlowRow 의 각 Content 들의 간격
 * @param rowSpacing FlowRow 의 각 Row 들의 간격
 * @param content FlowRow 내부에 들어갈 Composable
 */
@Composable
internal fun QuackFlowRow(
    contentSpacing: Dp = 0.dp,
    rowSpacing: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    val measurePolicy = flowRowMeasurePolicy(
        contentSpacing = contentSpacing,
        rowSpacing = rowSpacing,
    )
    Layout(
        measurePolicy = measurePolicy,
        content = content,
    )
}

/**
 * 수평 흐름으로 하위 항목을 배열하는 레이아웃 모델입니다.
 *
 * 내부 element 를 순회하면서, element 들의 width 에 [contentSpacing] 을 더해가면서
 * 상대 위치를 배정합니다. 만약 부모의 width 를 넘어간다면 element 의 height 의 최댓값 에 [rowSpacing] 을
 * 더해서 다음 행과의 간격을 계산하고 다음 열에 배치됩니다.
 *
 * @param contentSpacing FlowRow 의 각 Content 들의 간격
 * @param rowSpacing FlowRow 의 각 Row 들의 간격
 * @return [MeasurePolicy]
 */
//measureables 측정할 element, 여기서는 내부 tag들
//constraints, 부모로부터 받은 min/max의 width, height 범위
private fun flowRowMeasurePolicy(
    contentSpacing: Dp,
    rowSpacing: Dp,
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
                yPosition += (maxY + rowSpacing.roundToPx())
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
