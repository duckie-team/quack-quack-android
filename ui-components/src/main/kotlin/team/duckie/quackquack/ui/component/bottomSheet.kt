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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Gray3
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable

private val BottomSheetShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)

private val QuackBottomSheetHandleSize = DpSize(
    width = 40.dp,
    height = 4.dp,
)

private val QuackBottomSheetHandleShape = RoundedCornerShape(
    size = 2.dp,
)
private val QuackBottomSheetContentPadding = PaddingValues(
    vertical = 8.dp,
)

private val QuackBottomSheetPadding = PaddingValues(
    vertical = 16.dp,
)

private val QuackBottomSheetStartPadding = PaddingValues(
    start = 16.dp,
)

private val QuackBottomSheetSubtitleItemHeight = 38.dp

/**
 * [QuackBottomSheet]를 구현합니다.
 *
 * @param useHandle BottomSheet 의 handle 을 사용할지에 대한 여부
 * @param bottomSheetState BottomSheet 의 상태값
 * @param sheetContent BottomSheet 의 sheetContent 에 그릴 화면
 * @param content BottomSheet 를 호출하는 나머지 화면
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun QuackBottomSheet(
    useHandle: Boolean = false,
    bottomSheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.applyQuackSize(
            width = QuackWidth.Fill,
            height = QuackHeight.Fill,
        ),
        sheetContent = {
            QuackBottomSheetContent(
                useHandle = useHandle,
                sheetContent = sheetContent,
            )
        },
        sheetShape = BottomSheetShape,
        sheetBackgroundColor = QuackColor.Transparent.composeColor,
        sheetState = bottomSheetState,
        scrimColor = QuackColor.Black80.composeColor,
    ) {
        content()
    }
}

/**
 * [QuackSimpleBottomSheet]를 구현합니다.
 *
 * @param bottomSheetState BottomSheet 의 상태값
 * @param items BottomSheet 에 나타날 목록형 아이템 데이터 값
 * @param onClick BottomSheet 의 목록형 아이템 클릭 이벤트
 * @param content BottomSheet 를 호출하는 나머지 화면
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun QuackSimpleBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    items: PersistentList<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
    content: @Composable () -> Unit,
) {
    QuackBottomSheet(
        bottomSheetState = bottomSheetState,
        content = content,
        sheetContent = {
            QuackBottomSheetColumn {
                QuackBottomSheetSubtitles(
                    items = items,
                    onClick = onClick,
                )
            }
        },
        useHandle = true,
    )
}

/**
 * [QuackHeadlineBottomSheet]를 구현합니다.
 *
 * @param bottomSheetState BottomSheet 의 상태값
 * @param headline Headline 텍스트 값
 * @param items BottomSheet 에 나타날 목록형 아이템 데이터 값
 * @param onClick BottomSheet 의 목록형 아이템 클릭 이벤트
 * @param content BottomSheet 를 호출하는 나머지 화면
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun QuackHeadlineBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    headline: String,
    items: PersistentList<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
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
                    modifier = Modifier.padding(
                        paddingValues = QuackBottomSheetContentPadding,
                    ),
                )
                QuackBottomSheetSubtitles(
                    items = items,
                    onClick = onClick,
                )
            }
        },
        useHandle = true,
    )
}

/**
 * [QuackSubtitleBottomSheet]를 구현합니다.
 *
 * @param bottomSheetState BottomSheet 의 상태값
 * @param items BottomSheet 에 나타날 목록형 아이템 데이터 값
 * @param subtitle Subtitle 텍스트 값
 * @param onClick BottomSheet 의 목록형 아이템 클릭 이벤트
 * @param content BottomSheet 를 호출하는 나머지 화면
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun QuackSubtitleBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    subtitle: String,
    items: PersistentList<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
    content: @Composable () -> Unit,
) {
    QuackBottomSheet(
        bottomSheetState = bottomSheetState,
        content = content,
        sheetContent = {
            QuackBottomSheetColumn {
                QuackBottomSheetSubtitleItem(
                    item = QuackBottomSheetItem(
                        title = subtitle,
                        isImportant = false,
                    ),
                    onClick = onClick,
                )
                Divider(
                    modifier = Modifier.padding(
                        paddingValues = QuackBottomSheetContentPadding
                    ),
                    color = Gray3.composeColor,
                )
                QuackBottomSheetSubtitles(
                    items = items,
                    onClick = onClick,
                )
            }
        },
        useHandle = true,
    )
}

/**
 * [QuackBottomSheetContent] 를 구현합니다.
 *
 * @param useHandle BottomSheet 의 handle을 사용할지 여부
 * @param sheetContent sheetContent 에서 그려질 @Composable 함수
 */
