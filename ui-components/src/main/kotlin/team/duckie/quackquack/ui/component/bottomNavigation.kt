/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackBottomNavigationHeight = 52.dp
private val QuackBottomNavigationItemSize = DpSize(
    width = 24.dp,
    height = 24.dp,
)

/**
 * QuackBottomNavigation 을 구현합니다.
 *
 * 현재 LazyRow 의 width 를 계산해서 icons 의 갯수만큼 나눈 후에
 * 하나의 탭 크기를 결정합니다.
 *
 * icon 은 defaultIcon 과 selectedIcon 을 data class 를 통해 둘 다 전달받고
 * selectedIndex 의 상태값과 비교하여 보여줘야하는 Icon 을 결정합니다.
 *
 * @param modifier 다양한 Align 에서 사용하기 위해 Modifier 를 열어둡니다.
 * @param backgroundColor BottomNavigation 배경색
 * @param selectedIndex 현재 선택되어있는 index 상태값
 * @param onClick BottomNavigation 의 클릭 이벤트
 *
 */
@Composable
public fun QuackBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: QuackColor = QuackColor.White,
    selectedIndex: Int,
    onClick: (Int) -> Unit,
) {
    val icons = getBottomNavigationItems()
    val density = LocalDensity.current
    val barWidth = remember { mutableStateOf(0.dp) }
    val screenWidth = barWidth.value / icons.size

    LazyRow(
        modifier = modifier
            .applyQuackSize(
                width = QuackWidth.Fill,
                height = QuackHeight.Custom(
                    height = QuackBottomNavigationHeight,
                ),
            )
            .background(
                color = backgroundColor.composeColor,
            )
            .onSizeChanged { size ->
                with(density) {
                    barWidth.value = size.width.toDp()
                }
            },
    ) {
        itemsIndexed(icons) { index, _ ->
            QuackBottomNavigationItem(
                index = index,
                width = screenWidth,
                icon = when (selectedIndex) {
                    index -> icons[index].selectedIcon
                    else -> icons[index].defaultIcon
                },
                onClick = onClick,
            )
        }
    }
}

/**
 * QuackBottomNavigationItem 을 구현합니다.
 *
 * @param index 현재 탭이 몇 번째 index 인지에 대한 상태값, onClick 에 인자로 전달해주기 위해 넘겨받습니다.
 * @param width 현재 탭의 크기 -> 부모의 크기에 따라 달라집니다.
 * @param icon 현재 탭의 아이콘
 * @param onClick 탭의 클릭 이벤트
 */

@Composable
private fun QuackBottomNavigationItem(
    index: Int,
    width: Dp,
    icon: QuackIcon,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .applyQuackSize(
                width = QuackWidth.Custom(
                    width = width,
                ),
                height = QuackHeight.Fill,
            )
            .quackClickable {
                onClick(index)
            },
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            src = icon,
            overrideSize = QuackBottomNavigationItemSize,
            rippleEnabled = false,
        )
    }
}

/**
 * [getBottomNavigationItems] 을 구현합니다
 *
 * @return [QuackBottomNavigation] 에서 사용되는 아이콘들의 data class list를 반환해줍니다.
 */
private fun getBottomNavigationItems() = listOf(
    BottomNavigationItem(
        defaultIcon = QuackIcon.BottomNavHome,
        selectedIcon = QuackIcon.BottomNavHomeSelected,
    ),
    BottomNavigationItem(
        defaultIcon = QuackIcon.BottomNavSearch,
        selectedIcon = QuackIcon.BottomNavSearchSelected,
    ),
    BottomNavigationItem(
        defaultIcon = QuackIcon.BottomNavNotice,
        selectedIcon = QuackIcon.BottomNavNoticeSelected,
    ),
    BottomNavigationItem(
        defaultIcon = QuackIcon.BottomNavMessage,
        selectedIcon = QuackIcon.BottomNavMessageSelected,
    ),
)

private data class BottomNavigationItem(
    val defaultIcon: QuackIcon,
    val selectedIcon: QuackIcon,
)
