/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */
@file:Suppress("SpellCheckingInspection")

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.collections.immutable.PersistentList

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
 * [FlowRow] 에 배치되기 때문에 부모의 width 를 넘어가면
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
        FlowRow(
            mainAxisSpacing = QuackTagRowContentSpacing,
            crossAxisSpacing = QuackTagRowLineSpacing,
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
