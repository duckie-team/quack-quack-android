/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.border.QuackSquircle
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Black80
import team.duckie.quackquack.ui.color.QuackColor.Companion.Gray2
import team.duckie.quackquack.ui.color.QuackColor.Companion.Gray3
import team.duckie.quackquack.ui.color.QuackColor.Companion.Transparent
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackCardShape = RoundedCornerShape(12.dp)
private val QuackCardPadding = PaddingValues(8.dp)
private val QuackLargeIconCardSize = 80.dp
private val QuackSmallIconCardSize = 36.dp
private val QuackCardIconSize = DpSize(
    width = 24.dp,
    height = 24.dp,
)

/**
 * QuackCardImage 를 구현합니다.
 *
 * @param image 카드 이미지의 리소스
 * @param size 카드의 사이즈
 * @param cornerIcon 카드 오른쪽 아래 위치한 아이콘
 *
 */
@Composable
public fun QuackCardImage(
    image: Any,
    size: Dp? = null,
    cornerIcon: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    rippleEnabled: Boolean = true,
) {

    val sizeModifier = remember {
        when (size) {
            null -> {
                Modifier
                    .wrapContentWidth()
                    .aspectRatio(1f)
            }
            else -> {
                Modifier.size(
                    size = size
                )
            }
        }
    }
    Box {
        QuackImageInternal(
            modifier = sizeModifier
                .clip(
                    shape = QuackCardShape
                )
                .quackClickable(
                    onClick = onClick,
                    rippleEnabled = rippleEnabled,
                ),
            src = image,
        )
        QuackCornerIcon(
            cornerIcon = cornerIcon,
        )
    }
}

/**
 * [QuackCornerIcon] 을 구현합니다
 * 해당 아이콘은 우측 하단에 아이콘을 표시해야할 때 사용합니다.
 * @param cornerIcon 아이콘 리소스
 */

@Composable
private fun BoxScope.QuackCornerIcon(
    cornerIcon: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .align(
                alignment = Alignment.BottomEnd
            )
            .padding(
                paddingValues = QuackCardPadding,
            ),
    ) {
        cornerIcon?.invoke()
    }
}

/**
 * [QuackCardImageRow] 를 구현합니다.
 *
 * Card 로 연속된 목록형 이미지 UI 를 만들어줍니다.
 *
 * @param images 이미지 리소스들
 * @param cornerIcon 우측 하단에 들어갈 아이콘
 *
 */
@Composable
public fun QuackCardImageRow(
    images: PersistentList<Any>,
    cornerIcon: (@Composable () -> Unit)? = null,
) {
    when (images.size) {
        0 -> return
        1 -> {
            QuackWideCardImage(
                image = images[0],
                cornerIcon = cornerIcon,
            )
        }
        else -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                ),
            ) {
                itemsIndexed(images) { index, _ ->
                    QuackCardImage(
                        image = images[index],
                        size = 156.dp,
                        cornerIcon = cornerIcon,
                    )
                }
            }
        }
    }
}

/**
 * [QuackWideCardImage] 를 구현합니다.
 *
 * [QuackCardImageRow] 에서 Size가 1일 경우에 화면을 꽉 채워주는 컴포넌트 입니다.
 *
 * @param image 이미지 리소스
 * @param cornerIcon 우측 하단에 들어갈 아이콘
 */
@Composable
private fun QuackWideCardImage(
    image: Any,
    cornerIcon: (@Composable () -> Unit)?,
) {
    Box {
        QuackImageInternal(
            modifier = Modifier
                .applyQuackSize(
                    width = QuackWidth.Fill,
                    height = QuackHeight.Custom(
                        height = 186.dp,
                    ),
                )
                .clip(
                    shape = QuackCardShape,
                ),
            src = image,
        )
        QuackCornerIcon(
            cornerIcon = cornerIcon,
        )
    }
}

/**
 * [QuackSelectableCardImage] 를 구현합니다.
 *
 * Card 이미지에서 아이템을 선택해야할 경우에 사용합니다.
 *
 * @param checked 해당 아이템이 선택됐는지에 대한 여부
 * @param image 이미지 리소스
 * @param size 이미지 크기
 * @param onClick 아이템 클릭 이벤트
 */
