/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.runtimeCheck

private val QuackTagRowTitleSpace = 12.dp
private val QuackTagRowFlowContentSpace = 8.dp

// icon tag 와 grayscale tag 에 사용됩니다.
private val QuackTagContentSpace = 8.dp
private val QuackTagBorderWidth = 1.dp
private val QuackTagHorizontalPadding = 8.dp

private val QuackTagPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)
private val QuackIconTagPadding = PaddingValues(
    start = 16.dp,
    end = 10.dp,
    top = 8.dp,
    bottom = 8.dp,
)

private val QuackTagShape = RoundedCornerShape(
    size = 18.dp,
)

// grayscale tag 와 flowrow tag 에 사용됩니다.
private val QuackGrayscaleFlowRowTagShape = RoundedCornerShape(
    size = 12.dp,
)

private val QuackGrayscaleTagBackgroundColor = QuackColor.Gray4
private val QuackTagBackgroundColor: (
    isSelected: Boolean,
) -> QuackColor
    get() = { isSelected ->
        when (isSelected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.White
        }
    }
private val QuackTagBorderColor: (
    isSelected: Boolean,
) -> QuackColor
    get() = { isSelected ->
        when (isSelected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Gray3
        }
    }
private val QuackTagTextColor: (
    isSelected: Boolean,
    isBorderTag: Boolean, // border 가 사용되는 태그인지 여부
) -> QuackColor
    get() = { isSelected, isBorderTag ->
        if (isBorderTag) {
            when (isSelected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Black
            }
        } else { // is no BorderTag
            when (isSelected) {
                true -> QuackColor.White
                else -> QuackColor.Black
            }
        }
    }
private val QuackTagIconTintColor: (
    isSelected: Boolean,
) -> QuackColor
    get() = { isSelected ->
        when (isSelected) {
            true -> QuackColor.White
            else -> QuackColor.Gray2
        }
    }

/**
 * QuackQuack 의 기본 태그 입니다.
 * 기본 태그는 테두리로 selected 상태가 구분됩니다.
 *
 * @param text 태그에 표시할 텍스트
 * @param isSelected 태그가 선택됐는지 여부
 * @param onClick 태그가 클릭됐을 때 호출되는 람다
 */
@Composable
public fun QuackTag(
    text: String,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
) {
    QuackText(
        modifier = Modifier
            .wrapContentSize()
            .quackTag(
                isSelected = isSelected,
                type = QuackTagType.Default,
                onClick = onClick,
            ),
        text = text,
        style = QuackTextStyle.Title2.change(
            color = QuackTagTextColor(
                /*isSelected = */
                isSelected,
                /*isBorderTag = */
                true,
            )
        ),
    )
}

/**
 * QuackQuack 의 grayscale 배경을 가진 태그 입니다.
 * grayscale 태그는 배경이 [QuackColor.Gray4] 로 고정이며,
 * selected 상태를 갖지 않습니다. 다른 태그들과 달리 특별히
 * 마지막에 배치될 텍스트를 받을 수 있습니다.
 *
 * @param text 태그에 표시할 텍스트
 * @param trailingText 컴포넌트 오른쪽에 추가로 표시할 텍스트
 * @param onClick 태그가 클릭됐을 때 호출되는 람다
 */
@Composable
public fun QuackGrayscaleTag(
    text: String,
    trailingText: String = "",
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .quackTag(
                isSelected = false,
                type = QuackTagType.Grayscale,
                rippleEnabled = false,
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackText(
            modifier = Modifier.wrapContentSize(),
            text = text,
            style = QuackTextStyle.Title2.change(
                weight = FontWeight.Normal,
            ),
        )
        if (trailingText.isNotEmpty()) {
            QuackText(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(
                        start = QuackTagContentSpace,
                    ),
                text = trailingText,
                style = QuackTextStyle.Body2.change(
                    weight = FontWeight.Bold,
                    color = QuackColor.DuckieOrange,
                ),
            )
        }
    }
}

