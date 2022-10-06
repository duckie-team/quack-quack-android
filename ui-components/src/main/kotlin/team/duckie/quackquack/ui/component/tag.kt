/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.animation.quackAnimationSpec
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

private val QuackTagPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)
private val QuackIconTagPadding = PaddingValues(
    horizontal = 16.dp,
    vertical = 8.dp,
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
) {
    Row(
        modifier = Modifier
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
private typealias ImmutableQuackTagItemButMutable = List<Boolean>

/**
 * 덕키의 TagRow 를 구현합니다. 내부적으로 [FlowRow] 를 이용하며, 항상 현재 화면의
 * 가로 길이만큼 width 가 지정됩니다.
 *
 * @param title QuackRowTag 상단에 표시될 제목 Text. 만약 공백을 제공할 시
 * 제목이 표시되지 않습니다.
 * @param items 표시할 태그들의 제목들
 * @param itemsSelection 태그들의 선택 여부. 이 항목은 자주 바뀔 것으로
 * 예상되어 [ImmutableCollection] 가 아닌 일반 [Collection] 으로 받습니다.
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다
 *
 * @see ImmutableQuackTagItemButMutable
 */
@Composable
public fun QuackRowTag(
    title: String = "",
    items: PersistentList<String>,
    itemsSelection: ImmutableQuackTagItemButMutable,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        if (title.isNotEmpty()) {
            QuackText(
                modifier = Modifier.padding(
                    bottom = QuackTagRowTitleSpace,
                ),
                text = title,
                style = QuackTextStyle.Title2,
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = QuackTagRowFlowContentSpace,
            crossAxisSpacing = QuackTagRowFlowContentSpace,
        ) {
            items.forEachIndexed { index, itemText ->
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
                    text = itemText,
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
        }
    }
}

/**
 * QuackTag 의 타입을 정의합니다.
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
                animationSpec = quackAnimationSpec(),
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
                animationSpec = quackAnimationSpec(),
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
