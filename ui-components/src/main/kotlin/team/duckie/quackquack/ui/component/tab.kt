/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.ui.animation.QuackOptionalAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.drawAnimatedLine
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

/**
 * QuackTab 의 리소스 모음
 */
private object QuackTabDefaults {
    object Main {
        private val TextPadding = PaddingValues(
            vertical = 9.dp,
            horizontal = 2.dp,
        )

        /**
         * 각각 탭들의 사이 간격
         *
         * ```
         * | Tab1  Tab2  Tab3 |
         * ```
         *
         * 위와 같은 형태의 탭들의 사이 간격을 나타냅니다.
         */
        private val TabSpacedBy = 28.dp

        /**
         * 탭의 시작과 끝에 적용될 패딩
         *
         * ```
         * | Tab1  Tab2  Tab3 |
         * ```
         *
         * 위와 같은 형태의 탭에서 `|` 부분들을 나타냅니다.
         * 첫 번째 `|` 의 경우 왼쪽에 패딩이 적용되고,
         * 두 번째 `|` 의 경우 오른쪽에 패딩에 적용됩니다.
         */
        private val TabStartPadding = PaddingValues(
            horizontal = 16.dp,
        )

        /**
         * 탭에 표시되는 텍스트의 [QuackTextStyle] 을 조건에 맞게 계산합니다.
         *
         * @param isSelected 탭이 선택되었는지 여부
         *
         * @return [isSelected] 여부에 따른 [QuackTextStyle]
         */
        @Stable
        private fun typographyFor(
            isSelected: Boolean,
        ) = when (isSelected) {
            true -> QuackTextStyle.Title2
            else -> QuackTextStyle.Subtitle.change(
                color = QuackColor.Gray1,
            )
        }

        val BackgroundColor = QuackColor.White

        val FullUnderlineHeight = 1.dp
        val FullUnderlineColor = QuackColor.Gray3

        val SelectedTabUnderlineHeight = 2.dp
        val SelectedTabUnderlineColor = QuackColor.DuckieOrange

