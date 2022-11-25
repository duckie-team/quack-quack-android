/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.runIf
import team.duckie.quackquack.ui.util.runtimeCheck

/**
 * QuackLazyTag 에 사용할 태그들의 타입과 추가 정보들을 정의합니다.
 */
public sealed class QuackTagType {
    /**
     * [QuackGrayscaleTag] 에 해당합니다.
     *
     * @param trailingText 태그의 trailing content 로 들어갈 텍스트
     */
    public data class Grayscale(
        val trailingText: String,
    ) : QuackTagType()

    /**
     * [QuackCircleTag] 에 해당합니다.
     *
     * @param trailingIcon 태그의 trailing content 로 들어갈 [QuackIcon].
     * null 이 들어오면 trailing content 를 그리지 않습니다.
     */
    public data class Circle(
        val trailingIcon: QuackIcon? = null,
    ) : QuackTagType()

    /**
     * [QuackRoundTag] 에 해당합니다.
     */
    public object Round : QuackTagType()
}

private object QuackTagDefaults {
    object GrayscaleTag {
        val TagTextPadding = PaddingValues(
            top = 8.dp,
            bottom = 8.dp,
            start = 12.dp,
            end = 8.dp, // 텍스트 오른쪽에 trailing text 가 있음
        )

        // trailing text 는 TagTextPadding 를 기준으로 Vertical Center 에 배치돼야 함
        val TrailingTextPadding = PaddingValues(
            end = 15.dp,
        )

        val TagTypography = QuackTextStyle.Body1
        val TrailingTextTypography = QuackTextStyle.Subtitle2.change(
            color = QuackColor.DuckieOrange,
        )

        val Shape = RoundedCornerShape(
            size = 12.dp,
        )
        val BackgroundColor = QuackColor.Gray4
    }

    object CircleTag {
        /**
         * 조건에 맞게 태그 텍스트의 패딩 값을 계산합니다.
         *
         * @param hasTrailingIcon trailing icon 을 가지고 있는지 여부
         *
         * @return [hasTrailingIcon] 여부에 따른 패딩 값
         */
        @Stable
        fun tagTextPaddingFor(
            hasTrailingIcon: Boolean,
        ) = PaddingValues(
            top = 8.dp,
            bottom = 8.dp,
            start = 12.dp,
            end = if (hasTrailingIcon) 8.dp else 12.dp,
        )

        // trailing icon 은 TagTextPadding 를 기준으로 Vertical Center 에 배치돼야 함
        val TrailingIconPadding = PaddingValues(
            end = 10.dp,
        )

        /**
         * 조건에 맞게 태그 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         * @param hasTrailingIcon trailing icon 을 가지고 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isSelected: Boolean,
            hasTrailingIcon: Boolean,
        ) = QuackTextStyle.Title2.runIf(
            condition = isSelected,
        ) {
            change(
                color = when (hasTrailingIcon) {
                    true -> QuackColor.White
                    else -> QuackColor.DuckieOrange
                },
            )
        }

        /**
         * 조건에 맞게 태그의 테두리를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            isSelected: Boolean,
        ) = QuackBorder(
            color = when (isSelected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        )

        /**
         * 조건에 맞게 태그의 배경 색상을 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         * @param hasTrailingIcon trailing icon 을 가지고 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackColor]
         */
        @Stable
        fun backgroundColorFor(
            isSelected: Boolean,
            hasTrailingIcon: Boolean,
        ) = when (isSelected && hasTrailingIcon) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.White
        }

        /**
         * 조건에 맞게 trailing icon 의 틴트를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackColor]
         */
        @Stable
        fun trailingIconTintFor(
            isSelected: Boolean,
        ) = when (isSelected) {
            true -> QuackColor.White
            else -> QuackColor.Gray2
        }

        val TrailingIconSize = DpSize(
            all = 16.dp,
        )

