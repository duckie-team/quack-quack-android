/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.drawUnderBarWithAnimation
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

private val QuackTabFullUnderBarHeight = 1.dp
private val QuackTabFullUnderBarColor = QuackColor.Gray3
private val QuackFullTabSelectedUnderBarHeight = 2.dp

private val QuackTabVerticalPadding = 9.dp

private val QuackTabSelectedTextStyle: (
    isSelected: Boolean,
) -> QuackTextStyle
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
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param titles 탭 제목 리스트. 안정성을 위해 일반 [Collection] 이 아닌,
 * [ImmutableCollection] 를 사용합니다.
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
public fun QuackMainTab(
    titles: PersistentList<String>,
    tabStartHorizontalPadding: Dp = 16.dp,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current

    // 최초 컴포지션시에는 0 -> tabUnderBarXOffsets[0] 으로 사이즈가 바뀌므로
    // 이때도 애니메이션이 들어가는걸 방지하기 위해 사이즈 동적 계산이 끝난 후
    // 배치될 때만 애니메이션이 적용될 수 있도록 합니다.
    var isPlacedDone by remember {
        mutableStateOf(
            value = false,
        )
    }
    val dynamicSettingPerfectly = remember {
        mutableListOf(
            /*tabUnderBarXOffsets = */
            false,
            /*tabWidths = */
            false,
        )
    }

    @Suppress("FunctionName")
    fun <T> QuackAnimationSpec(): AnimationSpec<T> = when (isPlacedDone) {
        true -> team.duckie.quackquack.ui.animation.QuackAnimationSpec()
        else -> snap()
    }

    val selectedUnderBarHeight = remember(
        key1 = density,
    ) {
        with(
            receiver = density,
        ) {
            QuackFullTabSelectedUnderBarHeight.toPx()
        }
    }

    val titleSize = remember {
        titles.size
    }

    val tabUnderBarXOffsets = remember {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0f },
            ),
        )
    }
    // 탭 언더바의 x 오프셋 애니메이션이 끝나면 탭 언더바의 가로 길이
    // 에니메이션도 끝난걸로 간주합니다.
    val selectedTabUnderBarXOffsetAnimation by animateFloatAsState(
        targetValue = tabUnderBarXOffsets[selectedTabIndex],
        animationSpec = QuackAnimationSpec(),
        finishedListener = {
            isPlacedDone = true
        },
    )

    val tabWidths = remember {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0 },
            )
        )
    }
    val selectedTabUnderBarWidthAnimation by animateIntAsState(
        targetValue = tabWidths[selectedTabIndex] + with(
            receiver = density,
        ) {
            QuackMainTabTextInnerPadding.roundToPx()
        } * 2,
        animationSpec = QuackAnimationSpec(),
    )

    QuackMainTabTextLazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = QuackColor.White.composeColor,
            )
            .drawQuackTabSelectedUnderBar(
                isMainTab = true,
                widthProvider = { selectedUnderBarHeight },
                startXOffsetProvider = {
                    selectedTabUnderBarXOffsetAnimation
                },
                endXOffsetProvider = { startXOffset ->
                    startXOffset + selectedTabUnderBarWidthAnimation
                },
                yOffsetProvider = { height, strokeWidth ->
                    height - strokeWidth / 2 // why needs to divide by 2?, really i can't understand... :thinking:
                },
            )
            .drawUnderBarWithAnimation(
                width = QuackTabFullUnderBarHeight,
                color = QuackTabFullUnderBarColor,
            ),
        tabTitles = titles,
        tabStartHorizontalPadding = tabStartHorizontalPadding,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
        onEachTabPositioned = { index, size, offset ->
            with(density) {
                if (!dynamicSettingPerfectly[0]) {
                    val dynamicTabUnderBarXOffset =
                        offset.x - QuackMainTabTextInnerPadding.roundToPx()
                    if (tabUnderBarXOffsets[index] != dynamicTabUnderBarXOffset) {
                        tabUnderBarXOffsets[index] = dynamicTabUnderBarXOffset
                    } else {
                        dynamicSettingPerfectly[0] = true
                    }
                }

                if (!dynamicSettingPerfectly[1]) {
                    val dynamicTabWidth = size.width
                    if (tabWidths[index] != dynamicTabWidth) {
                        tabWidths[index] = dynamicTabWidth
                    } else {
                        dynamicSettingPerfectly[1] = true
                    }
                }
            }
        },
    )
}