        /**
         * [QuackMainTab] 에서 사용되는 개별 탭을 그리는 컴포넌트입니다. 이 컴포넌트는
         * [QuackMainTab] 의 drawing 순서에 맞게 항상 첫 번째 zIndex 에 배치됩니다.
         *
         * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
         * [Row] 가 아닌 [LazyRow] 로 구현하였습니다.
         *
         * @param modifier 이 컴포넌트를 그리기 위해 사용할 [Modifier]
         * @param tabTitles 탭 타이틀 리스트
         * @param selectedTabIndex 현재 선택된 탭의 index
         * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 람다.
         * 람다의 인자로는 선택된 탭의 index 가 넘어옵니다.
         * @param onEachTabPositioned 선택된 탭이 배치됐을 때 호출되는 람다.
         * 람다의 인자로는 선택된 탭의 index, 크기, 위치가 넘어옵니다.
         * 선택된 탭에 underline 을 그리기 위한 정보를 얻기 위해 사용됩니다.
         */
        @Composable
        fun TabTextLazyRow(
            modifier: Modifier,
            tabTitles: ImmutableList<String>,
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
                contentPadding = TabStartPadding,
                horizontalArrangement = Arrangement.spacedBy(
                    space = TabSpacedBy,
                ),
            ) {
                itemsIndexed(
                    items = tabTitles,
                    key = { _, title -> title },
                ) { index, title ->
                    QuackText(
                        modifier = Modifier
                            .wrapContentSize()
                            .quackClickable(
                                rippleEnabled = false,
                            ) {
                                onTabSelected(
                                    /* index = */
                                    index,
                                )
                            }
                            .onGloballyPositioned { layoutCoordinates ->
                                onEachTabPositioned(
                                    /* index = */
                                    index,
                                    /* size = */
                                    layoutCoordinates.size,
                                    /* position = */
                                    layoutCoordinates.positionInParent(),
                                )
                            }
                            .padding(
                                paddingValues = TextPadding,
                            ),
                        text = title,
                        style = typographyFor(
                            /* isSelected = */
                            index == selectedTabIndex,
                        ),
                    )
                }
            }
        }
    }

    object Sub {
        private val TextPadding = PaddingValues(
            vertical = 9.dp,
        )

        /**
         * 각각 탭들의 사이 간격
         *
         * ```
         * | Tab1  Tab2  Tab3 |
         * ```
         *
         * 위와 같은 형태의 탭들의 사이 간격을 나타냅니다.
         */
        private val TabSpacedBy = 2.dp

        /**
         * 탭의 시작과 끝에 적용될 패딩
         *
         * ```
         * | Tab1  Tab2  Tab3 |
         * ```
         *
         * 위와 같은 형태의 탭에서 `|` 부분들을 나타냅니다.
         * 첫 번째 `|` 의 경우 왼쪽에 패딩이 적용되고,
         * 두 번째 `|` 의 경우 오른쪽에 패딩에 적용됩니다.
         */
        private val TabStartPadding = PaddingValues(
            horizontal = 16.dp,
        )

        /**
         * 탭에 표시되는 텍스트의 [QuackTextStyle] 을 조건에 맞게 계산합니다.
         *
         * @param isSelected 탭이 선택되었는지 여부
         *
         * @return [isSelected] 여부에 따른 [QuackTextStyle]
         */
        @Stable
        private fun typographyFor(
            isSelected: Boolean,
        ) = when (isSelected) {
            true -> QuackTextStyle.Title2
            else -> QuackTextStyle.Subtitle.change(
                color = QuackColor.Gray1,
            )
        }

        val BackgroundColor = QuackColor.White

        val FullUnderlineHeight = 1.dp
        val FullUnderlineColor = QuackColor.Gray3

        val SelectedTabUnderlineHeight = 2.dp
        val SelectedTabUnderlineColor = QuackColor.Black

        /**
         * [QuackSubTab] 에서 사용되는 개별 탭을 그리는 컴포넌트입니다. 이 컴포넌트는
         * [QuackSubTab] 의 drawing 순서에 맞게 항상 첫 번째 zIndex 에 배치됩니다.
         *
         * `clipToPadding = false` 로 첫 번째 탭과 마지막 탭에 패딩을 적용하기 위해서
         * [Row] 가 아닌 [LazyRow] 로 구현하였습니다.
         *
         * @param modifier 이 컴포넌트를 그리기 위해 사용할 [Modifier]
         * @param tabTitles 탭 타이틀 리스트
         * @param eachTabWidth 각각 탭들이 그려질 가로 길이
         * @param selectedTabIndex 현재 선택된 탭의 index
         * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
         * 람다의 인자로는 선택된 탭의 index 가 넘어옵니다.
         */
        @Composable
        fun TabTextLazyRow(
            modifier: Modifier,
            tabTitles: ImmutableList<String>,
            eachTabWidth: Dp,
            selectedTabIndex: Int,
            onTabSelected: (
                index: Int,
            ) -> Unit,
        ) {
            LazyRow(
                modifier = modifier,
                contentPadding = TabStartPadding,
                horizontalArrangement = Arrangement.spacedBy(
                    space = TabSpacedBy,
                ),
            ) {
                itemsIndexed(
                    items = tabTitles,
                    key = { _, title -> title },
                ) { index, title ->
                    QuackText(
                        modifier = Modifier
                            .width(
                                width = eachTabWidth,
                            )
                            .wrapContentHeight()
                            .quackClickable(
                                rippleEnabled = false,
                            ) {
                                onTabSelected(
                                    /* index = */
                                    index,
                                )
                            }
                            .padding(
                                paddingValues = TextPadding,
                            ),
                        text = title,
                        style = typographyFor(
                            /* isSelected = */
                            index == selectedTabIndex,
                        ),
                    )
                }
            }
        }
    }
}

