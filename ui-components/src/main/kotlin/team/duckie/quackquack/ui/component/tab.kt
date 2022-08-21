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
import androidx.compose.runtime.*
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
private val QuackTabBottomDividerWidth = 1.dp
private val QuackTabHeight = 36.dp

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

        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(TabPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(QuackTabSpacedBy)
        ) {
            tabTitleResources.forEachIndexed { index, titleResource ->
                val text = stringResource(titleResource)
                val modifier = Modifier.clickable {
                    onTabSelected(index)
                }
                if (index == selectedTabIndex) {
                    QuackTitle2(text = text, modifier = modifier)
                } else {
                    QuackSubtitle(text = text, modifier = modifier)
                }
            }
        }

        Box(
            modifier = Modifier
                .width(tabWidths.getSumByIndex(tabTitleResources.size))
                .height(QuackTabBottomDividerWidth)
                .background(QuackColor.Gray3.value)
                .align(Alignment.BottomCenter)
        )

        Box(
            modifier = Modifier
                .padding(start = offsetAnimation)
                .width(textAnimation)
                .height(QuackTabBottomDividerWidth)
                .align(Alignment.BottomStart)
                .background(QuackColor.PumpkinOrange.value)
        )
    }

}

@Composable
fun QuackSubTab(
    selectedTabIndex: Int,
    tabTitleResources: List<Int>,
    onTabSelected: (index: Int) -> Unit,
) {

    val tabWidths = tabTitleResources.map {
        (stringResource(id = it).length * 12 + 32).dp
    }
    Box(
        modifier = Modifier.height(QuackTabHeight)
    ) {

        val offsetAnimation: Dp by animateDpAsState(
            targetValue = tabWidths.getSumByIndex(selectedTabIndex),
            animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
        )

        val textAnimation: Dp by animateDpAsState(
            targetValue = tabWidths[selectedTabIndex],
            animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(TabPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(QuackTabSpacedBy)
        ) {
            tabTitleResources.forEachIndexed { index, titleResource ->
                val text = stringResource(titleResource)
                val modifier = Modifier.clickable {
                    onTabSelected(index)
                }
                if (index == selectedTabIndex) {
                    QuackTitle2(text = text, modifier = modifier)
                } else {
                    QuackSubtitle(text = text, modifier = modifier)
                }
            }
        }

        Box(
            modifier = Modifier
                .width(tabWidths.getSumByIndex(tabTitleResources.size))
                .height(QuackTabBottomDividerWidth)
                .background(QuackColor.Gray3.value)
                .align(Alignment.BottomCenter)
        )

        Box(
            modifier = Modifier
                .padding(start = offsetAnimation)
                .width(textAnimation)
                .height(QuackTabBottomDividerWidth)
                .align(Alignment.BottomStart)
                .background(QuackColor.Black.value)
        )
    }


}

private fun List<Dp>.getSumByIndex(index: Int) : Dp{
    var sum = 0.dp
    for ( i in 0 until index){
        sum += this[i]
    }
    return sum
}
