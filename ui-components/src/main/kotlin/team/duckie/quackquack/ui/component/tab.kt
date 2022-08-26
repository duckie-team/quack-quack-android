/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [tab.kt] created by Ji Sungbin on 22. 8. 21. 오후 4:26
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.MainTabPadding
import team.duckie.quackquack.ui.constant.SubTabPadding
import team.duckie.quackquack.ui.typography.QuackSubtitle
import team.duckie.quackquack.ui.typography.QuackTitle2

private val QuackTabHorizontalPadding = 16.dp
private val QuackTabUnderBarHeight = 1.dp
private val QuackTabHeight = 36.dp
private val QuackSubTabWidth = 108.dp
private val QuackMainTabSpacedBy = 28.dp
private val QuackMainTabTextInnerPadding = 2.dp

@Composable
fun QuackMainTab(
    selectedTabIndex: Int,
    tabTitles: List<String>,
    onTabSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    val size = tabTitles.size
    val underBarWidths = remember { mutableStateOf(Array(size) { 0.dp }) }
    val underBarStartPaddingPosition = remember { mutableStateOf(Array(size + 1) { 0.dp }) }.apply {
        value[0] = QuackTabHorizontalPadding
    }
    val width = remember { mutableStateOf(0.dp) }

    val underBarStartPadding: Dp by animateDpAsState(
        targetValue = underBarStartPaddingPosition.value[selectedTabIndex],
        animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
    )

    val underBarWidth: Dp by animateDpAsState(
        targetValue = underBarWidths.value[selectedTabIndex],
        animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
    )


    Box {
        QuackUnderDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(width.value)
        )
        Box(
            modifier = Modifier
                .height(QuackTabHeight)
                .onSizeChanged {
                    with(density) {
                        width.value = it.width.toDp()
                    }
                }
        ) {
            QuackMainTabTextRow(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(MainTabPadding),
                tabTitles = tabTitles,
                onTabSelected = onTabSelected,
                selectedTabIndex = selectedTabIndex,
                spacedBy = QuackMainTabSpacedBy + QuackMainTabTextInnerPadding * 2,
                onSizeChanged = { intSize, index ->
                    with(density) {
                        underBarWidths.value[index] =
                            (intSize.width.toDp() + QuackMainTabTextInnerPadding * 2)
                        underBarStartPaddingPosition.value[index + 1] =
                            underBarStartPaddingPosition.value[index] + underBarWidths.value[index] + QuackMainTabSpacedBy
                    }
                }
            )
            QuackTabUnderBar(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                padding = underBarStartPadding,
                width = underBarWidth,
                color = QuackColor.PumpkinOrange
            )
        }
    }

}

@Composable
private fun QuackMainTabTextRow(
    modifier: Modifier,
    tabTitles: List<String>,
    onTabSelected: (index: Int) -> Unit,
    selectedTabIndex: Int,
    spacedBy: Dp = 0.dp,
    onSizeChanged: (IntSize, Int) -> Unit = { _: IntSize, _: Int -> }
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacedBy)
    ) {
        tabTitles.forEachIndexed { index, title ->
            val textModifier = Modifier
                .clickable {
                    onTabSelected(index)
                }
                .onSizeChanged {
                    onSizeChanged(it, index)
                }
            if (index == selectedTabIndex) {
                QuackTitle2(text = title, modifier = textModifier)
            } else {
                QuackSubtitle(text = title, modifier = textModifier)
            }
        }
    }
}

@Composable
fun QuackSubTab(
    selectedTabIndex: Int,
    tabTitles: List<String>,
    onTabSelected: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    val width = remember { mutableStateOf(0.dp) }
    val underBarStartPadding: Dp by animateDpAsState(
        targetValue = QuackSubTabWidth * selectedTabIndex,
        animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
    )

    Box {
        QuackUnderDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(width.value + QuackTabHorizontalPadding * 2)
        )
        Box(
            modifier = Modifier
                .padding(SubTabPadding)
                .height(QuackTabHeight)
                .onSizeChanged {
                    with(density) {
                        width.value = it.width.toDp()
                    }
                }
        ) {
            QuackSubTabTextRow(
                modifier = Modifier.align(Alignment.CenterStart),
                tabTitles = tabTitles,
                onTabSelected = onTabSelected,
                selectedTabIndex = selectedTabIndex
            )

            QuackTabUnderBar(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                padding = underBarStartPadding,
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
    selectedTabIndex: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabTitles.forEachIndexed { index, title ->
            val textModifier = Modifier
                .width(QuackSubTabWidth)
                .clickable {
                    onTabSelected(index)
                }
            if (index == selectedTabIndex) {
                QuackTitle2(text = title, modifier = textModifier)
            } else {
                QuackSubtitle(text = title, modifier = textModifier)
            }
        }
    }
}

@Composable
private fun QuackUnderDivider(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(QuackColor.Gray3.value)
            .height(QuackTabUnderBarHeight)
    )
}

@Composable
private fun QuackTabUnderBar(
    modifier: Modifier,
    padding: Dp,
    width: Dp,
    color: QuackColor,
) {
    Box(
        modifier = modifier
            .padding(start = padding)
            .width(width)
            .height(QuackTabUnderBarHeight)
            .background(color.value)
    )
}

@Preview
@Composable
private fun QuackMainTabPreview() {
    val selectedMainTabIndex = remember { mutableStateOf(1) }
    QuackMainTab(selectedTabIndex = selectedMainTabIndex.value, tabTitles = listOf(
        "판매중", "거래완료", "숨김"
    ), onTabSelected = {
        selectedMainTabIndex.value = it
    })
}

@Preview
@Composable
private fun QuackSubTabPreview() {
    val selectedSubTabIndex = remember { mutableStateOf(1) }
    QuackSubTab(selectedTabIndex = selectedSubTabIndex.value, tabTitles = listOf(
        "피드", "컬렉션", "좋아요"
    ), onTabSelected = {
        selectedSubTabIndex.value = it
    })
}