/**
 * 덕키의 메인 Tab 을 구현합니다. 선택된 탭 언더바의 위치가 각각 탭들 사이에서 겹쳐져
 * 움직여야 하므로 [Row] 가 아닌 [Box] 를 이용하여 각각 컴포저블이 겹쳐질 수 있게
 * 구현합니다.
 *
 * QuackMainTab 은 항상 상위 컨테이너의 가로에 꽉차게 배치되며, 탭 사이 간격이 항상 28dp 로
 * 일정합니다. 선택된 탭 언더바의 가로 길이는 선택된 탭의 사이즈에 따라 유동적으로
 * 변화화며, `선택된 탭 텍스트가 차지하는 가로 사이즈 + 2dp * 2` 로 구할 수 있습니다.
 *
 * 첫 번째와 마지막 탭은 화면에서 일정 사이즈 만큼 패딩이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param titles 탭 제목 리스트
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 콜백 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
public fun QuackMainTab(
    modifier: Modifier = Modifier,
    titles: ImmutableList<String>,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
): Unit = with(
    receiver = QuackTabDefaults.Main,
) {
    // 최초 컴포지션시에는 0 -> tabUnderBarXOffsets[0] 으로 사이즈가 바뀌므로
    // 이때도 애니메이션이 들어가는걸 방지하기 위해 사이즈 동적 계산이 끝난 후
    // 배치될 때만 애니메이션이 적용될 수 있도록 합니다.
    var isPlacedDone by remember(
        key1 = titles,
    ) {
        mutableStateOf(
            value = false,
        )
    }
    val titleSize = remember(
        key1 = titles,
    ) {
        titles.size
    }

    val eachTabXOffsets = remember(
        key1 = titleSize,
    ) {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0f },
            ),
        )
    }
    val eachTabWidths = remember(
        key1 = titleSize,
    ) {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0 },
            )
        )
    }

    // remember 보다 매번 연산이 더 저렴함
    val selectedTabUnderlineStartXOffset = eachTabXOffsets[selectedTabIndex]
    val selectedTabUnderlineWidth = eachTabWidths[selectedTabIndex]

    /**
     * [QuackOptionalAnimationSpec] 을 델리게이트 합니다.
     * `SpecifyAnimationSpec` 린트를 억제하기 위한 함수입니다.
     *
     * @return [QuackOptionalAnimationSpec] 을 반환합니다.
     */
    @Suppress("FunctionName")
    fun <T> QuackAnimationSpec() = QuackOptionalAnimationSpec<T>(
        useAnimation = isPlacedDone,
    )

    val animatedSelectedTabUnderlineStartXOffset by animateFloatAsState(
        targetValue = selectedTabUnderlineStartXOffset,
        animationSpec = QuackAnimationSpec(),
    )
    val animatedSelectedTabUnderlineWidth by animateIntAsState(
        targetValue = selectedTabUnderlineWidth,
        animationSpec = QuackAnimationSpec(),
    )

    TabTextLazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = BackgroundColor.composeColor,
            )
            // draw selected tab underline
            .drawAnimatedLine(
                thickness = SelectedTabUnderlineHeight,
                color = SelectedTabUnderlineColor,
                startOffsetProvider = { size ->
                    Offset(
                        x = animatedSelectedTabUnderlineStartXOffset,
                        y = size.height - SelectedTabUnderlineHeight.toPx(),
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = animatedSelectedTabUnderlineStartXOffset + animatedSelectedTabUnderlineWidth,
                        y = size.height - SelectedTabUnderlineHeight.toPx(),
                    )
                },
                colorAnimationFinishedListener = {
                    if (selectedTabUnderlineStartXOffset > 0f) {
                        isPlacedDone = true
                    }
                },
                useAnimation = isPlacedDone,
            )
            // draw full tab underline
            .drawAnimatedLine(
                thickness = FullUnderlineHeight,
                color = FullUnderlineColor,
                startOffsetProvider = { size ->
                    Offset(
                        x = 0f,
                        y = size.height - FullUnderlineHeight.toPx(),
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = size.width,
                        y = size.height - FullUnderlineHeight.toPx(),
                    )
                },
                useAnimation = isPlacedDone,
            ),
        tabTitles = titles,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
        onEachTabPositioned = { index, size, offset ->
            eachTabXOffsets[index] = offset.x
            eachTabWidths[index] = size.width
        },
    )
}

