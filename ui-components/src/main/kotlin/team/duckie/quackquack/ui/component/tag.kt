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

/*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.TagPadding
import team.duckie.quackquack.ui.constant.TagShape.QuackRectangleTagShape
import team.duckie.quackquack.ui.constant.TagShape.QuackRoundTagShape
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.typography.QuackBody1
import team.duckie.quackquack.ui.typography.QuackTitle2

private val QuackTagBorderWidth = 1.dp
private val QuackTagHeight = 34.dp
private val QuackIconTagSpace = 8.dp

@Composable
fun QuackBoldTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .tagModifier(isSelected = isSelected, shape = QuackRoundTagShape)
            .padding(paddingValues = TagPadding.Simple)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        QuackTitle2(text = text, color = getTagTextColor(isSelected))
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
            .tagModifier(isSelected = isSelected, shape = QuackRectangleTagShape)
            .padding(SimpleTagPadding)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        QuackBody1(text = text, color = getTagTextColor(isSelected))
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
            .tagModifier(isSelected = isSelected, shape = QuackRoundTagShape)
            .background(color = getTagBackground(isSelected).value)
            .padding(IconTagPadding)
            .clickable {
                onClickTag()
            }
    ) {

        Row {
            QuackTitle2(text = text, color = getIconTagTextColor(isSelected))
            Spacer(modifier = Modifier.width(QuackIconTagSpace))
            QuackSimpleIcon(
                modifier = Modifier.clickable {
                    onClickIcon()
                },
                icon = icon,
                tint = getIconColor(isSelected),
                contentDescription = "tagIcon",
            )
        }
    }

}


private fun Modifier.tagModifier(isSelected: Boolean, shape: Shape) = composed {
    Modifier
        .height(QuackTagHeight)
        .clip(shape)
        .background(
            color = QuackColor.White.value
        )
        .border(
            width = QuackTagBorderWidth,
            color = getTagBorderColor(isSelected).value,
            shape = shape,
        )
}

private fun getTagBorderColor(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.PumpkinOrange else QuackColor.Gray3
}

private fun getTagTextColor(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.PumpkinOrange else QuackColor.Black
}

private fun getTagBackground(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.PumpkinOrange else QuackColor.White
}

private fun getIconTagTextColor(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.White else QuackColor.Black
}

private fun getIconColor(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.White else QuackColor.Gray2
}


@Preview
@Composable
private fun QuackBoldTagPreview() {
    val text = "더키"
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        QuackBoldTag(isSelected = false, text = text, onClick = {})
        QuackBoldTag(isSelected = true, text = text, onClick = {})
    }
}

@Preview
@Composable
private fun QuackSimpleTagPreview() {
    val text = "시간 약속을 잘 지켜요"
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        QuackSimpleTag(isSelected = false, text = text, onClick = {})
        QuackSimpleTag(isSelected = true, text = text, onClick = {})
    }
}


@Preview
@Composable
private fun QuackIconTagPreview() {
    val text = "더키"
    val icon = QuackIcon.Close
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        QuackIconTag(
            isSelected = false,
            text = text,
            icon = icon,
            onClickTag = {},
            onClickIcon = {})
        QuackIconTag(isSelected = true, text = text, icon = icon, onClickTag = {}, onClickIcon = {})
    }
}
*/
