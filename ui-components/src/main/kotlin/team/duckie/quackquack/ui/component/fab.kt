/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */
package team.duckie.quackquack.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
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
private val QuackIconTextSpacing = 4.dp

// TODO: KDoc 필요
@Immutable
class QuackDialogMenuItem(
    val icon: QuackIcon,
    val text: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuackDialogMenuItem

        if (icon != other.icon) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = icon.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}

/**
 * QuackMenuFloatingActionButton 를 구현하였습니다.
 *
 * [QuackFloatingActionButton] 과는 다르게, 버튼을 클릭하였을 때
 * DialogMenu 가 출력됩니다.
 * [QuackDialogMenuItem] 을 통해 메뉴 아이템에 들어갈 icon, text 를 설정할 수 있습니다.
 *
 * @param expanded 메뉴가 현재 열려 있고 사용자에게 표시되는지 여부
 * @param onClickButton 버튼을 클릭했을 때 호출되는 콜백
 * @param onDismissRequest 사용자가 메뉴 닫기를 요청할 때 호출되는 콜백
 * @param menuItems 버튼을 클릭했을 때 나오는 메뉴의 [QuackDialogMenuItem] 리스트
 * @param onClickMenuItem 사용자가 메뉴 아이템을 클릭했을 때 호출되는 콜백
 * @see QuackDialogMenuItem
 */
@Composable
fun QuackMenuFloatingActionButton(
    expanded: Boolean,
    onClickButton: () -> Unit,
    onDismissRequest: () -> Unit,
    menuItems: PersistentList<QuackDialogMenuItem>,
    onClickMenuItem: (
        item: QuackDialogMenuItem,
    ) -> Unit,
) {
    val density = LocalDensity.current
    var buttonXOffset by remember {
        mutableStateOf(0.dp)
    }
    var buttonYOffset by remember {
        mutableStateOf(0.dp)
    }
    var buttonWidth by remember {
        mutableStateOf(0)
    }
    var dialogContentWidth by remember {
        mutableStateOf(0)
    }
    var dialogContentHeight by remember {
        mutableStateOf(0)
    }

    QuackBasicFloatingActionButton(
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                with(density) {
                    buttonXOffset = layoutCoordinates.boundsInWindow().center.x.toDp()
                    buttonYOffset = layoutCoordinates.boundsInWindow().center.y.toDp()
                    buttonWidth = layoutCoordinates.size.width
                }
            },
        icon = QuackIcon.Plus,
        onClick = onClickButton,
    )
    AnimatedVisibility(
        visible = expanded,
    ) {
        QuackDialog(
            offsetProvider = {
                IntOffset(
                    x = buttonXOffset.roundToPx() - dialogContentWidth + buttonWidth / 2,
                    y = buttonYOffset.roundToPx() - dialogContentHeight,
                )
            },
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = Modifier
                    .onGloballyPositioned { layoutCoordinates ->
                        dialogContentWidth = layoutCoordinates.size.width
                        dialogContentHeight = layoutCoordinates.size.height
                    },
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(
                    space = QuackFabItemSpacing,
                ),
            ) {
                QuackDialogMenu(
                    menuItems = menuItems,
                    onClickMenuItem = onClickMenuItem,
                    onDismissRequest = onDismissRequest,
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
 * [QuackMenuFloatingActionButton] 를 클릭했을 때 나오는 다이얼로그 입니다.
 *
 * Dialog 는 내부적으로 Android Dialog 로 구현되어있고, Compose View 로 래핑되어 있습니다.
 * 따라서 위치를 수동으로 조절할 수는 없고, Full Size Box 에서 offset 으로 조정되어야 합니다.
 *
 * 따라서 FloatingActionButton 의 Offset 을 구한 다음,
 *
 * xOffset = buttonXOffset - dialogContentWidth + buttonWidth / 2
 *
 * yOffset = buttonYOffset - dialogContentHeight
 *
 * 으로 위치를 조정시킬 수 있습니다.
 *
 * @param offsetProvider 내부 Composable 이 배치 될 offset
 * @param onDismissRequest 사용자가 메뉴 닫기를 요청할 때 호출되는 콜백
 * @param content Dialog 내부에 들어갈 Composable
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun QuackDialog(
    offsetProvider: Density.() -> IntOffset,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .quackClickable(
                    onClick = onDismissRequest
                ),
        ) {
            Box(
                modifier = Modifier.offset(
                    offset = offsetProvider,
                ),
                content = content,
            )
        }
    }
}

/**
 * QuackDialogMenu 를 구현하였습니다.
 *
 * Dialog Menu 에 표시될 Item List 입니다.
 *
 * @param menuItems 버튼을 클릭했을 때 나오는 메뉴의 [QuackDialogMenuItem] 리스트
 * @param onClickMenuItem 사용자가 메뉴 아이템을 클릭했을 때 호출되는 콜백
 * @param onDismissRequest 사용자가 메뉴 닫기를 요청할 때 호출되는 콜백
 * @see QuackDialogMenuItem
 */
@Composable
private fun QuackDialogMenu(
    menuItems: PersistentList<QuackDialogMenuItem>,
    onClickMenuItem: (
        item: QuackDialogMenuItem,
    ) -> Unit,
    onDismissRequest: () -> Unit,
) {
    QuackSurface(
        modifier = Modifier.applyQuackSize(
            width = QuackWidth.Wrap,
            height = QuackHeight.Wrap,
        ),
        shape = QuackPopUpMenuShape,
        backgroundColor = QuackColor.White,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = QuackMenuHorizontalPadding,
                end = QuackMenuHorizontalPadding,
                top = QuackMenuTopPadding,
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                items = menuItems,
                key = { item: QuackDialogMenuItem ->
                    item.text
                },
            ) { item: QuackDialogMenuItem ->
                QuackDialogMenuContent(
                    menuItem = item,
                    onClickMenuItem = onClickMenuItem,
                    onDismissRequest = onDismissRequest,
                )
            }
        }
    }
}

/**
 * QuackDialogMenuContent 를 구현하였습니다.
 *
 * DialogMenu 의 item 이 어떻게 구성되는지 정의합니다.
 *
 * @param menuItem [QuackDialogMenu] 에 들어갈 [QuackDialogMenuItem]
 * @param onClickMenuItem 사용자가 메뉴 아이템을 클릭했을 때 호출되는 콜백
 * @param onDismissRequest 사용자가 메뉴 닫기를 요청할 때 호출되는 콜백
 */
@Composable
@NonRestartableComposable
internal fun QuackDialogMenuContent(
    menuItem: QuackDialogMenuItem,
    onClickMenuItem: (
        item: QuackDialogMenuItem,
    ) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                bottom = QuackFabItemPadding,
            )
            .quackClickable(
                onClick = {
                    onClickMenuItem(menuItem)
                    onDismissRequest()
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackIconTextSpacing,
        ),
    ) {
        QuackImage(
            icon = menuItem.icon,
        )
        QuackSubtitle(
            text = menuItem.text,
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
@NonRestartableComposable
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
@NonRestartableComposable
private fun QuackBasicFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: QuackIcon,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(
                size = QuackFabSize,
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
