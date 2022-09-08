/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [dropDown.kt] created by doro on 22. 9. 4. 오전 1:21
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.Gray3
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

private val QuackDropDownPadding = PaddingValues(
    start = 12.dp,
    end = 8.dp,
    top = 8.dp,
    bottom = 8.dp,
)
private val QuackDropDownShape = RoundedCornerShape(8.dp)
private val QuackDropDownSpace = 4.dp
val Gray6 = Color(0xFFF8F8F8)

@Composable
fun QuackDropDown(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(QuackDropDownShape)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Gray3.value,
                ),
                shape = QuackDropDownShape
            )
            .quackClickable {
                onClick()
            }
            .background(QuackColor.White.value)
            .padding(QuackDropDownPadding),
        horizontalArrangement = Arrangement.spacedBy(QuackDropDownSpace)

    ) {
        QuackBody1(text = text)
        QuackImage(icon = QuackIcon.ArrowDown)
    }
}

@Preview
@Composable
fun QuackDropDownPreview(){
    Row(
        modifier = Modifier.background(Gray6).fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        QuackDropDown(text = "판매중", onClick = {})
    }
}
