/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [tab.kt] created by goddoro on 22. 8. 21. 오후 4:26
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.typography.QuackSubtitle
import team.duckie.quackquack.ui.typography.QuackTitle2

private val QuackTabDividerHeight = 1.dp
private val QuackTabUnderBarHeight = 2.dp

private val QuackTabVerticalPadding = 9.dp

private val QuackMainTabSpacedBy = 28.dp
private val QuackMainTabTextInnerPadding = 2.dp

/**
 * QuackMainTab 을 구현합니다.
 *
 * QuackMainTab 은 탭 사이 간격이 항상 28dp 로 일정하며,
 * 현재 탭 언더바의 가로 사이즈는 현재 탭의 사이즈에 따라 유동적으로 변화합니다.
 * 현재 탭 언더바의 가로 사이즈는
 *
 * ```
 * 현재 탭 텍스트가 차지하는 가로 사이즈 + 2dp * 2
 * ```
 *
 * 로 구할 수 있습니다.
 *
 * 전체 탭 언더바의 가로 사이즈는 항상 전체 탭의 가로 사이즈와 동일합니다.
 *
 * 첫 번째와 마지막 탭은 화면에서 일정 사이즈 만큼 패딩이 적용되야 합니다.
 *
 * @param titles 탭 제목 리스트
 * @param tabStartVerticalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
fun QuackMainTab(
    titles: List<String>,
    tabStartVerticalPadding: Dp = 16.dp,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    val titleSize = remember(titles) {
        titles.size
    }
    var tabHeight by remember(titles) {
        mutableStateOf(0)
    }
    val tabUnderBarXOffsets = remember(titles) {
        mutableStateListOf(*Array(titleSize) { 0 })
    }
    val currentTabUnderBarXOffsetAnimation by animateIntAsState(
        targetValue = tabUnderBarXOffsets[selectedTabIndex],
        animationSpec = quackTween(),
    )
    val tabWidths = remember(titles) {
        mutableStateListOf(*Array(titleSize) { 0.dp })
    }
    val currentTabUnderBarWidthAnimation by animateDpAsState(
        targetValue = tabWidths[selectedTabIndex] + QuackMainTabTextInnerPadding * 2,
        animationSpec = quackTween(),
    )

    Box(
        modifier = Modifier.wrapContentSize(),
    ) {
        QuackTabDivider()
        QuackMainTabTextLazyRow(
            tabTitles = titles,
            tabStartVerticalPadding = tabStartVerticalPadding,
            onTabSizeChanged = { size ->
                tabHeight = size.height
            },
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            onSelectedTabPositionChanged = { index, size, offset ->
                tabUnderBarXOffsets[index] =
                    offset.x.roundToInt() - QuackMainTabTextInnerPadding.roundToPx()
                tabWidths[index] = size.width.toDp()
            }
        )
        QuackSelectedTabUnderBar(
            offsetProvider = {
                IntOffset(
                    x = currentTabUnderBarXOffsetAnimation,
                    y = tabHeight,
                )
            },
            widthProvider = { currentTabUnderBarWidthAnimation },
            color = QuackColor.DuckieOrange,
        )
    }
}

/**
 * QuackMainTab 에서 사용되는 탭 제목을 그리는 컴포넌트입니다.
 *
 * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
 * Row 가 아닌 LazyRow 로 구현하였습니다.
 */