        val Shape = RoundedCornerShape(
            size = 18.dp,
        )
    }

    object RoundingTag {
        val TextPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 8.dp,
        )

        /**
         * 조건에 맞게 태그 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isSelected: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = isSelected,
        ) {
            change(
                color = QuackColor.DuckieOrange,
            )
        }

        /**
         * 조건에 맞게 태그의 테두리를 계산합니다.
         *
         * @param isSelected 현재 선택된 상태로 있는지 여부
         *
         * @return 현재 조건에 맞게 사용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            isSelected: Boolean,
        ) = QuackBorder(
            color = when (isSelected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        )

        val BackgroundColor = QuackColor.White

        val Shape = RoundedCornerShape(
            size = 12.dp,
        )
    }

    object LazyTag {
        val VerticalSpacedBy = 8.dp
        val HorizontalSpacedBy = 8.dp

        /**
         * ```
         * [Title]
         * [LazyTag]
         * ```
         *
         * 에서 `title` 과 `LazyTag` 사이의 간격 입니다.
         */
        val TitleSpacedBy = 12.dp

        val TitleTypogrphy = QuackTextStyle.Title2
    }
}

/**
 * Grayscale 테마를 띄는 태그를 구현합니다.
 * [QuackGrayscaleTag] 는 다음과 같은 특징을 갖습니다.
 *
 * 1. 배경 색상이 항상 Grayscale 입니다.
 * 2. trailing text 를 갖습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그의 텍스트
 * @param trailingText 태그의 trailing content 로 들어갈 텍스트
 * @param onClick 태그를 클릭했을 때 실행될 람다
 */
@Composable
public fun QuackGrayscaleTag(
    modifier: Modifier = Modifier,
    text: String,
    trailingText: String,
    onClick: (() -> Unit)? = null,
): Unit = QuackGrayscaleTagInternal(
    modifier = modifier,
    text = text,
    trailingText = trailingText,
    onClick = onClick,
)

/**
 * [QuackGrayscaleTag] 을 실제로 구현합니다.
 * [QuackGrayscaleTagInternal] 는 [QuackLazyVerticalGridTag] 에서 onClick 사용을 위해
 * [onClickWithIndex] 인자를 추가로 받습니다. [onClick] 으로 null 이 제공됐다면
 * 클릭 이벤트로 [onClickWithIndex] 를 실행합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그의 텍스트
 * @param trailingText 태그의 trailing content 로 들어갈 텍스트
 * @param actualIndex 이 태그가 위치하는 실제 index 값.
 * [onClickWithIndex] 가 null 이 아닐 때만 유효합니다.
 * @param onClick 태그를 클릭했을 때 실행될 람다. index 값을 제공받지 않습니다.
 * @param onClickWithIndex 클릭 이벤트에 클릭된 index 를 함께 제공하는 람다.
 * index 값으론 [actualIndex] 를 참조합니다. [onClick] 이 null 이 아닐 때만 유효합니다.
 */
@Composable
private fun QuackGrayscaleTagInternal(
    modifier: Modifier = Modifier,
    text: String,
    trailingText: String,
    actualIndex: Int? = null,
    onClick: (() -> Unit)? = null,
    onClickWithIndex: ((index: Int) -> Unit)? = null,
): Unit = with(
    receiver = QuackTagDefaults.GrayscaleTag,
) {
    quackTagInternalAssert(
        actualIndex = actualIndex,
        onClick = onClick,
        onClickWithIndex = onClickWithIndex,
    )
    QuackSurface(
        modifier = modifier,
        backgroundColor = BackgroundColor,
        shape = Shape,
        onClick = onClick ?: onClickWithIndex?.let {
            {
                onClickWithIndex(
                    /* index = */
                    actualIndex!!, // assertion works above
                )
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = TagTextPadding,
                ),
                text = text,
                style = TagTypography,
                singleLine = true,
            )
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = TrailingTextPadding,
                ),
                text = trailingText,
                style = TrailingTextTypography,
                singleLine = true,
            )
        }
    }
}

/**
 * 덕키의 기본적인 태그를 구현합니다.
 * [QuackCircleTag] 는 다음과 같은 특징을 갖습니다.
 *
 * 1. trailing icon 을 가질 수 있습니다.
 * 2. trailing icon 여부와 현재 선택 상태에 따라 배경색과 테두리가 달라집니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그의 텍스트
 * @param trailingIcon 태그의 trailing content 로 들어갈 [QuackIcon].
 * null 이 들어오면 trailing content 를 그리지 않습니다.
 * @param isSelected 현재 선택된 상태로 있는지 여부
 * @param onClick 태그를 클릭했을 때 실행될 람다
 */
