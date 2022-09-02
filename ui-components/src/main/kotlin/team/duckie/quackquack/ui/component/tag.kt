/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [tag.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:42
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

// TODO: 전체 리펙토링 필요

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackTagHeight = 34.dp
private val QuackTagBorderWidth = 1.dp

private val QuackIconTagSpace = 8.dp

@Stable
internal val SimpleTagPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)

@Stable
internal val IconTagPadding = PaddingValues(
    start = 16.dp,
    end = 10.dp,
    top = 8.dp,
    bottom = 8.dp,
)

@Stable
internal val MainTabPadding = PaddingValues(
    horizontal = 18.dp,
)

@Stable
internal val SubTabPadding = PaddingValues(
    horizontal = 16.dp,
)

internal val QuackTagShape = RoundedCornerShape(18.dp)

internal val QuackRectangleTagShape = RoundedCornerShape(12.dp)

@Composable
fun QuackBoldTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .tagShape(
                isSelected = isSelected,
                shape = QuackTagShape,
            )
            .padding(
                paddingValues = SimpleTagPadding
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        QuackTitle2(
            text = text,
            color = getTagTextColor(isSelected),
        )
    }
}

@Composable
fun QuackSimpleTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .tagShape(
                isSelected = isSelected,
                shape = QuackRectangleTagShape,
            )
            .padding(
                paddingValues = SimpleTagPadding,
            )
            .quackClickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        QuackBody1(
            text = text,
            color = getTagTextColor(isSelected),
        )
    }
}

@Composable
fun QuackIconTag(
    isSelected: Boolean,
    text: String,
    icon: QuackIcon,
    onClickTag: () -> Unit,
    onClickIcon: () -> Unit,
) {
    Box(
        modifier = Modifier
            .tagShape(
                isSelected = isSelected,
                shape = QuackTagShape,
            )
            .background(
                color = getTagBackground(
                    isSelected = isSelected,
                ).value,
            )
            .padding(
                paddingValues = IconTagPadding,
            )
            .quackClickable(
                onClick = onClickTag,
            ),
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
        ) {
            QuackTitle2(
                text = text,
                color = getIconTagTextColor(
                    isSelected = isSelected,
                ),
            )
            Spacer(
                modifier = Modifier.width(
                    width = QuackIconTagSpace,
                ),
            )
            QuackImage(
                icon = icon,
                tint = getIconColor(isSelected),
                onClick = onClickIcon,
            )
        }
    }
}

private fun Modifier.tagShape(
    isSelected: Boolean,
    shape: Shape,
) = this
    .height(
        height = QuackTagHeight,
    )
    .clip(
        shape = shape,
    )
    .background(
        color = QuackColor.White.value,
    )
    .border(
        width = QuackTagBorderWidth,
        color = getTagBorderColor(isSelected).value,
        shape = shape,
    )

private fun getTagBorderColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.Gray3
}

private fun getTagTextColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.Black
}

private fun getTagBackground(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.White
}

private fun getIconTagTextColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.White
    else -> QuackColor.Black
}

private fun getIconColor(isSelected: Boolean) = when (isSelected) {
    true -> QuackColor.White
    else -> QuackColor.Gray2
}
