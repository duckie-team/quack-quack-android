/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
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
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Black80
import team.duckie.quackquack.ui.color.QuackColor.Companion.Transparent
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackCardShape = RoundedCornerShape(12.dp)
private val QuackCardPadding = PaddingValues(8.dp)

/**
 * QuackCardImage 를 구현합니다.
 *
 * @param image 카드 이미지의 리소스
 * @param size 카드의 사이즈
 * @param cornerIcon 카드 오른쪽 아래 위치한 아이콘
 *
 */
@Composable
fun QuackCardImage(
    image: Any,
    size: Dp? = null,
    cornerIcon: (@Composable () -> Unit)? = null,
) {

    val sizeModifier = remember {
        when (size) {
            null -> Modifier
                .wrapContentWidth()
                .aspectRatio(1f)

            else -> Modifier.size(
                size = size
            )
        }
    }
    Box {
        InternalQuackImage(
            modifier = sizeModifier
                .clip(
                    shape = QuackCardShape
                ),
            image = image,
        )
        QuackCornerIcon(
            cornerIcon = cornerIcon,
        )
    }
}

/**
 * @param cornerIcon
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
 * QuackCaredImageRow 를 구현합니다.
 *
 * @param images
 * @param cornerIcon
 *
 */
@Composable
fun QuackCardImageRow(
    images: PersistentList<Any>,
    cornerIcon: (@Composable () -> Unit)? = null,
) {
    when (images.size) {
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
                itemsIndexed(images) { index, image ->
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

@Composable
private fun QuackWideCardImage(
    image: Any,
    cornerIcon: (@Composable () -> Unit)?,
) {
    Box {
        InternalQuackImage(
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
            image = image,
        )
        QuackCornerIcon(
            cornerIcon = cornerIcon,
        )
    }

}

@Composable
fun QuackSelectableCardImage(
    checked: Boolean,
    image: Any,
    size: Dp? = null,
    onClick: () -> Unit,
) {

    val sizeModifier = remember {
        when (size) {
            null -> Modifier
                .wrapContentWidth()
                .aspectRatio(1f)

            else -> Modifier.size(
                size = size
            )
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

        InternalQuackImage(
            image = image,
        )
        CheckFilter(
            checked = checked,
        )
    }
}

@Composable
fun CheckFilter(
    checked: Boolean
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