@Composable
public fun QuackCircleTag(
    modifier: Modifier = Modifier,
    text: String,
    trailingIcon: QuackIcon? = null,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
): Unit = QuackCircleTagInternal(
    modifier = modifier,
    text = text,
    trailingIcon = trailingIcon,
    isSelected = isSelected,
    onClick = onClick,
)

/**
 * [QuackCircleTag] 을 실제로 구현합니다.
 * [QuackCircleTagInternal] 는 [QuackLazyVerticalGridTag] 에서 onClick 사용을 위해
 * [onClickWithIndex] 인자를 추가로 받습니다. [onClick] 으로 null 이 제공됐다면
 * 클릭 이벤트로 [onClickWithIndex] 를 실행합니다.
 *
 * 1. trailing icon 을 가질 수 있습니다.
 * 2. trailing icon 여부와 현재 선택 상태에 따라 배경색과 테두리가 달라집니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그의 텍스트
 * @param trailingIcon 태그의 trailing content 로 들어갈 [QuackIcon]
 * @param isSelected 현재 선택된 상태로 있는지 여부
 * @param actualIndex 이 태그가 위치하는 실제 index 값.
 * [onClickWithIndex] 가 null 이 아닐 때만 유효합니다.
 * @param onClick 태그를 클릭했을 때 실행될 람다. index 값을 제공받지 않습니다.
 * @param onClickWithIndex 클릭 이벤트에 클릭된 index 를 함께 제공하는 람다.
 * index 값으론 [actualIndex] 를 참조합니다. [onClick] 이 null 이 아닐 때만 유효합니다.
 */
@Composable
private fun QuackCircleTagInternal(
    modifier: Modifier = Modifier,
    text: String,
    trailingIcon: QuackIcon? = null,
    isSelected: Boolean,
    actualIndex: Int? = null,
    onClick: (() -> Unit)? = null,
    onClickWithIndex: ((index: Int) -> Unit)? = null,
): Unit = with(
    receiver = QuackTagDefaults.CircleTag,
) {
    quackTagInternalAssert(
        actualIndex = actualIndex,
        onClick = onClick,
        onClickWithIndex = onClickWithIndex,
    )

    val hasTrailingIcon = trailingIcon != null

    QuackSurface(
        modifier = modifier,
        backgroundColor = backgroundColorFor(
            isSelected = isSelected,
            hasTrailingIcon = hasTrailingIcon,
        ),
        shape = Shape,
        border = borderFor(
            isSelected = isSelected,
        ),
        onClick = onClick ?: onClickWithIndex?.let {
            {
                onClickWithIndex(
                    /* index = */
                    actualIndex!!, // assertion works above
                )
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = tagTextPaddingFor(
                        hasTrailingIcon = hasTrailingIcon,
                    ),
                ),
                text = text,
                style = typographyFor(
                    isSelected = isSelected,
                    hasTrailingIcon = hasTrailingIcon,
                ),
                singleLine = true,
            )
            if (trailingIcon != null) {
                QuackImage(
                    // FIXME: why do not working??
                    // 아마 마진이 패딩으로 적용되는 듯 함
                    // modifier = Modifier.padding(
                    //     paddingValues = TrailingIconPadding,
                    // ),
                    src = trailingIcon,
                    size = TrailingIconSize,
                    tint = trailingIconTintFor(
                        isSelected = isSelected,
                    ),
                )
                Spacer(
                    modifier = Modifier.padding(
                        paddingValues = TrailingIconPadding,
                    ),
                )
            }
        }
    }
}

/**
 * 덕키의 기본적인 태그를 구현합니다.
 * [QuackRoundTag] 는 [QuackCircleTag] 와 달리 아래와 같은 특징이 있습니다.
 *
 * 1. [QuackCircleTag] 보다 적은 둥글기를 갖습니다.
 * 2. trailing icon 을 가질 수 없습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그의 텍스트
 * @param isSelected 현재 선택된 상태로 있는지 여부
 * @param onClick 태그를 클릭했을 때 실행될 람다
 */
@Composable
public fun QuackRoundTag(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
): Unit = QuackRoundTagInternal(
    modifier = modifier,
    text = text,
    isSelected = isSelected,
    onClick = onClick,
)