@Composable
private fun QuackBottomSheetContent(
    useHandle: Boolean,
    sheetContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .applyQuackSize(
                width = QuackWidth.Fill,
                height = QuackHeight.Wrap,
            )
            .background(
                color = QuackColor.White.composeColor,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackBottomSheetHandle(
            useHandle = useHandle,
        )
        sheetContent()
    }

}

/**
 * [QuackBottomSheetHandle] 을 구현합니다.
 *
 * @param useHandle BottomSheet 의 handle을 사용할지 여부
 */
@Composable
private fun QuackBottomSheetHandle(
    useHandle: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                paddingValues = QuackBottomSheetContentPadding,
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
                        color = Gray3.composeColor,
                    )
                    .clip(
                        shape = QuackBottomSheetHandleShape,
                    )
            )
        }
    }
}

/**
 * [QuackBottomSheetSubtitleItem] 을 구현합니다.
 *
 * BottomSheet 의 목록형 UI를 나타내기 위해 필요한 함수입니다.
 *
 * @param item Subtitle 의 아이템 데이터 값
 * @param onClick Subtitle 을 onClick 했을 떄 이벤트
 */
@Composable
private fun QuackBottomSheetSubtitleItem(
    item: QuackBottomSheetItem,
    onClick: (QuackBottomSheetItem) -> Unit,
) {
    val textColor = when (item.isImportant) {
        true -> QuackColor.OrangeRed
        else -> QuackColor.Black
    }

    Box(
        modifier = Modifier
            .applyQuackSize(
                width = QuackWidth.Fill,
                height = QuackHeight.Custom(
                    height = QuackBottomSheetSubtitleItemHeight,
                ),
            )
            .quackClickable(
                onClick = {
                    onClick(item)
                },
            )
            .padding(
                paddingValues = QuackBottomSheetStartPadding,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        QuackSubtitle(
            text = item.title,
            color = textColor,
        )
    }
}

/**
 * [QuackBottomSheetSubtitles] 를 구현합니다.
 *
 * @param items Subtitle 의 아이템 데이터 리스트 값
 * @param onClick Subtitle 을 onClick 했을 떄 이벤트
 */
@Composable
private fun QuackBottomSheetSubtitles(
    items: PersistentList<QuackBottomSheetItem>,
    onClick: (QuackBottomSheetItem) -> Unit,
) {
    items.forEach { item ->
        QuackBottomSheetSubtitleItem(
            item = item,
            onClick = onClick,
        )
    }
}

/**
 * [QuackBottomSheetHeadline] 을 구현합니다.
 *
 * [QuackHeadlineBottomSheet] 에서 패딩을 넣는 용도로 구현했습니다.
 *
 * @param headline 헤드라인 텍스트 값
 */
@Composable
private fun QuackBottomSheetHeadline(
    headline: String,
) {
    Box(
        modifier = Modifier.padding(
            paddingValues = QuackBottomSheetStartPadding,
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
 * 공통적으로 사용하는 Padding 를 재활용 가능합니다.
 * 또한 [ModalBottomSheetLayout] 의 sheetContent 를 한 번 더 감싸서 사용해야
 * RoundedCornerShape 의 모양을 완성할 수 있습니다.
 *
 * @param content sheetContent 에서 그려질 @Composable 함수
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

public data class QuackBottomSheetItem(
    val title: String,
    val isImportant: Boolean,
)
