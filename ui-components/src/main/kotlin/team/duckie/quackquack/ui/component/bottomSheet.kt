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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Gray3
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize

private val BottomSheetShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
)

private val QuackBottomSheetHandleSize = DpSize(
    width = 40.dp,
    height = 4.dp,
)

private val QuackBottomSheetHandleShape = RoundedCornerShape(
    size = 2.dp,
)
private val QuackBottomSheetHandlePadding = PaddingValues(
    vertical = 8.dp,
)

private val QuackBottomSheetPadding = PaddingValues(
    vertical = 16.dp,
)

/**
 * [QuackBottomSheet]를 구현합니다.
 *
 * @param useHandle
 * @param bottomSheetState
 * @param sheetContent
 * @param content
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackBottomSheet(
    useHandle: Boolean = false,
    bottomSheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            Column(
                modifier = Modifier
                    .clip(
                        shape = BottomSheetShape
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .applyQuackSize(
                            width = QuackWidth.Fill,
                            height = QuackHeight.Wrap,
                        )
                        .background(
                            color = QuackColor.White.value,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    QuackBottomSheetHandle(
                        useHandle = useHandle,
                    )
                    sheetContent()
                }
            }
        },
        sheetBackgroundColor = Color.Transparent,
        sheetState = bottomSheetState,
        scrimColor = QuackColor.Black80.value,
    ) {
        content()
    }

}

/**
 * @param useHandle
 */
@Composable
private fun QuackBottomSheetHandle(
    useHandle: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                paddingValues = QuackBottomSheetHandlePadding,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (useHandle) {
            Box(
                modifier = Modifier
                    .size(
                        size = QuackBottomSheetHandleSize,
                    )
                    .background(
                        color = Gray3.value,
                    )
                    .clip(
                        shape = QuackBottomSheetHandleShape,
                    )
            )
        }
    }
}

/**
 * [QuackSimpleBottomSheet]를 구현합니다.
 *
 * @param bottomSheetState
 * @param items
 * @param content
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackSimpleBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    items: PersistentList<QuackBottomSheetItem>,
    content: @Composable () -> Unit,
) {
    QuackBottomSheet(
        bottomSheetState = bottomSheetState,
        content = content,
        sheetContent = {
            QuackBottomSheetColumn {
                QuackBottomSheetSubtitles(
                    items = items,
                )
            }
        },
        useHandle = true,
    )

}

/**
 * [QuackHeadlineBottomSheet]를 구현합니다.
 *
 * @param bottomSheetState
 * @param headline
 * @param items
 * @param content
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackHeadlineBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    headline: String,
    items: PersistentList<QuackBottomSheetItem>,
    content: @Composable () -> Unit,
) {
    QuackBottomSheet(
        bottomSheetState = bottomSheetState,
        content = content,
        sheetContent = {
            QuackBottomSheetColumn {
                QuackBottomSheetHeadline(
                    headline = headline,
                )
                Spacer(
                    modifier = Modifier.height(
                        height = 16.dp,
                    ),
                )
                QuackBottomSheetSubtitles(
                    items = items,
                )
            }
        },
        useHandle = true,
    )

}

/**
 * [QuackSubtitleBottomSheet]를 구현합니다.
 *
 * @param bottomSheetState
 * @param items
 * @param subtitle
 * @param content
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuackSubtitleBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    subtitle: String,
    items: PersistentList<QuackBottomSheetItem>,
    content: @Composable () -> Unit,
) {
    QuackBottomSheet(
        bottomSheetState = bottomSheetState,
        content = content,
        sheetContent = {
            QuackBottomSheetColumn {
                QuackBottomSheetSubtitleItem(
                    isImportant = false,
                    title = subtitle,
                )
                Divider(
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    ),
                    color = Gray3.value,
                )
                QuackBottomSheetSubtitles(
                    items = items,
                )
            }
        },
        useHandle = true,
    )

}

/**
 * @param isImportant
 * @param title
 */
@Composable
private fun QuackBottomSheetSubtitleItem(
    isImportant: Boolean,
    title: String,
) {
    val textColor = when (isImportant) {
        true -> QuackColor.OrangeRed
        else -> QuackColor.Black
    }

    Box(
        modifier = Modifier
            .applyQuackSize(
                width = QuackWidth.Fill,
                height = QuackHeight.Custom(
                    height = 38.dp,
                ),
            )
            .padding(
                start = 16.dp,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        QuackSubtitle(
            text = title,
            color = textColor,
        )
    }
}

/**
 * [QuackBottomSheetSubtitles]
 *
 * @param items
 */
@Composable
private fun QuackBottomSheetSubtitles(
    items: PersistentList<QuackBottomSheetItem>,
) {
    items.forEach { item ->
        QuackBottomSheetSubtitleItem(
            isImportant = item.isImportant,
            title = item.title,
        )
    }
}

/**
 * [QuackBottomSheetHandle] 은 BottomSheet 상단에 있는 Handle 입니다.
 *
 * @param headline
 */
@Composable
private fun QuackBottomSheetHeadline(
    headline: String
) {
    Box(
        modifier = Modifier.padding(
            start = 16.dp,
        ),
    ) {
        QuackHeadLine2(
            text = headline,
        )
    }
}

/**
 * [QuackBottomSheetColumn] 은 BottomSheet 에서 공통적으로 사용되는 Column 입니다.
 *
 * @param content
 */
@Composable
private fun QuackBottomSheetColumn(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.padding(
            paddingValues = QuackBottomSheetPadding,
        ),
    ) {
        content()
    }
}

data class QuackBottomSheetItem(
    val title: String,
    val isImportant: Boolean,
)
