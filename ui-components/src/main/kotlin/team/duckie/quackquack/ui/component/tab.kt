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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.TabPadding
import team.duckie.quackquack.ui.typography.QuackSubtitle
import team.duckie.quackquack.ui.typography.QuackTitle2

private val QuackTabSpacedBy = 32.dp
private val QuackTabUnderBarHeight = 1.dp
private val QuackTabHeight = 36.dp
private val QuackSubTabWidth = 108.dp

/**
 * TODO : 추상화 단계 맞춰주기
 */

@Composable
fun QuackMainTab(
    selectedTabIndex: Int,
    tabTitleResources: List<Int>,
    onTabSelected: (index: Int) -> Unit,
) {
    val bottomBarWidths = tabTitleResources.map {
        (stringResource(id = it).length * 12 + 4).dp
    }
    val tabWidths = tabTitleResources.map {
        (stringResource(id = it).length * 12 + 32).dp
    }
    Box(
        modifier = Modifier.height(QuackTabHeight)
    ) {

        val offsetAnimation: Dp by animateDpAsState(
            targetValue = tabWidths.getSumByIndex(selectedTabIndex) + 14.dp,
            animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
        )

        val textAnimation: Dp by animateDpAsState(
            targetValue = bottomBarWidths[selectedTabIndex],
            animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
        )
        QuackTabTextRow(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(TabPadding),
            tabTitleResources = tabTitleResources,
            onTabSelected = onTabSelected,
            selectedTabIndex = selectedTabIndex
        )
        QuackTabUnderBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            width = tabWidths.getSumByIndex(tabTitleResources.size),
            background = QuackColor.Gray3
        )
        QuackTabUnderBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = offsetAnimation),
            width = textAnimation,
            background = QuackColor.PumpkinOrange
        )
    }

}

@Composable
fun QuackSubTab(
    selectedTabIndex: Int,
    tabTitleResources: List<Int>,
    onTabSelected: (index: Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .height(QuackTabHeight)
            .padding(TabPadding)
    ) {

        val offsetAnimation: Dp by animateDpAsState(
            targetValue = QuackSubTabWidth * selectedTabIndex,
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        )

        QuackTabTextRow(
            modifier = Modifier.align(Alignment.CenterStart),
            tabTitleResources = tabTitleResources,
            onTabSelected = onTabSelected,
            selectedTabIndex = selectedTabIndex
        )

        QuackTabUnderBar(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = offsetAnimation),
            width = QuackSubTabWidth,
            background = QuackColor.Black,
        )
    }
}

@Composable
private fun QuackTabTextRow(
    modifier: Modifier,
    tabTitleResources: List<Int>,
    onTabSelected: (index: Int) -> Unit,
    selectedTabIndex: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabTitleResources.forEachIndexed { index, titleResource ->
            val text = stringResource(titleResource)
            val textModifier = Modifier
                .width(QuackSubTabWidth)
                .clickable {
                    onTabSelected(index)
                }
            if (index == selectedTabIndex) {
                QuackTitle2(text = text, modifier = textModifier)
            } else {
                QuackSubtitle(text = text, modifier = textModifier)
            }
        }
    }
}

@Composable
private fun QuackTabUnderBar(
    modifier: Modifier,
    width: Dp,
    background: QuackColor,
) {

    Box(
        modifier = modifier
            .width(width)
            .height(QuackTabUnderBarHeight)
            .background(background.value)
    )

}

private fun List<Dp>.getSumByIndex(index: Int): Dp {
    var sum = 0.dp
    for (i in 0 until index) {
        sum += this[i]
    }
    return sum
}