@Composable
public fun QuackSelectableCardImage(
    checked: Boolean,
    image: Any,
    size: Dp? = null,
    onClick: () -> Unit,
) {

    val sizeModifier = remember {
        when (size) {
            null -> {
                Modifier
                    .wrapContentWidth()
                    .aspectRatio(1f)
            }
            else -> {
                Modifier.size(
                    size = size
                )
            }
        }
    }

    Box(
        modifier = sizeModifier
            .quackClickable(
                onClick = onClick,
                rippleEnabled = false,
            )
            .clip(
                shape = QuackCardShape
            ),
    ) {
        QuackCardImage(
            image = image,
        )
        CheckFilter(
            checked = checked,
        )
    }
}

/**
 * [QuackLargeIconRoundCard]
 *
 * [QuackIconRoundCard] 에서 사이즈가 큰 컴포넌트를 구현합니다.
 *
 * @param icon 카드 안에 사용될 아이콘
 * @param iconSize 카드 안에 들어갈 아이콘의 사이즈
 * @param onClick 카드 클릭시 발생하는 이벤트
 */
@Composable
public fun QuackLargeIconRoundCard(
    icon: QuackIcon,
    onClick: () -> Unit,
    iconSize: DpSize = QuackCardIconSize,
) {
    QuackIconRoundCard(
        size = QuackLargeIconCardSize,
        icon = icon,
        onClick = onClick,
        iconSize = iconSize,
    )
}

/**
 * [QuackSmallIconRoundCard]
 *
 * [QuackIconRoundCard] 에서 사이즈가 작은 컴포넌트를 구현합니다.
 *
 * @param icon 카드 안에 사용될 아이콘
 * @param iconSize 카드 안에 들어갈 아이콘의 사이즈
 * @param onClick 카드 클릭시 발생하는 이벤트
 */
@Composable
public fun QuackSmallIconRoundCard(
    icon: QuackIcon,
    onClick: () -> Unit,
    iconSize: DpSize = QuackCardIconSize,
) {
    QuackIconRoundCard(
        size = QuackSmallIconCardSize,
        icon = icon,
        onClick = onClick,
        iconSize = iconSize,
    )
}


/**
 * [QuackIconRoundCard] 를 구현합니다.
 *
 * 회색 바탕을 가지고, 아이콘을 필요로 하는 카드 컴포넌트의 UI를 만들 때 사용합니다.
 *
 * @param size 카드의 사이즈
 * @param icon 카드 안에 들어갈 icon 종류
 * @param iconSize 카드 안에 들어갈 icon 사이즈
 * @param onClick 카드 클릭시 발생하는 이벤트
 */
@Composable
private fun QuackIconRoundCard(
    size: Dp,
    icon: QuackIcon,
    iconSize: DpSize,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(
                shape = QuackSquircle(),
            )
            .size(
                size = size,
            )
            .background(
                color = Gray3.composeColor,
            )
            .quackClickable(
                rippleEnabled = true,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        QuackImageInternal(
            src = icon,
            size = iconSize,
            tint = Gray2,
        )
    }
}

/**
 * [CheckFilter] 를 구현합니다.
 *
 * 이 Composable 함수를 호출하는 화면에서 해당 아이템이 선택이 되었을 경우
 * Check 아이콘과 함께 deem 필터를 제공해줍니다.
 *
 * @param checked 해당 아이템이 선택됐는지 아닌지 여부
 */
@Composable
private fun CheckFilter(
    checked: Boolean,
) {
    val targetColor = when (checked) {
        true -> Black80
        else -> Transparent
    }
    val backgroundColor = animateQuackColorAsState(
        targetValue = targetColor,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = backgroundColor.value.composeColor
            ),
        contentAlignment = Alignment.Center,
    ) {

        Check(
            value = ToggleableState(
                value = checked,
            ),
            checkColor = QuackColor.White,
            size = 28.dp,
        )
    }
}
