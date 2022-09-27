/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.animation.quackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.drawUnderBarWithAnimation
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.fixedCopy

private val QuackTabFullUnderBarHeight = 1.dp
private val QuackTabFullUnderBarColor = QuackColor.Gray3

private val QuackTabVerticalPadding = 9.dp

private val QuackTabSelectedUnderBarHeight = 2.dp

private inline val QuackTabSelectedTextStyle: (isSelected: Boolean) -> QuackTextStyle
    get() = { isSelected ->
        when (isSelected) {
            true -> QuackTextStyle.Title2
            else -> QuackTextStyle.Subtitle.change(
                color = QuackColor.Gray1,
            )
        }
    }

/* ----- above: QuackTab common / below: QuackMainTab ----- */

private val QuackMainTabSelectedUnderBarColor = QuackColor.DuckieOrange

private val QuackMainTabSpacedBy = 28.dp
private val QuackMainTabTextInnerPadding = 2.dp

/* ----- above: QuackMainTab / below: QuackSubTab ----- */

private val QuackSubTabSelectedUnderBarColor = QuackColor.Black

private val QuackSubTabSpacedBy = 2.dp

/**
 * QuackMainTab 을 구현합니다. 선택된 탭 언더바의 위치가 각각 탭들 사이에서 겹쳐져
 * 움직여야 하므로 [Row] 가 아닌 [Box] 를 이용하여 각각 컴포저블이 겹쳐질 수 있게
 * 구현해야 합니다.
 *
 * QuackMainTab 은 항상 화면의 가로에 꽉차게 배치되며, 탭 사이 간격이 항상 28dp 로
 * 일정합니다. 선택된 탭 언더바의 가로 길이는 선택된 탭의 사이즈에 따라 유동적으로
 * 변화화며, `선택된 탭 텍스트가 차지하는 가로 사이즈 + 2dp * 2` 로 구할 수 있습니다.
 *
 * 전체 탭 언더바의 가로 길이는 항상 전체 탭의 가로 길이와 동일합니다.
 * 첫 번째와 마지막 탭은 화면에서 일정 사이즈 만큼 패딩이 적용되야 합니다.
 *
 * **모든 구현은 [titles] 가 동적으로 변하지 않는다는 가정하에 진행됐습니다.**
 *
 * @param titles 탭 제목 리스트. 안정성을 위해 일반 [Collection] 이 아닌,
 * [ImmutableCollection] 를 사용합니다.
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
    val titleSize = remember {
        titles.size
    }
    var tabHeight by remember {
        mutableStateOf(
            value = 0,
        )
    }

    val tabUnderBarXOffsets = remember {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0 },
            ),
        )
    }
    val selectedTabUnderBarXOffsetAnimation by animateIntAsState(
        targetValue = tabUnderBarXOffsets[selectedTabIndex],
        animationSpec = quackAnimationSpec(),
    )

    val tabWidths = remember {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0.dp },
            )
        )
    }
    val selectedTabUnderBarWidthAnimation by animateDpAsState(
        targetValue = tabWidths[selectedTabIndex] + QuackMainTabTextInnerPadding * 2,
        animationSpec = quackAnimationSpec(),
    )

    val dynamicSettingPerfectly = remember {
        mutableListOf(
            /*tabUnderBarXOffsets = */
            false,
            /*tabWidths = */
            false,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(
                zIndex = 0f,
            )
            .onPlaced { layoutCoordinates ->
                if (tabHeight == 0) {
                    tabHeight = layoutCoordinates.size.height
                }
            }
            .background(
                color = QuackColor.White.composeColor,
            )
            .drawUnderBarWithAnimation(
                width = QuackTabFullUnderBarHeight,
                color = QuackTabFullUnderBarColor,
            ),
    ) {
        QuackMainTabTextLazyRow(
            tabTitles = titles,
            tabStartHorizontalPadding = tabStartHorizontalPadding,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            onEachTabPositioned = { index, size, offset ->
                if (!dynamicSettingPerfectly[0]) {
                    val dynamicTabUnderBarXOffset =
                        offset.x.roundToInt() - QuackMainTabTextInnerPadding.roundToPx()
                    if (tabUnderBarXOffsets[index] != dynamicTabUnderBarXOffset) {
                        tabUnderBarXOffsets[index] = dynamicTabUnderBarXOffset
                    } else {
                        dynamicSettingPerfectly[0] = true
                    }
                }

                if (!dynamicSettingPerfectly[1]) {
                    val dynamicTabWidth = size.width.toDp()
                    if (tabWidths[index] != dynamicTabWidth) {
                        tabWidths[index] = dynamicTabWidth
                    } else {
                        dynamicSettingPerfectly[1] = true
                    }
                }
            },
        )
        QuackTabSelectedUnderBar(
            isMainTab = true,
            offsetProvider = {
                IntOffset(
                    x = selectedTabUnderBarXOffsetAnimation,
                    y = (tabHeight - QuackTabSelectedUnderBarHeight.roundToPx()).coerceAtLeast(
                        minimumValue = 0,
                    ),
                )
            },
            widthProvider = {
                selectedTabUnderBarWidthAnimation.roundToPx()
            },
        )
    }
}

