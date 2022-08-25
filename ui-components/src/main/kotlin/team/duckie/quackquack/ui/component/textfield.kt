/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [textfield.kt] created by Ji Sungbin on 22. 8. 21. 오후 3:26
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.*
import team.duckie.quackquack.ui.typography.QuackTextStyle


@Composable
fun QuackTrailingTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    buttonText: String,
    placeholder: String,
    onClickButton: () -> Unit
) {
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = placeholder,
        keyboardActions = KeyboardActions(
            onDone = {
                onClickButton()
            },
        ),
        trailing = {
            QuackText(
                modifier = Modifier
                    .clickable(text.isNotEmpty()) {
                        onClickButton()
                    },
                text = buttonText,
                style = if (text.isEmpty()) {
                    QuackTextStyle.M1420
                } else {
                    QuackTextStyle.M1420.changeColor(
                        newColor = QuackColor.DuckieOrange
                    )
                },
            )
        },
    )
}

@Composable
fun QuackIconTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    icon: QuackIcon,
    placeholder: String
) {
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = placeholder,
        iconSpacing = when (icon) {
            QuackIcon.Won -> QuackTextFieldDefaults.smallIconSpacing
            else -> QuackTextFieldDefaults.iconSpacing
        },
        icon = {
            QuackSimpleIconImage(
                icon = icon,
                color = if (text.isEmpty()) {
                    QuackColor.Greyish
                } else {
                    QuackColor.Black
                },
                contentDescription = "textFieldIcon",
            )
        },
    )
}

@Composable
fun QuackTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholder: String
) {
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = placeholder,
    )
}

@Composable
private fun QuackBasicTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholder: String,
    width: Dp = QuackTextFieldDefaults.width,
    height: Dp = QuackTextFieldDefaults.height,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    trailing: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    margin: PaddingValues = QuackTextFieldDefaults.textFieldPadding(),
    iconSpacing: Dp = QuackTextFieldDefaults.iconSpacing,
    singleLine: Boolean = true,
    colors: QuackTextFieldColors = QuackTextFieldDefaults.textFieldColors(),
    style: QuackTextStyle = QuackTextStyle.M1420
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = style.toComposeStyle(),
        cursorBrush = SolidColor(colors.cursorColor().value),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            QuackSurface(
                modifier = Modifier.size(
                    width = width,
                    height = height,
                ),
                contentAlignment = Alignment.CenterStart,
                backgroundColor = colors.backgroundColor().value,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(margin),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        icon?.run {
                            this()
                            Spacer(modifier = Modifier.width(iconSpacing))
                        }
                        Box {
                            if (text.isEmpty()) {
                                QuackText(
                                    text = placeholder,
                                    style = style.changeColor(
                                        newColor = QuackColor.Greyish,
                                    ),
                                )
                            }
                            innerTextField()
                        }
                    }
                    trailing?.run {
                        Spacer(modifier = Modifier.width(iconSpacing))
                        this()
                    }
                }
            }
        },
    )
}

@Composable
@Preview
fun QuackTextFieldPreview() {
    val (text, setText) = remember { mutableStateOf("") }
    val placeholder = "placeholder"
    QuackTextField(
        text = text,
        onTextChanged = setText,
        placeholder = placeholder,
    )
}

@Composable
@Preview
fun QuackIconTextFieldPreview() {
    val (text, setText) = remember { mutableStateOf("") }
    val placeholder = "placeholder"
    Column {
        QuackIconTextField(
            text = text,
            onTextChanged = setText,
            icon = QuackIcon.Search,
            placeholder = placeholder,
        )
        Spacer(modifier = Modifier.height(20.dp))
        QuackIconTextField(
            text = text,
            onTextChanged = setText,
            icon = QuackIcon.Won,
            placeholder = "가격(필수 입력)",
        )
    }

}

@Composable
@Preview
fun QuackTrailingTextFieldPreview() {
    val (text, setText) = remember { mutableStateOf("") }
    val placeholder = "placeholder"
    QuackTrailingTextField(
        text = text,
        onTextChanged = setText,
        buttonText = "등록",
        placeholder = placeholder,
        onClickButton = {},
    )
}
