/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import okhttp3.internal.immutableListOf
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

/**
 * QuackBottomNavigationBar 를 그리는데 필요한 리소스들을 정의합니다.
 */
private object QuackBottomNavigationDefaults {
    val Height = 52.dp
    val BackgroundColor = QuackColor.White

    val IconSize = DpSize(
        all = 24.dp,
    )
}

/**
 * QuackBottomNavigation 을 구현합니다.
 *
 * 각각 아이템들은 화면에 1:N 비율로 가로 길이가 결정됩니다.
 *
 * icon 은 defaultIcon 과 selectedIcon 을 data class 를 통해 둘 다 전달받고
 * selectedIndex 의 상태값과 비교하여 보여줘야하는 Icon 을 결정합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param selectedIndex 현재 선택되어있는 index 상태값
 * @param onClick BottomNavigation 의 클릭 이벤트
 */
@Composable
public fun QuackBottomNavigation(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onClick: (index: Int) -> Unit,
): Unit = with(
    receiver = QuackBottomNavigationDefaults,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = Height,
            )
            .background(
                color = BackgroundColor.composeColor,
            ),
    ) {
        rememberBottomNavigationIcons().fastForEachIndexed { index, icons ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(
                        ratio = 1f,
                    )
                    .quackClickable(
                        rippleEnabled = false,
                    ) {
                        onClick(
                            /* index = */
                            index,
                        )
                    },
                contentAlignment = Alignment.Center,
            ) {
                QuackImage(
                    src = icons.pick(
                        isSelected = index == selectedIndex,
                    ),
                    size = IconSize,
                )
            }
        }
    }
}

/**
 * [QuackBottomNavigation] 에서 사용되는 아이콘들을 반환해줍니다.
 *
 * @return [BottomNavigationIcon] 의 모음
 */
@Composable
private fun rememberBottomNavigationIcons() = remember {
    immutableListOf(
        BottomNavigationIcon(
            defaultIcon = QuackIcon.BottomNavHome,
            selectedIcon = QuackIcon.BottomNavHomeSelected,
        ),
        BottomNavigationIcon(
            defaultIcon = QuackIcon.BottomNavSearch,
            selectedIcon = QuackIcon.BottomNavSearchSelected,
        ),
        BottomNavigationIcon(
            defaultIcon = QuackIcon.BottomNavNotice,
            selectedIcon = QuackIcon.BottomNavNoticeSelected,
        ),
        BottomNavigationIcon(
            defaultIcon = QuackIcon.BottomNavMessage,
            selectedIcon = QuackIcon.BottomNavMessageSelected,
        ),
    )
}

/**
 * [QuackBottomNavigation] 에서 표시할 아이콘들을 정의합니다.
 *
 * @property defaultIcon 기본 아이콘 (선택 X)
 * @property selectedIcon 선택 상태의 아이콘
 */
private data class BottomNavigationIcon(
    val defaultIcon: QuackIcon,
    val selectedIcon: QuackIcon,
) {
    /**
     * 주어진 상태에 맞는 아이콘 리소스를 가져옵니다.
     *
     * @param isSelected 현재 선택된 상태인지 여부
     *
     * @return [isSelected] 여부에 따라 사용할 [QuackIcon]
     */
    fun pick(
        isSelected: Boolean,
    ) = when (isSelected) {
        true -> selectedIcon
        else -> defaultIcon
    }
}