/**
 * [QuackMainTab] 에서 사용되는 개별 탭을 그리는 컴포넌트입니다. 이 컴포넌트는
 * [QuackMainTab] 의 drawing 순서에 맞게 항상 첫 번째 zIndex 에 배치됩니다.
 *
 * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
 * [Row] 가 아닌 [LazyRow] 로 구현하였습니다.
 *
 * @param tabTitles 탭 타이틀 리스트
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 람다의 인자로는 선택된 탭의 index 가 넘어옵니다.
 * @param onEachTabPositioned 선택된 탭의 위치가 변경될 때 호출되는 콜백 람다.
 * 람다의 인자로는 선택된 탭의 index, 크기, 위치가 넘어옵니다.
 */
@Composable
private fun QuackMainTabTextLazyRow(
    tabTitles: PersistentList<String>,
    tabStartHorizontalPadding: Dp,
    selectedTabIndex: Int,
    onTabSelected: (
        index: Int,
    ) -> Unit,
    onEachTabPositioned: Density.(
        index: Int,
        size: IntSize,
        position: Offset,
    ) -> Unit,
) {
    val density = LocalDensity.current

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(
                zIndex = 1f,
            ),
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
                    .padding(
                        vertical = QuackTabVerticalPadding,
                        horizontal = QuackMainTabTextInnerPadding,
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        density.onEachTabPositioned(
                            /*index = */
                            index,
                            /*size = */
                            layoutCoordinates.size,
                            /*position = */
                            layoutCoordinates.positionInParent(),
                        )
                    }
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        onTabSelected(
                            /*index = */
                            index,
                        )
                    }
            }
            QuackText(
                modifier = tabModifier,
                text = title,
                style = QuackTabSelectedTextStyle(
                    /*isSelected = */
                    index == selectedTabIndex,
                ),
            )
        }
    }
}

