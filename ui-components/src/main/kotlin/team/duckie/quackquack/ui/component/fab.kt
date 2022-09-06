/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [fab.kt] created by Ji Sungbin on 22. 9. 3. 오후 1:15
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackFabSize = 48.dp

private val QuackFabShape = CircleShape
private val QuackPopUpMenuShape = RoundedCornerShape(12.dp)

private val QuackMenuTopPadding = 20.dp
private val QuackMenuHorizontalPadding = 16.dp
private val QuackFabItemPadding = 16.dp

private val QuackFabItemSpacing = 8.dp

@Immutable
class QuackPopUpMenuItem(
    val icon: QuackIcon,
    val text: String,
    val onClick: () -> Unit,
)

/**
 * QuackMenuFloatingActionButton 를 구현하였습니다.
 *
 * [QuackFloatingActionButton] 과는 다르게, 버튼을 클릭하였을 때
 * DialogMenu 가 출력됩니다.
 * [QuackPopUpMenuItem] 을 통해 메뉴 아이템에 들어갈 icon, text, onClick 을 설정할 수 있습니다.
 *
 * @param items 버튼을 클릭했을 때 나오는 메뉴의 아이템 리스트 [QuackPopUpMenuItem]
 * @see QuackPopUpMenuItem
 */
@Composable
fun QuackMenuFloatingActionButton(
    expanded: Boolean,
    onClickButton: () -> Unit,
    onDismissRequest: () -> Unit,
    items: PersistentList<QuackPopUpMenuItem>,
) {
    var positionInRoot by remember { mutableStateOf(Offset.Zero) }
    var menuSize by remember { mutableStateOf(IntSize.Zero) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    QuackBasicFloatingActionButton(
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                positionInRoot = layoutCoordinates.positionInWindow()
                buttonSize = layoutCoordinates.size
            },
        icon = QuackIcon.Plus,
        onClick = onClickButton,
    )
    if (expanded) {
        QuackDialog(
            buttonOffset = positionInRoot,
            menuSize = menuSize,
            buttonSize = buttonSize,
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    menuSize = layoutCoordinates.size
                },
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(
                    space = QuackFabItemSpacing,
                ),
            ) {
                QuackDialogMenu(
                    items = items,
                )
                QuackBasicFloatingActionButton(
                    icon = QuackIcon.Close,
                    onClick = onDismissRequest,
                )
            }
        }
    }
}

/**
 * QuackMenuFloatingActionButton 를 클릭했을 때 나오는 다이얼로그 입니다.
 *
 * Dialog 는 내부적으로 Android Dialog 로 구현되어있고, Compose View 로 래핑되어 있습니다.
 * 따라서 위치를 수동으로 조절할 수는 없고, Full Size Box 에서 offset 으로 조정되어야 합니다.
 *
 * 따라서 FloatingActionButton 의 Offset 을 구한 다음,
 * Menu 크기만큼 더하고 Button 크기만큼 빼면 위치를 조정시킬 수 있습니다.
 *
 * dpOffsetX - menuWidth + buttonWidth
 *
 * @param buttonOffset FloatingActionButton 의 offset
 * @param menuSize menu container 크기
 * @param buttonSize button container 크기
 * @param onDismissRequest Menu 를 닫으라는 명령이 떨어졌을 때의 동작
 * @param content Dialog 내부에 들어갈 Composable
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun QuackDialog(
    buttonOffset: Offset,
    menuSize: IntSize,
    buttonSize: IntSize,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        val density = LocalDensity.current
        val dpOffsetX = with(
            receiver = density,
        ) {
            buttonOffset.x.toDp()
        }
        val dpOffsetY = with(
            receiver = density,
        ) {
            buttonOffset.y.toDp()
        }
        val menuWidth = with(
            receiver = density,
        ) {
            menuSize.width.toDp()
        }
        val menuHeight = with(
            receiver = density,
        ) {
            menuSize.height.toDp()
        }
        val buttonWidth = with(
            receiver = density,
        ) {
            buttonSize.width.toDp()
        }
        val buttonHeight = with(
            receiver = density,
        ) {
            buttonSize.height.toDp()
        }
        var xOffset by remember {
            mutableStateOf(0)
        }
        var yOffset by remember {
            mutableStateOf(0)
        }
        xOffset = (dpOffsetX - menuWidth + buttonWidth).value.toInt()
        yOffset = (dpOffsetY - menuHeight + buttonHeight).value.toInt()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .quackClickable(
                    onClick = onDismissRequest
                )
                .offset {
                    IntOffset(
                        x = xOffset,
                        y = yOffset,
                    )
                },
            contentAlignment = Alignment.TopStart,
        ) {
            Box(
                content = content
            )
        }
    }
}

/**
 * QuackDialogMenu 를 구현하였습니다.
 *
 * 메뉴의 아이템 리스트를 보여주는 Composable 입니다.
 *
 * @param items 버튼을 클릭했을 때 나오는 메뉴의 아이템 리스트 [QuackPopUpMenuItem]
 * @see QuackPopUpMenuItem
 */
@Composable
@NonRestartableComposable
private fun QuackDialogMenu(
    items: PersistentList<QuackPopUpMenuItem>,
) {
    QuackSurface(
        modifier = Modifier.applyQuackSize(
            width = QuackWidth.Wrap,
            height = QuackHeight.Wrap,
        ),
        shape = QuackPopUpMenuShape,
        backgroundColor = QuackColor.White,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = QuackMenuHorizontalPadding,
                )
                .padding(
                    top = QuackMenuTopPadding,
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items.forEach { item: QuackDialogMenuItem ->
                QuackDialogMenuContent(
                    item = item,
                )
            }
        }
    }
}

/**
 * QuackDialogMenuContent 를 구현하였습니다.
 *
 * 메뉴의 Item List Content 입니다.
 *
 * @param itemIcon item 의 icon
 * @param itemText item 의 text
 * @param onClickItem item 의 클릭 이벤트
 */
@Composable
@NonRestartableComposable
internal fun QuackDialogMenuContent(
    itemIcon: QuackIcon,
    itemText: String,
    onClickItem: () -> Unit,
) {
    Row(
        modifier = Modifier.quackClickable(
            onClick = onClickItem,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackImage(
            icon = itemIcon,
        )
        QuackSubtitle(
            text = itemText,
        )
    }
}

/**
 * QuackFloatingActionButton 을 구현하였습니다.
 *
 * Box 로 구현되어 있기 때문에, 다른 Composable 위에 배치될 수 있습니다.
 *
 * @param icon FloatingActionButton 에 들어갈 icon
 * @param onClick 버튼 클릭 이벤트
 */
@Composable
fun QuackFloatingActionButton(
    icon: QuackIcon,
    onClick: () -> Unit,
) {
    QuackBasicFloatingActionButton(
        icon = icon,
        onClick = onClick,
    )
}

/**
 * QuackFloatingActionButton 의 기초가 되는 Composable
 *
 * Content 의 사이즈를 알아야 하는 경우로 인해 Modifier 를 가집니다.
 *
 * @param modifier [Modifier]
 * @param icon FloatingActionButton 에 들어갈 icon
 * @param onClick 버튼 클릭 이벤트
 */
@Composable
private fun QuackBasicFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: QuackIcon,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(
                QuackFabSize,
            )
            .clip(
                shape = QuackFabShape,
            )
            .background(
                color = QuackColor.DuckieOrange.value,
            )
            .quackClickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            tint = QuackColor.White,
            icon = icon,
        )
    }
}