/**
 * [QuackRoundTag] 을 실제로 구현합니다.
 * [QuackRoundTagInternal] 는 [QuackLazyVerticalGridTag] 에서 onClick 사용을 위해
 * [onClickWithIndex] 인자를 추가로 받습니다. [onClick] 으로 null 이 제공됐다면
 * 클릭 이벤트로 [onClickWithIndex] 를 실행합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 태그의 텍스트
 * @param isSelected 현재 선택된 상태로 있는지 여부
 * @param actualIndex 이 태그가 위치하는 실제 index 값.
 * [onClickWithIndex] 가 null 이 아닐 때만 유효합니다.
 * @param onClick 태그를 클릭했을 때 실행될 람다. index 값을 제공받지 않습니다.
 * @param onClickWithIndex 클릭 이벤트에 클릭된 index 를 함께 제공하는 람다.
 * index 값으론 [actualIndex] 를 참조합니다. [onClick] 이 null 이 아닐 때만 유효합니다.
 */
@Composable
private fun QuackRoundTagInternal(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    actualIndex: Int? = null,
    onClick: (() -> Unit)? = null,
    onClickWithIndex: ((index: Int) -> Unit)? = null,
): Unit = with(
    receiver = QuackTagDefaults.RoundingTag,
) {
    quackTagInternalAssert(
        actualIndex = actualIndex,
        onClick = onClick,
        onClickWithIndex = onClickWithIndex,
    )
    QuackSurface(
        modifier = modifier,
        backgroundColor = BackgroundColor,
        shape = Shape,
        border = borderFor(
            isSelected = isSelected,
        ),
        onClick = onClick ?: onClickWithIndex?.let {
            {
                onClickWithIndex(
                    /* index = */
                    actualIndex!!, // assertion works above
                )
            }
        },
    ) {
        QuackText(
            modifier = Modifier.padding(
                paddingValues = TextPadding,
            ),
            text = text,
            style = typographyFor(
                isSelected = isSelected,
            ),
            singleLine = true,
        )
    }
}

/**
 * QuackTagInternal 이 정상적으로 작동하기 위한 assertion 을 구현합니다.
 *
 * @param actualIndex 이 태그가 위치하는 실제 index 값.
 * [onClickWithIndex] 가 null 이 아닐 때만 유효합니다.
 * @param onClick 태그를 클릭했을 때 실행될 람다. index 값을 제공받지 않습니다.
 * @param onClickWithIndex 클릭 이벤트에 클릭된 index 를 함께 제공하는 람다.
 * index 값으론 [actualIndex] 를 참조합니다. [onClick] 이 null 이 아닐 때만 유효합니다.
 */
@Stable
private fun quackTagInternalAssert(
    actualIndex: Int? = null,
    onClick: (() -> Unit)? = null,
    onClickWithIndex: ((index: Int) -> Unit)? = null,
) {
    when {
        onClick != null -> {
            runtimeCheck(
                value = actualIndex == null && onClickWithIndex == null,
            ) {
                "onClick 에 유효한 값이 제공됐을 때는, actualIndex 와 onClickWithIndex 는 null 이어야 합니다."
            }
        }
        onClickWithIndex != null -> {
            runtimeCheck(
                value = actualIndex != null,
            ) {
                "onClickWithIndex 에 유효한 값이 제공됐을 때는, actualIndex 는 null 이 아니어야 합니다."
            }
        }
    }
}