/**
 * 꽥꽥 탭 컴포넌트에서 **현재 선택된 탭**의 가로 길이 만큼 표시하는 Divider 를
 * [Offset] 을 이용하여 구현합니다. 이 컴포넌트는 꽥꽥 탭 컴포넌트의 drawing 순서에
 * 맞게 항상 두 번째 zIndex 에 배치됩니다.
 *
 * 이 컴포넌트는 꽥꽥 탭 컴포넌트에서 공통으로 쓰입니다.
 *
 * @param isMainTab 사용되는 위치가 [QuackMainTab] 인지 여부
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
private fun QuackTabSelectedUnderBar(
    isMainTab: Boolean,
    offsetProvider: Density.() -> IntOffset,
    widthProvider: Density.() -> Int,
) = Box(
    modifier = Modifier
        .height(
            height = QuackTabSelectedUnderBarHeight,
        )
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
                    zIndex = 2f,
                )
            }
        }
        .background(
            color = when (isMainTab) {
                true -> QuackMainTabSelectedUnderBarColor
                else -> QuackSubTabSelectedUnderBarColor
            }.composeColor,
        )
)

/**
 * QuackSubTab 을 구현합니다. 선택된 탭 언더바의 위치가 각각 탭들 사이에서 겹쳐져
 * 움직여야 하므로 [Row] 가 아닌 [Box] 를 이용하여 각각 컴포저블이 겹쳐질 수 있게
 * 구현해야 합니다.
 *
 * QuackSubTab 은 항상 화면의 가로 길이에 꽉차게 배치되며 각각 탭들의 사이즈는
 * 전체 탭의 가로 길이에서 1:N 비율을 가져갑니다. (N: 전체 탭 아이템 개수)
 *
 * 첫 번째와 마지막 탭은 화면에서 일정 사이즈 만큼 패딩이 적용되야 합니다.
 *
 * **모든 구현은 [titles] 가 동적으로 변하지 않는다는 가정하에 진행됐습니다.**
 *
 * @param titles 탭 제목 리스트. 안정성을 위해 일반 [Collection] 이 아닌,
 * [ImmutableCollection] 를 사용합니다.
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
fun QuackSubTab(
    titles: PersistentList<String>,
    tabStartHorizontalPadding: Dp = 16.dp,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    var eachTabWidth by remember {
        mutableStateOf(
            value = 0.dp,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(
                zIndex = 0f,
            )
            .onPlaced { layoutCoordinate ->
                if (eachTabWidth.value == 0f) {
                    with(density) {
                        val tabPlaceableWidth =
                            layoutCoordinate.size.width.toDp() - tabStartHorizontalPadding * 2
                        eachTabWidth = tabPlaceableWidth / titles.size
                    }
                }
            }
            .drawUnderBarWithAnimation(
                width = QuackTabFullUnderBarHeight,
                color = QuackTabFullUnderBarColor,
            ),
    ) {
        QuackSubTabTextLazyRow(
            tabTitles = titles,
            tabStartHorizontalPadding = tabStartHorizontalPadding,
            eachTabWidth = eachTabWidth,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
        )
        QuackTabSelectedUnderBar(
            isMainTab = false,
            offsetProvider = {
                with(density) {
                    IntOffset(
                        x = ((eachTabWidth - tabStartHorizontalPadding) * selectedTabIndex).roundToPx(),
                        y = 0,
                    )
                }
            },
            widthProvider = {
                with(density) {
                    eachTabWidth.roundToPx()
                }
            },
        )
    }
}

/**
 * [QuackSubTab] 에서 사용되는 개별 탭을 그리는 컴포넌트입니다. 이 컴포넌트는
 * [QuackSubTab] 의 drawing 순서에 맞게 항상 첫 번째 zIndex 에 배치됩니다.
 *
 * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
 * [Row] 가 아닌 [LazyRow] 로 구현하였습니다.
 *
 * @param tabTitles 탭 타이틀 리스트
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param eachTabWidth 각각 탭들이 그려질 가로 길이
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 람다의 인자로는 선택된 탭의 index 가 넘어옵니다.
 */
@Composable
private fun QuackSubTabTextLazyRow(
    tabTitles: PersistentList<String>,
    tabStartHorizontalPadding: Dp,
    eachTabWidth: Dp,
    selectedTabIndex: Int,
    onTabSelected: (
        index: Int,
    ) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(
                zIndex = 1f,
            ),
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
                    .width(
                        width = eachTabWidth,
                    )
                    .wrapContentHeight()
                    .padding(
                        vertical = QuackTabVerticalPadding,
                        horizontal = QuackMainTabTextInnerPadding,
                    )
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        onTabSelected(
                            /*index = */
                            index,
                        )
                    }
            }
            QuackText(
                modifier = tabModifier,
                text = title,
                style = QuackTabSelectedTextStyle(
                    /*isSelected = */
                    index == selectedTabIndex,
                ),
            )
        }
    }
}