/**
 * QuackQuack 의 아이콘을 함께 표시하는 태그 입니다.
 * 컴포넌트 오른쪽에 아이콘을 배치할 수 있습니다.
 *
 * @param text 태그에 표시할 텍스트
 * @param icon 태그 오른쪽에 표시할 아이콘
 * @param isSelected 태그가 선택됐는지 여부
 * @param onClick 태그의 텍스트가 클릭됐을 떄 호출되는 람다
 * @param onClickIcon 태그의 아이콘이 클릭됐을 때 호출되는 람다
 */
@Composable
public fun QuackIconTag(
    text: String,
    icon: QuackIcon,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
    onClickIcon: (() -> Unit)? = null,
): Unit = QuackIconTagInternal(
    text = text,
    icon = icon,
    isSelected = isSelected,
    onClick = onClick,
    onClickIcon = onClickIcon,
)

/**
 * QuackQuack 의 아이콘을 함께 표시하는 태그 입니다.
 * 컴포넌트 오른쪽에 아이콘을 배치할 수 있습니다.
 * 꽥꽥 내부에서 사용하도록 설계됐으며, 따라서 [Modifier] 인자를
 * 받습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그에 표시할 텍스트
 * @param icon 태그 오른쪽에 표시할 아이콘
 * @param isSelected 태그가 선택됐는지 여부
 * @param onClick 태그의 텍스트가 클릭됐을 떄 호출되는 람다
 * @param onClickIcon 태그의 아이콘이 클릭됐을 때 호출되는 람다
 */
@Composable
private fun QuackIconTagInternal(
    modifier: Modifier = Modifier,
    text: String,
    icon: QuackIcon,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
    onClickIcon: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .quackTag(
                isSelected = isSelected,
                type = QuackTagType.Icon,
                onClick = onClick,
            ),
    ) {
        QuackText(
            modifier = Modifier.wrapContentSize(),
            text = text,
            style = QuackTextStyle.Title2.change(
                color = QuackTagTextColor(
                    /*isSelected = */
                    isSelected,
                    /*isBorderTag = */
                    false,
                ),
            ),
        )
        QuackImageInternal(
            modifier = Modifier
                .padding(
                    start = QuackTagContentSpace,
                )
                .size(
                    size = 16.dp,
                )
                .quackClickable(
                    rippleEnabled = false,
                    onClick = onClickIcon,
                ),
            src = icon,
            tint = QuackTagIconTintColor(
                /*isSelected = */
                isSelected,
            ),
        )
    }
}

// Why `@Suppress("PreferredImmutableCollections")` is not working???
// This typealias is just to avoid the `PreferredImmutableCollections` issue.
// If the suppress issue is resolved, this typealias should be removed.
// Issue: https://github.com/duckie-team/duckie-quack-quack-mvp/issues/144
// TODO: private typealias ImmutableQuackTagItemButMutable = List<Boolean>
// Fix lint suppress issue.

/**
 * [QuackTag] 의 표시하는 방식을 정의합니다.
 * 이 컴포넌트는 항상 현재 화면의 가로 길이만큼 width 가 지정되고,
 * (즉, 좌우 패딩이 허용되지 않습니다) 한 줄에 최대 2개가 들어갈
 * 수 있습니다. 또한 가로와 세로 스크롤을 모두 지원합니다.
 *
 * 퍼포먼스 측면에서 [LazyLayout] 를 사용하는 것이 좋지만, 덕키의 경우
 * 표시해야 하는 태그의 개수가 많지 않기 때문에 컴포저블을 직접 그려도
 * 성능에 중대한 영향을 미치지 않을 것으로 판단하여 [LazyColumn] 과
 * [Row] + [Modifier.horizontalScroll] 를 사용하여 구현하였습니다.
 *
 * @param title QuackRowTag 상단에 표시될 제목 Text. 만약 null 을 제공할 시
 * 제목이 표시되지 않습니다.
 * @param items 표시할 태그들의 제목들. **중복되는 태그 제목은 허용하지 않습니다.**
 * @param itemsSelection 태그들의 선택 여부. 이 항목은 자주 바뀔 것으로
 * 예상되어 [ImmutableCollection] 이 아닌 일반 [Collection] 으로 받습니다.
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다
 */