/**
 * [LazyVerticalGrid] 형식으로 주어진 태그들을 배치합니다.
 * 이 컴포넌트는 항상 상위 컴포저블의 가로 길이만큼 width 가 지정되고,
 * (즉, 좌우 패딩이 허용되지 않습니다) 한 줄에 최대 [itemChunkedSize]개가 들어갈 수 있습니다.
 * 또한 가로와 세로 스크롤을 모두 지원합니다.
 *
 * 퍼포먼스 측면에서 [LazyLayout] 를 사용하는 것이 좋지만, 덕키의 경우
 * 표시해야 하는 태그의 개수가 많지 않기 때문에 컴포저블을 직접 그려도
 * 성능에 중대한 영향을 미치지 않을 것으로 판단하여 [LazyColumn] 과
 * [Row] + [Modifier.horizontalScroll] 를 사용하여 구현하였습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param title 상단에 표시될 제목. 만약 null 을 제공할 시 표시되지 않습니다.
 * @param items 표시할 태그들의 제목. **중복되는 태그 제목은 허용하지 않습니다.**
 * 이 항목은 바뀔 수 있으므로 [ImmutableList] 가 아닌 일반 [List] 로 받습니다.
 * @param itemSelections 태그들의 선택 여부.
 * 이 항목은 바뀔 수 있으므로 [ImmutableList] 가 아닌 [List] 로 받습니다.
 * @param itemChunkedSize 한 칸에 들어갈 최대 아이템의 개수
 * @param tagType [QuackLazyVerticalGridTag] 에서 표시할 태그의 타입을 지정합니다.
 * 여러 종류의 태그가 [QuackLazyVerticalGridTag] 으로 표시될 수 있게 태그의 타입을 따로 받습니다.
 * @param key a factory of stable and unique keys representing the item. Using the same key
 * for multiple items in the list is not allowed. Type of the key should be saveable
 * via Bundle on Android. If null is passed the position in the list will represent the key.
 * When you specify the key the scroll position will be maintained based on the key, which
 * means if you add/remove items before the current visible item the item with the given key
 * will be kept as the first visible one.
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다.
 * 람다식의 인자로는 선택된 태그의 index 가 들어옵니다.
 */
@Composable
public fun QuackLazyVerticalGridTag(
    modifier: Modifier = Modifier,
    title: String? = null,
    items: List<String>,
    itemSelections: List<Boolean>? = null,
    itemChunkedSize: Int,
    tagType: QuackTagType,
    key: ((
        index: Int,
        items: List<String>,
    ) -> Any)? = null,
    onClick: (
        index: Int,
    ) -> Unit,
): Unit = with(
    receiver = QuackTagDefaults.LazyTag,
) {
    if (itemSelections != null) {
        runtimeCheck(
            value = items.size == itemSelections.size,
        ) {
            "The size of items and the size of itemsSelection must always be the same. " +
                    "[items.size (${items.size}) != itemsSelection.size (${itemSelections.size})]"
        }
    }
    val chunkedItems = remember(
        key1 = items,
    ) {
        items.chunked(
            size = itemChunkedSize,
        )
    }
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = VerticalSpacedBy,
        ),
    ) {
        if (title != null) {
            item {
                QuackText(
                    modifier = Modifier.padding(
                        bottom = TitleSpacedBy,
                    ),
                    text = title,
                    style = TitleTypogrphy,
                    singleLine = true,
                )
            }
        }
        itemsIndexed(
            items = chunkedItems,
            key = { index: Int, items: List<String> ->
                key!!.invoke(
                    /* index = */
                    index,
                    /* items = */
                    items,
                )
            }.takeIf {
                key != null
            },
        ) { rowIndex, rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        state = rememberScrollState(),
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    space = HorizontalSpacedBy,
                ),
            ) {
                rowItems.fastForEachIndexed { index, item ->
                    val currentIndex = rowIndex * itemChunkedSize + index
                    val isSelected = itemSelections?.get(
                        index = currentIndex,
                    ) ?: false
                    with(
                        receiver = tagType,
                    ) {
                        when (this) {
                            is QuackTagType.Grayscale -> QuackGrayscaleTagInternal(
                                modifier = Modifier.animateItemPlacement(
                                    animationSpec = QuackAnimationSpec(),
                                ),
                                text = item,
                                trailingText = trailingText,
                                actualIndex = currentIndex,
                                onClickWithIndex = onClick,
                            )
                            is QuackTagType.Circle -> QuackCircleTagInternal(
                                modifier = Modifier.animateItemPlacement(
                                    animationSpec = QuackAnimationSpec(),
                                ),
                                text = item,
                                trailingIcon = trailingIcon,
                                isSelected = isSelected,
                                actualIndex = currentIndex,
                                onClickWithIndex = onClick,
                            )
                            QuackTagType.Round -> QuackRoundTagInternal(
                                modifier = Modifier.animateItemPlacement(
                                    animationSpec = QuackAnimationSpec(),
                                ),
                                text = item,
                                isSelected = isSelected,
                                actualIndex = currentIndex,
                                onClickWithIndex = onClick,
                            )
                        }
                    }
                }
            }
        }
    }
}
