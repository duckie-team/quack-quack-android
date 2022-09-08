/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [selectableImage.kt] created by doro on 22. 9. 3. 오후 11:45
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon

private val SelectableImageSize = 118.dp
private val ImagePadding = PaddingValues(top = 4.dp, end = 4.dp)

private val Black80 = Color(0x80222222)

/**
 * TODO Border 챙기기 , Icon 챙기기, 왜 영역보다 더 Clickable이 많이 되는거 같지?
 */
@Composable
fun QuackSelectableImage(
    isSelected: Boolean,
    image: String,
    onClick: () -> Unit
) {
    val selectableIcon = when (isSelected) {
        true -> QuackIcon.Checked
        else -> QuackIcon.UnChecked
    }

    Box(
        modifier = Modifier
            .size(SelectableImageSize)
            .quackBorderOrNull(
                color = QuackColor.DuckieOrange,
                isSelected = isSelected
            )
    ) {
        Image(
            painterResource(team.duckie.quackquack.ui.R.drawable.sample_image),
            contentDescription = null
        )
        SelectedFilterBox(isSelected)
        QuackSelectedIcon(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onClick,
            selectableIcon = selectableIcon
        )
        //QuackImage(image = image)

    }
}

@Composable
private fun QuackSelectedIcon(modifier: Modifier, onClick: () -> Unit, selectableIcon: QuackIcon) {
    Box(
        modifier = modifier
            .padding(ImagePadding),
    ) {
        QuackImage(
            icon = selectableIcon,
            onClick = onClick
        )
    }
}

@Composable
private fun SelectedFilterBox(isSelected: Boolean) {
    if (!isSelected) return
    Box(
        modifier = Modifier
            .background(Black80)
            .fillMaxSize()
    )
}

private val DeletableImageSmallSize = 46.dp
private val DeletableImageLargeSize = 72.dp
private val QuackDeletableImageShape = RoundedCornerShape(12.dp)
private val Gray5 = Color(0xFFD1D1D1)

/**
 *
 */

@Composable
fun QuackLargeDeletableImage(
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.size(DeletableImageLargeSize)) {
        QuackDeleteImage(modifier = Modifier.matchParentSize())
        QuackDeleteIcon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 6.dp),
            onClick = onClick
        )
    }
}

@Composable
fun QuackSmallDeletableImage(
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.size(DeletableImageSmallSize)) {
        QuackDeleteImage(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 2.dp, end = 2.dp)
        )
        QuackDeleteIcon(
            modifier = Modifier
                .align(Alignment.TopEnd),
            onClick = onClick
        )
    }
}

@Composable
private fun QuackDeleteImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(QuackDeletableImageShape)
            .background(Gray5)
    )

}

@Composable
private fun QuackDeleteIcon(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
    ) {
        QuackImage(icon = QuackIcon.Delete, onClick = onClick)
    }
}


@Preview
@Composable
private fun QuackSelectableImagePreview() {
    val isSelected = remember { mutableStateOf(false) }
    val previewUrl = "https://picsum.photos/id/237/200/300"
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuackSelectableImage(isSelected = isSelected.value, image = previewUrl, onClick = {
            isSelected.value = !isSelected.value
        })
    }
}

@Preview
@Composable
private fun QuackDeletableImagePreview(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        QuackLargeDeletableImage {}
        QuackSmallDeletableImage {}
    }
}

@Stable
internal fun Modifier.quackBorderOrNull(
    isSelected: Boolean,
    width: Dp = 1.dp,
    color: QuackColor = QuackColor.DuckieOrange
) = when (isSelected) {
    true -> composed {
        border(
            border = QuackBorder(
                width = width,
                color = color
            ).asComposeBorder()
        )
    }
    else -> this
}