@Composable
public fun QuackRowTag(
    title: String? = null,
    items: PersistentList<String>,
    itemsSelection: List<Boolean>,
    onClick: (
        index: Int,
    ) -> Unit,
) {
    runtimeCheck(
        value = items.size == itemsSelection.size,
    ) {
        "The size of items and the size of itemsSelection must always be the same. " +
                "[items.size (${items.size}) != itemsSelection.size (${itemsSelection.size})]"
    }
    val chunkedItems = remember(
        key1 = items,
    ) {
        items.chunked(
            size = 2,
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(
            space = QuackTagRowFlowContentSpace,
        ),
    ) {
        if (title != null) {
            item {
                QuackText(
                    modifier = Modifier.padding(
                        bottom = QuackTagRowTitleSpace,
                    ),
                    text = title,
                    style = QuackTextStyle.Title2,
                )
            }
        }
        itemsIndexed(
            items = chunkedItems,
            key = { _, item ->
                item
            },
        ) { rowIndex, rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        state = rememberScrollState(),
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    space = QuackTagRowFlowContentSpace,
                ),
            ) {
                rowItems.forEachIndexed { index, item ->
                    val currentIndex = rowIndex * 2 + index
                    QuackRowTagItem(
                        itemsSelection = itemsSelection,
                        item = item,
                        index = currentIndex,
                        onClick = onClick,
                    )
                }
            }
        }
    }
}

/**
 * [QuackRowTag] 에서 사용되는 태그를 그리기 위한 컴포넌트 입니다.
 *
 * @param itemsSelection 태그들의 선택 여부
 * @param item 표시할 태그의 제목
 * @param index 표시할 태그의 인덱스
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다
 */
@Composable
private fun QuackRowTagItem(
    itemsSelection: List<Boolean>,
    item: String,
    index: Int,
    onClick: (
        index: Int,
    ) -> Unit,
) {
    QuackText(
        modifier = Modifier
            .wrapContentSize()
            .quackTag(
                isSelected = itemsSelection[index],
                type = QuackTagType.Row,
                onClick = {
                    onClick(
                        /*index = */
                        index,
                    )
                },
            ),
        text = item,
        style = QuackTextStyle.Body2.change(
            color = QuackTagTextColor(
                /*isSelected = */
                itemsSelection[index],
                /*isBorderTag = */
                true,
            ),
        ),
    )
}

/**
 * [QuackTag] 의 표시하는 방식을 정의합니다.
 * [QuackRowTag] 와는 다르게 한 줄로 태그들을 표시하며 가로 스크롤을
 * 지원합니다. 또한 이 컴포넌트는 항상 현재 화면의 가로 길이만큼 width 가
 * 지정됩니다. 즉, 좌우 패딩이 허용되지 않습니다.
 *
 * @param title QuackRowTag 상단에 표시될 제목 Text. 만약 null 을 제공할 시
 * 제목이 표시되지 않습니다.
 * @param items 표시할 태그들의 제목들. Scrollable Tag 의 경우 이 값은
 * 자주 바뀔 것으로 예상되어 [ImmutableCollection] 이 아닌 일반 [Collection]
 * 으로 받습니다. 아이템에 변동이 있는 경우 자동으로 [QuackAnimationSpec]
 * 애니메이션을 적용합니다. **중복되는 태그 제목은 허용하지 않습니다.**
 * @param itemsSelection 태그들의 선택 여부. 이 항목은 자주 바뀔 것으로
 * 예상되어 [ImmutableCollection] 이 아닌 일반 [Collection] 으로 받습니다.
 * @param iconResource 태그의 오른쪽에 아이콘을 표시할지 여부.
 * 아이콘을 표시하지 않는 경우는 null 을 받습니다. 이런 경우에는 [QuackTag] 를
 * 사용하며 태그를 표시하고, 만약 null 이 아닌 값이 제공된 경우 태그를 표시하기 위해
 * 해당 아이콘을 이용하여 [QuackIconTag] 를 사용합니다. 기본값은 null 을 받습니다.
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다.
 * [iconResource] 값이 제공됐을땐 아이콘을 클릭했을 때 호출됩니다.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun QuackSingleRowTag(
    title: String? = null,
    items: List<String>,
    itemsSelection: List<Boolean>,
    iconResource: QuackIcon? = null,
    onClick: ((
        index: Int,
    ) -> Unit)? = null,
) {
    runtimeCheck(
        value = items.size == itemsSelection.size,
    ) {
        "The size of items and the size of itemsSelection must always be the same. " +
                "[items.size (${items.size}) != itemsSelection.size (${itemsSelection.size})]"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        if (title != null) {
            QuackText(
                modifier = Modifier.padding(
                    bottom = QuackTagRowTitleSpace,
                ),
                text = title,
                style = QuackTextStyle.Title2,
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = QuackTagHorizontalPadding,
            ),
        ) {
            itemsIndexed(
                items = items,
                key = { _, item ->
                    item
                },
            ) { index, item ->
                // The feature "unit conversion" is disabled
                // 타입 명시 필수
                val onClickValue: () -> Unit = {
                    onClick?.invoke(
                        /*index = */
                        index,
                    )
                }
                if (iconResource != null) {
                    QuackIconTagInternal(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = QuackAnimationSpec(),
                        ),
                        text = item,
                        icon = iconResource,
                        isSelected = false,
                        onClickIcon = onClickValue,
                    )
                } else {
                    QuackTag(
                        isSelected = itemsSelection[index], // assertion 은 함수 초반부에서 진행됨
                        onClick = onClickValue,
                        text = item,
                    )
                }
            }
        }
    }
}

