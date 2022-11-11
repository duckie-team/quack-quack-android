/*
 * Designed and developed by Duckie Team, 2022
 *
 * [dropDown.kt] created by doro on 22. 9. 4. 오전 1:21
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
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

/**
 * QuackDropDown 을 구현합니다.
 * @param title DropDown 의 타이틀
 * @param onClick DropDown 클릭시 발생하는 이벤트
 */
@Composable
public fun QuackDropDown(
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(
                shape = QuackDropDownShape,
            )
            .border(
                border = QuackBorder(
                    color = Gray3,
                ).asComposeBorder(),
                shape = QuackDropDownShape,
            )
            .quackClickable {
                onClick()
            }
            .background(
                color = QuackColor.White.composeColor,
            )
            .padding(
                paddingValues = QuackDropDownPadding,
            ),
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackDropDownSpace
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackBody1(
            text = title,
        )
        QuackImage(
            src = QuackIcon.ArrowDown,
            size = DpSize(
                width = 24.dp,
                height = 24.dp,
            ),
        )
    }
}