/**
 * [QuackMainTab] 에서 사용되는 개별 탭을 그리는 컴포넌트입니다. 이 컴포넌트는
 * [QuackMainTab] 의 drawing 순서에 맞게 항상 첫 번째 zIndex 에 배치됩니다.
 *
 * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
 * [Row] 가 아닌 [LazyRow] 로 구현하였습니다.
 *
 * @param modifier 이 컴포넌트를 그리기 위해 사용할 [Modifier]
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
    modifier: Modifier,
    tabTitles: PersistentList<String>,
    tabStartHorizontalPadding: Dp,
    selectedTabIndex: Int,
    onTabSelected: (
        index: Int,
    ) -> Unit,
    onEachTabPositioned: (
        index: Int,
        size: IntSize,
        position: Offset,
    ) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = tabStartHorizontalPadding,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackMainTabSpacedBy,
        ),
    ) {
        itemsIndexed(
            items = tabTitles,
            key = { _, title -> title },
        ) { index, title ->
            val tabModifier = remember {
                Modifier
                    .wrapContentSize()
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        onTabSelected(
                            /*index = */
                            index,
                        )
                    }
                    .onGloballyPositioned { layoutCoordinates ->
                        onEachTabPositioned(
                            /*index = */
                            index,
                            /*size = */
                            layoutCoordinates.size,
                            /*position = */
                            layoutCoordinates.positionInParent(),
                        )
                    }
                    .padding(
                        vertical = QuackTabVerticalPadding,
                        horizontal = QuackMainTabTextInnerPadding,
                    )
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
 * 꽥꽥 탭 컴포넌트에서 **현재 선택된 탭**의 가로 길이 만큼 under bar 를 그립니다.
 * 각각 탭의 x 오프셋이 동적으로 계산되므로 해당 값은 인자로 제공받아야 합니다.
 * 리컴포지션 스킵을 위해 값을 바로 받는게 아닌 람다를 통해 받습니다.
 *
 * @param isMainTab 사용되는 위치가 [QuackMainTab] 인지 여부. 이 값에 따라 under bar 의
 * 색상이 달라집니다.
 * @param widthProvider under bar 의 width 사이즈를 계산하는 람다
 * @param startXOffsetProvider under bar drawing 을 시작할 x 오프셋을 계산하는 람다.
 * X 와 Y 위치가 동적으로 계산되므로 이 인자를 통해 해당 값을 제공받아야 합니다.
 * 리컴포지션 스킵을 위해 값을 바로 받는게 아닌 람다를 통해 받습니다.
 * @param endXOffsetProvider under bar drawing 을 끝낼 x 오프셋을 계산하는 람다
 * @param yOffsetProvider under bar 가 drawing 될 y 오프셋을 계산하는 람다
 *
 * @return 선택된 탭의 under bar 가 그려진 [Modifier]
 */