/**
 * QuackTag 의 타입을 정의합니다.
 *
 * [Modifier.quackTag] 에 쓰이며 주어진 타입에 따라
 * 태그의 모양이 결정됩니다.
 *
 * @see Modifier.quackTag
 */
private enum class QuackTagType {
    /**
     * [QuackGrayscaleTag] 에 해당합니다.
     */
    Grayscale,

    /**
     * [QuackTag] 에 해당합니다.
     */
    Default,

    /**
     * [QuackIconTag] 에 해당합니다.
     */
    Icon,

    /**
     * [QuackRowTag] 에 해당합니다.
     */
    Row;
}

/**
 * 주어진 옵션에 따라 QuackTag 의 기본 모양을 조정합니다.
 * padding, clip, background, border 가 조정됩니다.
 *
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param isSelected 태그가 선택되었는지 여부.
 * 표시하려는 태그가 [QuackGrayscaleTag] 일 경우 무시됩니다.
 * @param type 태그의 타입
 * @param rippleEnabled 태그를 클릭했을 때 ripple 효과를 사용할지 여부
 * @param onClick 태그를 클릭했을 때 호출되는 람다
 *
 * @return 주어진 옵션에 따른 QuackTag 의 기본 모양이 적용된 [Modifier]
 */
private fun Modifier.quackTag(
    isSelected: Boolean,
    type: QuackTagType,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) = composed {
    val shape = remember(
        key1 = type,
    ) {
        when (type) {
            QuackTagType.Default, QuackTagType.Icon -> QuackTagShape
            QuackTagType.Grayscale, QuackTagType.Row -> QuackGrayscaleFlowRowTagShape
        }
    }

    this
        .clip(
            shape = shape,
        )
        .background(
            color = animateColorAsState(
                targetValue = when (type) {
                    QuackTagType.Grayscale -> QuackGrayscaleTagBackgroundColor
                    QuackTagType.Default, QuackTagType.Row -> QuackColor.White
                    QuackTagType.Icon -> QuackTagBackgroundColor(
                        /*isSelected = */
                        isSelected,
                    )
                }.composeColor,
                animationSpec = QuackAnimationSpec(),
            ).value,
        )
        .quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        )
        .border(
            width = QuackTagBorderWidth,
            color = animateColorAsState(
                targetValue = QuackTagBorderColor(
                    /*isSelected = */
                    isSelected,
                ).composeColor,
                animationSpec = QuackAnimationSpec(),
            ).value,
            shape = shape,
        )
        .padding(
            paddingValues = when (type) {
                QuackTagType.Default, QuackTagType.Row, QuackTagType.Grayscale -> QuackTagPadding
                QuackTagType.Icon -> QuackIconTagPadding
            },
        )
}
