/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [textfieldbar.kt] created by EvergreenTree97 on 22. 9. 1. 오후 3:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.constant.QuackTextFieldDefaults
import team.duckie.quackquack.ui.icon.QuackIcon

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
            QuackSimpleIcon(
                icon = leadingIcon,
                onClick = onLeadingClick,
            )
        },
        trailing = {
            QuackSimpleIcon(
                icon = trailingIcon,
                tint = QuackTextFieldDefaults.textFieldColors().trailingIconColor(
                    isFocused = text.isNotEmpty(),
                ),
                onClick = onTrailingClick,
            )
        },
        margin = PaddingValues(
            horizontal = QuackTextFieldDefaults.iconHorizontalPadding,
            vertical = QuackTextFieldDefaults.iconVerticalPadding,
        ),
    )
}