@Composable
private fun QuackMainTabTextLazyRow(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    tabStartVerticalPadding: Dp,
    onTabSizeChanged: Density.(
        size: IntSize,
    ) -> Unit,
    selectedTabIndex: Int,
    onTabSelected: (
        index: Int,
    ) -> Unit,
    onSelectedTabPositionChanged: Density.(
        index: Int,
        size: IntSize,
        position: Offset,
    ) -> Unit,
) {
    val density = LocalDensity.current

    LazyRow(
        modifier = modifier
            .wrapContentSize()
            .onSizeChanged { size ->
                density.onTabSizeChanged(size)
            },
        contentPadding = PaddingValues(
            vertical = tabStartVerticalPadding,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackMainTabSpacedBy,
            alignment = Alignment.CenterHorizontally,
        )
    ) {
        itemsIndexed(
            items = tabTitles,
            key = { _, title -> title },
        ) { index, title ->
            val tabModifier = remember {
                Modifier
                    .padding(
                        vertical = QuackTabVerticalPadding,
                        horizontal = QuackMainTabTextInnerPadding,
                    )
                    .clickable {
                        onTabSelected(index)
                    }
                    .onGloballyPositioned { layoutCoordinates ->
                        density.onSelectedTabPositionChanged(
                            /*index = */
                            index,
                            /*size = */
                            layoutCoordinates.size,
                            /*position = */
                            layoutCoordinates.positionInWindow(),
                        )
                    }
            }
            if (index == selectedTabIndex) {
                QuackTitle2(
                    modifier = tabModifier,
                    text = title,
                    color = QuackColor.Black,
                )
            } else {
                QuackSubtitle(
                    modifier = tabModifier,
                    text = title,
                    color = QuackColor.Gray1,
                )
            }
        }
    }
}

/*@Composable
fun QuackSubTab(
    selectedTabIndex: Int,
    tabTitles: List<String>,
    onTabSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    val width = remember { mutableStateOf(0.dp) }
    val underBarStartPadding by animateDpAsState(
        targetValue = QuackSubTabWidth * selectedTabIndex,
        animationSpec = quackTween()
    )

    Box {
        QuackTabDivider(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .width(width = width.value + QuackTabHorizontalPadding * 2)
        )
        Box(
            modifier = Modifier
                .padding(paddingValues = TabPadding.Sub)
                .height(height = QuackTabHeight)
                .onSizeChanged { size ->
                    with(density) {
                        width.value = size.width.toDp()
                    }
                }
        ) {
            QuackSubTabTextRow(
                modifier = Modifier.align(alignment = Alignment.CenterStart),
                tabTitles = tabTitles,
                onTabSelected = onTabSelected,
                selectedTabIndex = selectedTabIndex,
            )
            QuackSelectedTabUnderBar(
                modifier = Modifier.align(alignment = Alignment.BottomStart),
                startPadding = underBarStartPadding,
                width = QuackSubTabWidth,
                color = QuackColor.Black,
            )
        }
    }
}

@Composable
private fun QuackSubTabTextRow(
    modifier: Modifier,
    tabTitles: List<String>,
    onTabSelected: (index: Int) -> Unit,
    selectedTabIndex: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabTitles.forEachIndexed { index, title ->
            val textModifier = remember {
                Modifier
                    .width(width = QuackSubTabWidth)
                    .clickable {
                        onTabSelected(index)
                    }
            }
            if (index == selectedTabIndex) {
                QuackTitle2(
                    text = title,
                    modifier = textModifier,
                )
            } else {
                QuackSubtitle(
                    text = title,
                    modifier = textModifier,
                )
            }
        }
    }
}*/

@Composable
private fun QuackTabDivider(
    modifier: Modifier = Modifier,
) {
    Divider(
        modifier = modifier
            .background(color = QuackColor.Gray3.value)
            .height(height = QuackTabDividerHeight)
    )
}

@Composable
private fun QuackSelectedTabUnderBar(
    modifier: Modifier = Modifier,
    offsetProvider: Density.() -> IntOffset,
    widthProvider: Density.() -> Dp,
    color: QuackColor,
) {
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .width(width = density.widthProvider())
            .height(height = QuackTabUnderBarHeight)
            .background(color = color.value)
            .offset(offsetProvider)
    )
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun QuackMainTabPreview() {
    var selectedMainTabIndex by remember { mutableStateOf(0) }

    QuackMainTab(
        selectedTabIndex = selectedMainTabIndex,
        titles = listOf(
            "판매중",
            "거래완료",
            "숨김",
        ),
        onTabSelected = { tabIndex ->
            selectedMainTabIndex = tabIndex
        }
    )
}

/*
@Preview
@Composable
private fun QuackSubTabPreview() {
    var selectedSubTabIndex by remember { mutableStateOf(1) }

    QuackSubTab(
        selectedTabIndex = selectedSubTabIndex,
        tabTitles = listOf(
            "피드",
            "컬렉션",
            "좋아요",
        ),
        onTabSelected = { tabIndex ->
            selectedSubTabIndex = tabIndex
        }
    )
}
*/
