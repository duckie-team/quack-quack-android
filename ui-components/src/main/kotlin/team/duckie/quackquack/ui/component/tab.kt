/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("KDocUnresolvedReference")

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.animation.quackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.fixedCopy

private val QuackTabDividerHeight = 1.dp
private val QuackTabDividerColor = QuackColor.Gray3

private val QuackSelectedTabUnderBarHeight = 2.dp
private val QuackSelectedTabUnderBarColor = QuackColor.DuckieOrange

private inline val QuackSelectedTabTextStyle: (isSelected: Boolean) -> QuackTextStyle
    get() = { isSelected ->
        when (isSelected) {
            true -> QuackTextStyle.Title2.change(
                color = QuackColor.Black,
            )
            else -> QuackTextStyle.Subtitle.change(
                color = QuackColor.Gray1,
            )
        }
    }

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
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
fun QuackMainTab(
    titles: PersistentList<String>,
    tabStartHorizontalPadding: Dp = 16.dp,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    val titleSize = remember(
        key1 = titles,
    ) {
        titles.size
    }
    var tabHeight by remember(
        key1 = titles,
    ) {
        mutableStateOf(0)
    }
    val tabUnderBarXOffsets = remember(
        key1 = titles,
    ) {
        mutableStateListOf(*Array(titleSize) { 0 })
    }
    val currentTabUnderBarXOffsetAnimation by animateIntAsState(
        targetValue = tabUnderBarXOffsets[selectedTabIndex],
        animationSpec = quackAnimationSpec(),
    )
    val tabWidths = remember(
        key1 = titles,
    ) {
        mutableStateListOf(*Array(titleSize) { 0.dp })
    }
    val currentTabUnderBarWidthAnimation by animateDpAsState(
        targetValue = tabWidths[selectedTabIndex] + QuackMainTabTextInnerPadding * 2,
        animationSpec = quackAnimationSpec(),
    )

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = QuackColor.White.value,
            ),
    ) {
        QuackMainTabTextLazyRow(
            modifier = Modifier.zIndex(
                zIndex = 1f,
            ),
            tabTitles = titles,
            tabStartHorizontalPadding = tabStartHorizontalPadding,
            onTabContainerSizeChanged = { size ->
                tabHeight = size.height
            },
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            onSelectedTabPositionChanged = { index, size, offset ->
                tabUnderBarXOffsets[index] =
                    offset.x.roundToInt() - QuackMainTabTextInnerPadding.roundToPx()
                tabWidths[index] = size.width.toDp()
            },
        )
        QuackTabDivider(
            zIndex = 2f,
            offsetProvider = {
                IntOffset(
                    x = 0,
                    y = (tabHeight - QuackTabDividerHeight.roundToPx()).coerceAtLeast(
                        minimumValue = 0,
                    ),
                )
            },
        )
        QuackSelectedTabUnderBar(
            color = QuackSelectedTabUnderBarColor,
            zIndex = 3f,
            offsetProvider = {
                IntOffset(
                    x = currentTabUnderBarXOffsetAnimation,
                    y = (tabHeight - QuackSelectedTabUnderBarHeight.roundToPx()).coerceAtLeast(
                        minimumValue = 0,
                    ),
                )
            },
            widthProvider = {
                currentTabUnderBarWidthAnimation.roundToPx()
            },
        )
    }
}

/**
 * QuackMainTab 에서 사용되는 탭 제목을 그리는 컴포넌트입니다.
 *
 * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
 * Row 가 아닌 LazyRow 로 구현하였습니다.
 *
 * @param modifier 컴포넌트에 적용할 [Modifier]
 * @param tabTitles 탭 타이틀 리스트
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param onTabContainerSizeChanged 탭 컨테이너의 크기가 변경될 때 호출되는 콜백 람다
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 람다의 인자로는 선택된 탭의 index 가 넘어옵니다.
 * @param onSelectedTabPositionChanged 선택된 탭의 위치가 변경될 때 호출되는 콜백 람다.
 * 람다의 인자로는 선택된 탭의 index, 크기, 위치가 넘어옵니다.
 */