private fun Modifier.drawQuackTabSelectedUnderBar(
    isMainTab: Boolean,
    widthProvider: () -> Float,
    startXOffsetProvider: () -> Float,
    endXOffsetProvider: (
        startXOffset: Float,
    ) -> Float,
    yOffsetProvider: (
        height: Float,
        strokeWidth: Float,
    ) -> Float = { height, strokeWidth ->
        height - strokeWidth
    },
) = composed {
    val color = remember {
        when (isMainTab) {
            true -> QuackMainTabSelectedUnderBarColor
            else -> QuackSubTabSelectedUnderBarColor
        }.composeColor
    }
    drawWithContent {
        val strokeWidth = widthProvider()
        val startX = startXOffsetProvider()
        val endX = endXOffsetProvider(
            /*startXOffset =*/
            startX,
        )
        val height = yOffsetProvider(
            /*height = */
            size.height,
            /*strokeWidth =*/
            strokeWidth,
        )
        drawContent()
        drawLine(
            color = color,
            start = Offset(
                x = startX,
                y = height,
            ),
            end = Offset(
                x = endX,
                y = height,
            ),
            strokeWidth = strokeWidth,
        )
    }
}

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
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param titles 탭 제목 리스트. 안정성을 위해 일반 [Collection] 이 아닌,
 * [ImmutableCollection] 를 사용합니다.
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
public fun QuackSubTab(
    titles: PersistentList<String>,
    tabStartHorizontalPadding: Dp = 16.dp,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    val underBarHeight = remember(
        key1 = density,
    ) {
        with(
            receiver = density,
        ) {
            QuackFullTabSelectedUnderBarHeight.toPx()
        }
    }
    var eachTabWidth by remember {
        mutableStateOf(
            value = 0.dp,
        )
    }

    val selectedTabWidthAnimation by animateFloatAsState(
        targetValue = with(
            receiver = density,
        ) {
            (eachTabWidth * selectedTabIndex)
                .plus(
                    other = tabStartHorizontalPadding,
                )
                .plus(
                    other = QuackSubTabSpacedBy * selectedTabIndex
                )
                .toPx()
        },
        animationSpec = QuackAnimationSpec(),
    )

    QuackSubTabTextLazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onPlaced { layoutCoordinate ->
                if (eachTabWidth.value == 0f) {
                    with(density) {
                        val tabPlaceableWidth = layoutCoordinate.size.width
                            .toDp()
                            .minus(
                                other = tabStartHorizontalPadding * 2,
                            )
                            .minus(
                                other = QuackSubTabSpacedBy * (titles.size - 1),
                            )
                        eachTabWidth = tabPlaceableWidth / titles.size
                    }
                }
            }
            .drawQuackTabSelectedUnderBar(
                isMainTab = false,
                widthProvider = { underBarHeight },
                startXOffsetProvider = { selectedTabWidthAnimation },
                endXOffsetProvider = { startXOffset ->
                    with(
                        receiver = density,
                    ) {
                        startXOffset + eachTabWidth.toPx()
                    }
                },
                yOffsetProvider = { height, strokeWidth ->
                    height - strokeWidth / 2 // why needs to divide by 2?, really i can't understand... :thinking:
                },
            )
            .drawUnderBarWithAnimation(
                width = QuackTabFullUnderBarHeight,
                color = QuackTabFullUnderBarColor,
            ),
        tabTitles = titles,
        tabStartHorizontalPadding = tabStartHorizontalPadding,
        eachTabWidth = eachTabWidth,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
    )
}

/**
 * [QuackSubTab] 에서 사용되는 개별 탭을 그리는 컴포넌트입니다. 이 컴포넌트는
 * [QuackSubTab] 의 drawing 순서에 맞게 항상 첫 번째 zIndex 에 배치됩니다.
 *
 * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
 * [Row] 가 아닌 [LazyRow] 로 구현하였습니다.
 *
 * @param modifier 이 컴포넌트를 그리기 위해 사용할 [Modifier]
 * @param tabTitles 탭 타이틀 리스트
 * @param tabStartHorizontalPadding 첫 번째와 마지막 탭의 가로 패딩
 * @param eachTabWidth 각각 탭들이 그려질 가로 길이
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 람다의 인자로는 선택된 탭의 index 가 넘어옵니다.
 */
@Composable
private fun QuackSubTabTextLazyRow(
    modifier: Modifier,
    tabTitles: PersistentList<String>,
    tabStartHorizontalPadding: Dp,
    eachTabWidth: Dp,
    selectedTabIndex: Int,
    onTabSelected: (
        index: Int,
    ) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = tabStartHorizontalPadding,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackSubTabSpacedBy,
        ),
    ) {
        itemsIndexed(
            items = tabTitles,
            key = { _, title -> title },
        ) { index, title ->
            val tabModifier = remember(
                key1 = eachTabWidth,
            ) {
                Modifier
                    .width(
                        width = eachTabWidth,
                    )
                    .wrapContentHeight()
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        onTabSelected(
                            /*index = */
                            index,
                        )
                    }
                    .padding(
                        vertical = QuackTabVerticalPadding,
                        horizontal = QuackMainTabTextInnerPadding,
                    )
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
