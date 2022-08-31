/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [textfieldbar.kt] created by Ji Sungbin on 22. 9. 1. 오전 12:31
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackTextFieldDefaults

@Composable
fun QuackAddSendTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    leadingIcon: QuackIcon,
    trailingIcon: QuackIcon,
    onLeadingClick: () -> Unit,
    onTrailingClick: () -> Unit,
) {
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        leading = {
            QuackSimpleIconImage(
                modifier = Modifier.clickable {
                    onLeadingClick()
                },
                icon = leadingIcon,
                color = QuackColor.Gray2,
                contentDescription = "textFieldIcon",
            )
        },
        trailing = {
            QuackSimpleIconImage(
                modifier = Modifier.clickable {
                    onTrailingClick()
                },
                icon = trailingIcon,
                color = if (text.isEmpty()) {
                    QuackColor.Gray2
                } else {
                    QuackColor.DuckieOrange
                },
                contentDescription = "textFieldIcon",
            )
        },
        margin = PaddingValues(
            horizontal = QuackTextFieldDefaults.iconHorizontalPadding,
            vertical = QuackTextFieldDefaults.iconVerticalPadding
        )
    )
}