@Composable
private fun QuackMainTabTextLazyRow(
    modifier: Modifier = Modifier,
    tabTitles: PersistentList<String>,
    tabStartHorizontalPadding: Dp,
    onTabContainerSizeChanged: Density.(
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
                density.onTabContainerSizeChanged(size)
            },
        contentPadding = PaddingValues(
            horizontal = tabStartHorizontalPadding,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackMainTabSpacedBy,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        itemsIndexed(
            items = tabTitles,
            key = { _, title -> title },
        ) { index, title ->
            val tabModifier = remember {
                Modifier
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        onTabSelected(index)
                    }
                    .padding(
                        vertical = QuackTabVerticalPadding,
                        horizontal = QuackMainTabTextInnerPadding,
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        density.onSelectedTabPositionChanged(
                            /*index = */
                            index,
                            /*size = */
                            layoutCoordinates.size,
                            /*position = */
                            layoutCoordinates.positionInParent(),
                        )
                    }
            }
            QuackText(
                modifier = tabModifier,
                text = title,
                style = QuackSelectedTabTextStyle(index == selectedTabIndex),
            )
        }
    }
}

// TODO: 리펙토링 필요
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

// TODO: 리펙토링 필요
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

/**
 * QuackTab 의 전체 가로 길이 만큼 표시하는 Divider 인
 * QuackTabDivider 를 Offset 을 이용하여 구현합니다.
 *
 * @param modifier 이 컴포저블이 사용할 [Modifier]
 * @param offsetProvider 이 컴포저블이 배치될 [Offset].
 * Y 위치가 동적으로 계산되므로 이 인자를 통해 해당 값을 제공받아야 합니다.
 * 리컴포지션 스킵을 위해 값을 바로 받는게 아닌 람다를 통해 받습니다.
 */
@Composable
@NonRestartableComposable
private fun QuackTabDivider(
    modifier: Modifier = Modifier,
    zIndex: Float,
    offsetProvider: Density.() -> IntOffset,
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .height(height = QuackTabDividerHeight)
        .zIndex(zIndex)
        .offset(offsetProvider)
        .background(color = QuackTabDividerColor.value)
)

/**
 * QuackTab 의 현재 선택된 탭의 가로 길이 만큼 표시하는 Divider 인
 * QuackSelectedTabUnderBar 를 Offset 을 이용하여 구현합니다.
 *
 * @param modifier 이 컴포저블이 사용할 [Modifier]
 * @param color 이 컴포저블이 표시할 언더바의 색상
 * @param zIndex 이 컴포저블의 우선 순위. 높을수록 상위에 배치됩니다.
 * @param offsetProvider 이 컴포저블이 배치될 [Offset].
 * X 와 Y 위치가 동적으로 계산되므로 이 인자를 통해 해당 값을 제공받아야 합니다.
 * 리컴포지션 스킵을 위해 값을 바로 받는게 아닌 람다를 통해 받습니다.
 * @param widthProvider 이 컴포저블의 가로 길이.
 * 선택된 탭의 가로 길이에 추가로 값을 더해서 가로 길이가 계산됩니다.
 * 선택된 탭의 따라 동적으로 변화는 값이므로 인자를 통해 매번 새로운 값을
 * 제공받아야 합니다. 리컴포지션 스킵을 위해 값을 바로 받는게 아닌 람다를 통해 받습니다.
 */
@Composable
@NonRestartableComposable
private fun QuackSelectedTabUnderBar(
    modifier: Modifier = Modifier,
    color: QuackColor,
    zIndex: Float,
    offsetProvider: Density.() -> IntOffset,
    widthProvider: Density.() -> Int,
) = Box(
    modifier = modifier
        .height(height = QuackSelectedTabUnderBarHeight)
        .layout { measurable, constraints ->
            val width = widthProvider()
            val placeable = measurable.measure(
                constraints = constraints.fixedCopy(
                    width = width,
                ),
            )
            layout(
                width = placeable.width,
                height = placeable.height,
            ) {
                placeable.place(
                    position = offsetProvider(),
                    zIndex = zIndex,
                )
            }
        }
        .background(color = color.value)
)
