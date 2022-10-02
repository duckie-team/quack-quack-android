/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon

private val QuackFabContainerElevation = 3.dp

private val QuackFabContainerShape = CircleShape
private val QuackFabPopUpMenuShape = RoundedCornerShape(
    size = 12.dp,
)

private val QuackFabIconSize = DpSize(
    width = 20.dp,
    height = 20.dp,
)
private val QuackFabContainerSize = 48.dp

private val QuackFabIconResource = QuackIcon.Plus
private val QuackFabIconTint = QuackColor.White
private const val QuackFabIconRotate = 45f

private val QuackFabContainerColor = QuackColor.DuckieOrange
private val QuackFabBackgroundDimmedColor = Color.Black.copy(
    alpha = 0.6f,
)

private val QuackFabContainerPadding = PaddingValues(
    horizontal = 16.dp,
    vertical = 20.dp,
)
private val QuackFabMenuSpacedBy = 16.dp
private val QuackFabMenuTextPadding = PaddingValues(
    horizontal = 4.dp,
    vertical = 3.dp,
)

/**
 * 꽥꽥의 기본 FloatingActionButton 을 그립니다.
 *
 * @param icon 이 FAB 에 표시할 아이콘
 * @param onClick 이 FAB 이 클릭됐을 때 호출될 람다
 */
@Composable
public fun QuackFab(
    icon: QuackIcon,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = Modifier.size(
            size = QuackFabContainerSize,
        ),
        shape = QuackFabContainerShape,
        backgroundColor = QuackFabContainerColor,
        elevation = QuackFabContainerElevation,
        onClick = onClick,
    ) {
        Box(
            // needs for set size in box inlining
            modifier = Modifier.size(
                size = QuackFabIconSize,
            ),
        ) {
            QuackImage(
                src = QuackFabIconResource,
                overrideSize = QuackFabIconSize,
                tint = QuackFabIconTint,
            )
        }
    }
}

/**
 * [QuackFab] 위에 팝업 메뉴를 추가로 그립니다. 다이얼로그보다는 팝업의 형태에
 * 더 가까우므로 [Dialog] 가 아닌 [Popup] 컴포저블을 사용하여 구현합니다.
 * 배경 dimmed 는 전체 화면을 [Box] 로 채운 후, [QuackFabBackgroundDimmedColor] 로
 * 배경 색상을 지정하여 구현합니다.
 */
@Composable
public fun QuackMenuFab() {
}