/**
 * QuackSubTab 을 구현합니다. 선택된 탭 언더바의 위치가 각각 탭들 사이에서 겹쳐져
 * 움직여야 하므로 [Row] 가 아닌 [Box] 를 이용하여 각각 컴포저블이 겹쳐질 수 있게
 * 구현합니다.
 *
 * QuackSubTab 은 항상 상위 컨테이너의 가로에 꽉차게 배치되며, 각각 탭들의 사이즈는
 * 전체 탭의 가로 길이에서 1:N 비율을 가져갑니다. (N: 전체 탭 아이템 개수)
 *
 * 첫 번째와 마지막 탭은 화면에서 일정 사이즈 만큼 패딩이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param titles 탭 제목 리스트
 * @param selectedTabIndex 현재 선택된 탭의 index
 * @param onTabSelected 새로운 탭이 선택됐을 때 호출되는 람다.
 * 인자로는 선택된 탭의 index 가 들어옵니다.
 */
@Composable
public fun QuackSubTab(
    modifier: Modifier = Modifier,
    titles: ImmutableList<String>,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
): Unit = with(
    receiver = QuackTabDefaults.Sub,
) {
    val density = LocalDensity.current

    // 최초 컴포지션시에는 0 -> tabUnderBarXOffsets[0] 으로 사이즈가 바뀌므로
    // 이때도 애니메이션이 들어가는걸 방지하기 위해 사이즈 동적 계산이 끝난 후
    // 배치될 때만 애니메이션이 적용될 수 있도록 합니다.
    var isPlacedDone by remember(
        key1 = titles,
    ) {
        mutableStateOf(
            value = false,
        )
    }
    val titleSize = remember(
        key1 = titles,
    ) {
        titles.size
    }

    val eachTabXOffsets = remember(
        key1 = titleSize,
    ) {
        mutableStateListOf(
            elements = Array(
                size = titleSize,
                init = { 0f },
            ),
        )
    }
    var eachTabWidth by remember(
        key1 = titleSize,
    ) {
        mutableStateOf(
            value = 0,
        )
    }
    val eachTabWidthDp = remember(
        key1 = eachTabWidth,
        key2 = density.density,
    ) {
        with(
            receiver = density,
        ) {
            eachTabWidth.toDp()
        }
    }

    // remember 보다 매번 연산이 더 저렴함
    val selectedTabUnderlineStartXOffset = eachTabXOffsets[selectedTabIndex]

    /**
     * [QuackOptionalAnimationSpec] 을 델리게이트 합니다.
     * `SpecifyAnimationSpec` 린트를 억제하기 위한 함수입니다.
     *
     * @return [QuackOptionalAnimationSpec] 을 반환합니다.
     */
    @Suppress("FunctionName")
    fun <T> QuackAnimationSpec() = QuackOptionalAnimationSpec<T>(
        useAnimation = isPlacedDone,
    )

    val animatedSelectedTabUnderlineStartXOffset by animateFloatAsState(
        targetValue = selectedTabUnderlineStartXOffset,
        animationSpec = QuackAnimationSpec(),
    )

    TabTextLazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = BackgroundColor.composeColor,
            )
            .onPlaced { layoutCoordinate ->
                eachTabWidth = layoutCoordinate.size.width
            }
            // draw selected tab underline
            .drawAnimatedLine(
                thickness = SelectedTabUnderlineHeight,
                color = SelectedTabUnderlineColor,
                startOffsetProvider = { size ->
                    Offset(
                        x = animatedSelectedTabUnderlineStartXOffset,
                        y = size.height - SelectedTabUnderlineHeight.toPx(),
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = animatedSelectedTabUnderlineStartXOffset + eachTabWidth,
                        y = size.height - SelectedTabUnderlineHeight.toPx(),
                    )
                },
                colorAnimationFinishedListener = {
                    if (selectedTabUnderlineStartXOffset > 0f) {
                        isPlacedDone = true
                    }
                },
                useAnimation = isPlacedDone,
            )
            // draw full tab underline
            .drawAnimatedLine(
                thickness = FullUnderlineHeight,
                color = FullUnderlineColor,
                startOffsetProvider = { size ->
                    Offset(
                        x = 0f,
                        y = size.height - FullUnderlineHeight.toPx(),
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = size.width,
                        y = size.height - FullUnderlineHeight.toPx(),
                    )
                },
                useAnimation = isPlacedDone,
            ),
        tabTitles = titles,
        eachTabWidth = eachTabWidthDp,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
    )
}
