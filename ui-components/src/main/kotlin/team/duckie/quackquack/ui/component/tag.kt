/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [tag.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:42
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackTagShape
import team.duckie.quackquack.ui.constant.SimpleTagPadding
import team.duckie.quackquack.ui.typography.QuackTitle2

private val QuackTagBorderWidth = 1.dp
private val QuackTagHeight = 34.dp

@Composable
fun QuackSimpleTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(QuackTagHeight)
            .clip(QuackTagShape)
            .tagBorder(isSelected)
            .padding(SimpleTagPadding)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        QuackTitle2(text = text, color = getTagTextColor(isSelected))
    }
}

@Composable
fun QuackBoldTag(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {

}

@Composable
fun QuackIconTag(
    isSelected: Boolean,
    text: String,
    icon: QuackIcon,
    onClickTag: () -> Unit,
    onClickIcon: () -> Unit,
) {

}


private fun Modifier.tagBorder(isSelected: Boolean) = composed {
    Modifier.border(
        width = QuackTagBorderWidth,
        color = getTagBorderColor(isSelected).value,
        shape = QuackTagShape,
    )
}

private fun getTagBorderColor(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.PumpkinOrange else QuackColor.Gray3
}

private fun getTagTextColor(isSelected: Boolean): QuackColor {
    return if (isSelected) QuackColor.PumpkinOrange else QuackColor.Black
}
