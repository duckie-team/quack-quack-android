/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [fab.kt] created by Ji Sungbin on 22. 9. 3. 오후 1:15
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

private val QuackFloatingActionButtonSize = 48.dp

private val QuackFloatingActionButtonShape = CircleShape
private val QuackPopUpMenuShape = RoundedCornerShape(12.dp)

private val QuackFabTopPadding = 20.dp
private val QuackFabHorizontalPadding = 16.dp
private val QuackFabItemPadding = 16.dp

private val QuackFabItemSpacing = 8.dp

@Immutable
class QuackPopUpMenuItem(
    val quackIcon: QuackIcon,
    val text: String,
    val onClick: () -> Unit,
)

private val dummyPopUpMenuItem = persistentListOf(
    QuackPopUpMenuItem(
        quackIcon = QuackIcon.Close,
        text = "피드",
        onClick = {}
    ),
    QuackPopUpMenuItem(
        quackIcon = QuackIcon.Close,
        text = "덕딜",
        onClick = {}
    ),
)

@Composable
fun FloatingMenuActionButton(
    items: PersistentList<QuackPopUpMenuItem>,
) {
    var expanded by remember { mutableStateOf(false) }
    var positionInRoot by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        QuackText(
            text = "${positionInRoot.x} , ${positionInRoot.y}}",
            style = QuackTextStyle.Body1,
        )
    }
    BasicFloatingActionButton(
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                positionInRoot = layoutCoordinates.positionInRoot()
            },
        onClick = {
            expanded = true
        },
    )
    if (expanded) {
        Column {
            QuackDialog(
                buttonOffset = positionInRoot,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    QuackDialogMenu(
                        items = items,
                    )
                    Spacer(
                        modifier = Modifier.height(
                            height = QuackFabItemSpacing,
                        ),
                    )
                    BasicFloatingActionButton(
                        onClick = {
                            expanded = true
                        }
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun QuackDialog(
    buttonOffset: Offset,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onDismissRequest()
                },
            contentAlignment = Alignment.TopStart,
        ) {
            Box(
                modifier = Modifier.offset(
                    x = (buttonOffset.x).dp,
                    y = (buttonOffset.y).dp,
                ),
            ) {
                content()
            }

        }
    }
}

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
                    horizontal = QuackFabHorizontalPadding,
                )
                .padding(
                    top = QuackFabTopPadding
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items.forEach { item: QuackPopUpMenuItem ->
                QuackDialogMenuContent(
                    itemIcon = item.quackIcon,
                    itemText = item.text,
                    onClickItem = item.onClick,
                )
                Spacer(
                    modifier = Modifier.height(
                        height = QuackFabItemPadding,
                    ),
                )
            }
        }
    }
}


@Composable
@NonRestartableComposable
internal fun QuackDialogMenuContent(
    itemIcon: QuackIcon,
    itemText: String,
    onClickItem: () -> Unit,
) {
    Row(
        modifier = Modifier.quackClickable {
            onClickItem()
        },
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        QuackNonClickableImage(
            icon = itemIcon,
        )
        QuackText(
            text = itemText,
            style = QuackTextStyle.Subtitle,
        )
    }
}

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
) {
    BasicFloatingActionButton(
        onClick = onClick,
    )
}

@Composable
private fun BasicFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier
            .applyQuackSize(
                width = QuackWidth.Custom(QuackFloatingActionButtonSize),
                height = QuackHeight.Custom(QuackFloatingActionButtonSize),
            ),
        shape = QuackFloatingActionButtonShape,
        backgroundColor = QuackColor.DuckieOrange,
    ) {
        QuackImage(
            icon = QuackIcon.Close,
            onClick = onClick,
        )
    }
}

@Preview
@Composable
fun fabPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        FloatingMenuActionButton(
            items = dummyPopUpMenuItem,
        )
    }
}
