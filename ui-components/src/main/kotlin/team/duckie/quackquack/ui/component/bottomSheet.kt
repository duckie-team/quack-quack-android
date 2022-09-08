/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [bottomSheet.kt] created by doro on 22. 9. 4. 오전 1:38
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Gray3
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable
import kotlin.math.roundToInt

private val QuackBottomSheetHeadlineSize = DpSize(
    width = 40.dp,
    height = 4.dp
)
private val QuackBottomSheetHeadlineContentHeight = 20.dp
private val QuackBottomSheetHeadlineShape = RoundedCornerShape(2.dp)
private val QuackBottomSheetShape = RoundedCornerShape(
    topStart = 8.dp,
    topEnd = 8.dp,
)
private val QuackBottomSheetItemPadding = PaddingValues(
    horizontal = 16.dp,
    vertical = 10.dp
)

private val QuackBottomSheetHeadlinePadding = PaddingValues(
    horizontal = 16.dp
)

private val QuackBottomSheetDividerPadding = PaddingValues(
    vertical = 8.dp
)

private val QuackBottomSheetSpacedBy = 16.dp
private val QuackBottomSheetSubtitleHeight = 38.dp

private val Black60 = Color(0x99000000)

/**
 * TODO : onClick을 Smart하게 처리할 수 없을까?
 */
@Composable
fun QuackSimpleBottomSheet(
    onDismissRequest: () -> Unit,
    items: List<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
) {
    QuackBottomSheetColumn(
        onDismissRequest = onDismissRequest
    ) {
        QuackBottomSheetItems(
            items = items,
            onClick = onClick,
        )
    }
}

@Composable
fun QuackHeadlineBottomSheet(
    onDismissRequest: () -> Unit,
    headline: String,
    items: List<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
) {
    QuackBottomSheetColumn(
        onDismissRequest = onDismissRequest
    ) {
        QuackHeadLine2(
            modifier = Modifier.padding(QuackBottomSheetHeadlinePadding),
            text = headline
        )
        QuackBottomSheetItems(items = items, onClick = onClick)
    }

}

@Composable
fun QuackLineBottomSheet(
    onDismissRequest: () -> Unit,
    subtitle: String,
    items: List<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
) {
    QuackBottomSheetColumn(spacedBy = 0.dp, onDismissRequest = onDismissRequest) {
        QuackBottomSheetSubtitle(
            item = QuackBottomSheetItem(title = subtitle, important = false),
            onClick = null
        )
        QuackBottomSheetDivider()
        QuackBottomSheetItems(items = items, onClick = onClick)
    }
}

/**
 * TODO : spacedBy를 대입받는게 맞을까
 */
@Composable
private fun QuackBottomSheetColumn(
    spacedBy: Dp = QuackBottomSheetSpacedBy,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true, dismissOnBackPress = true)) {
        Box(
            modifier = Modifier
                .applyQuackSize(width = QuackWidth.Fill, height = QuackHeight.Fill)
                .background(Black60)
        ) {

            Column(
                modifier = Modifier
                    .applyQuackSize(width = QuackWidth.Fill, height = QuackHeight.Wrap)
                    .offset { IntOffset(0f.roundToInt(), offsetY.roundToInt()) }
                    .clip(QuackBottomSheetShape)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
                    .background(QuackColor.White.value)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(spacedBy)
            ) {
                QuackBottomSheetHeadline()
                ModalBottomSheetLayout(sheetContent = ) {
                    
                }
                content()
            }
        }
    }
}

@Composable
private fun QuackBottomSheetHeadline(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(QuackBottomSheetHeadlineContentHeight),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(QuackBottomSheetHeadlineSize)
                .background(Gray3.value)
                .clip(QuackBottomSheetHeadlineShape),
        )
    }
}

@Composable
private fun QuackBottomSheetItems(
    items: List<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
) {
    Column {
        items.forEach { quackBottomSheetItem ->
            QuackBottomSheetSubtitle(
                item = quackBottomSheetItem,
                onClick = onClick,
            )
        }


    }
}

/**
 * TODO : onClick을 저렇게 사용하는게 맞을까.. 아니면 수정해야할까?
 */
@Composable
private fun QuackBottomSheetSubtitle(
    item: QuackBottomSheetItem,
    onClick: ((QuackBottomSheetItem) -> Unit)?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(QuackBottomSheetSubtitleHeight)
            .quackClickable(
                onClick = when (onClick) {
                    null -> null
                    else -> {
                        { onClick(item) }
                    }
                }
            )
            .padding(QuackBottomSheetItemPadding)

    ) {
        QuackSubtitle(
            text = item.title, color = when (item.important) {
                true -> QuackColor.OrangeRed
                else -> QuackColor.Black
            }
        )
    }
}

@Composable
private fun QuackBottomSheetDivider() {
    Box(
        modifier = Modifier.padding(QuackBottomSheetDividerPadding)
    ) {
        Divider(color = Gray3.value)
    }

}

@Preview
@Composable
private fun QuackBottomSheetPreview() {
    val items = listOf(
        QuackBottomSheetItem("subtitle"),
        QuackBottomSheetItem("subtitle"),
        QuackBottomSheetItem("subtitle", true)
    )
    val headline = "headline"
    val isVisible = remember { mutableStateOf(true) }

    if ( isVisible.value) {
        QuackSimpleBottomSheet(
            onDismissRequest = { isVisible.value = false },
            items = items,
            onClick = {},
        )
    }


}

data class QuackBottomSheetItem(
    val title: String,
    val important: Boolean = false,
)

class MyClass
