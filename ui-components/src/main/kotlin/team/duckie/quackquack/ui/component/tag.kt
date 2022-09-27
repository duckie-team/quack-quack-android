/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

// TODO: 전체 리팩토링 필요
@file:Suppress(
    "KDocFields",
    "unused",
) // 리팩토링 하면서 재거

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.flowlayout.FlowRow
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackIconTagSpace = 8.dp
private val QuackTagRowTitleSpace = 12.dp
private val QuackTagRowFlowContentSpace = 8.dp

private val QuackTagHeight = 34.dp
private val QuackTagBorderWidth = 1.dp

internal val SimpleTagPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)
internal val IconTagPadding = PaddingValues(
    start = 16.dp,
    end = 10.dp,
    top = 8.dp,
    bottom = 8.dp,
)
internal val MainTabPadding = PaddingValues(
    horizontal = 18.dp,
)
internal val SubTabPadding = PaddingValues(
    horizontal = 16.dp,
)

internal val QuackTagShape = RoundedCornerShape(18.dp)
internal val QuackRectangleTagShape = RoundedCornerShape(12.dp)

/**
 * [QuackTagRow] 내부의 [QuackSimpleTag] 의 Argument 로 제공될 Class 입니다.
 * 탭이 선택됐을 시 탭 내부 텍스트와 선택 여부 값 수정을 위해 가변으로
 * 필드들을 제공합니다.
 *
 * @param isSelected Tag 가 선택되었는지 여부
 * @param text Tag 에 표시될 텍스트
 */
@Immutable
data class QuackTagItem(
    var isSelected: Boolean,
    var text: String,
)

// Why `@Suppress("PreferredImmutableCollections")` is not working???
// This typealias is just to avoid the `PreferredImmutableCollections` issue.
// If the suppress issue is resolved, this typealias should be removed.
private typealias ImmutableQuackTagItemButMutable = List<QuackTagItem>

/**
 * 덕키의 TagRow 를 구현합니다. 내부적으로 [FlowRow] 를 이용하며, 항상 현재 화면의
 * 가로 길이만큼 width 가 지정됩니다.
 *
 * @param title QuackTagRow 상단에 표시될 제목 Text
 * @param items Tag 에 표시될 Text 의 리스트. [QuackTagItem] 의 [QuackTagItem.isSelected] 항목은
 * 자주 바뀔 것으로 예상되어 [ImmutableCollection] 가 아닌 일반 [Collection] 으로 받습니다.
 * @param onClick 사용자가 Tag 를 클릭했을 때 호출되는 람다
 *
 * @see ImmutableQuackTagItemButMutable
 */
@Composable
fun QuackTagRow(
    title: String,
    items: ImmutableQuackTagItemButMutable,
    onClick: (
        index: Int,
    ) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        QuackTitle2(
            text = title,
        )
        FlowRow(
            modifier = Modifier.padding(
                top = QuackTagRowTitleSpace,
            ),
            mainAxisSpacing = QuackTagRowFlowContentSpace,
            crossAxisSpacing = QuackTagRowFlowContentSpace,
        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .tagShape(
                            isSelected = item.isSelected,
                            shape = QuackRectangleTagShape,
                        )
                        .padding(
                            paddingValues = SimpleTagPadding,
                        )
                        .quackClickable(
                            onClick = {
                                onClick(
                                    /*index = */
                                    index,
                                )
                            },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    /*QuackBody1(
                        text = text,
                        color = getTagTextColor(
                            isSelected
                        ),
                    )*/
                }
            }
        }
    }
}

@Composable
fun QuackBoldTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .tagShape(
                isSelected = isSelected,
                shape = QuackTagShape,
            )
            .padding(
                paddingValues = SimpleTagPadding
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        QuackTitle2(
            text = text,
            color = getTagTextColor(
                isSelected
            ),
        )
    }
}

@Composable
fun QuackIconTag(
    isSelected: Boolean,
    text: String,
    icon: QuackIcon,
    onClickTag: () -> Unit,
    onClickIcon: () -> Unit,
) {
    Box(
        modifier = Modifier
            .tagShape(
                isSelected = isSelected,
                shape = QuackTagShape,
            )
            .background(
                color = getTagBackground(
                    isSelected = isSelected,
                ).composeColor,
            )
            .padding(
                paddingValues = IconTagPadding,
            )
            .quackClickable(
                onClick = onClickTag,
            ),
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
        ) {
            QuackTitle2(
                text = text,
                color = getIconTagTextColor(
                    isSelected = isSelected,
                ),
            )
            Spacer(
                modifier = Modifier.width(
                    width = QuackIconTagSpace,
                ),
            )
            InternalQuackImage(
                icon = icon,
                tint = getIconColor(
                    isSelected
                ),
                onClick = onClickIcon,
            )
        }
    }
}

private fun Modifier.tagShape(
    isSelected: Boolean,
    shape: Shape,
) = this
    .height(
        height = QuackTagHeight,
    )
    .clip(
        shape = shape,
    )
    .background(
        color = QuackColor.White.composeColor,
    )
    .border(
        width = QuackTagBorderWidth,
        color = getTagBorderColor(isSelected).composeColor,
        shape = shape,
    )

private fun getTagBorderColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.Gray3
}

private fun getTagTextColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.Black
}

private fun getTagBackground(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.White
}

private fun getIconTagTextColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.White
    else -> QuackColor.Black
}

private fun getIconColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.White
    else -> QuackColor.Gray2
}
