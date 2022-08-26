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
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.*
import team.duckie.quackquack.ui.modifier.bottomIndicatorLine
import team.duckie.quackquack.ui.typography.QuackBody1
import team.duckie.quackquack.ui.typography.QuackSubtitle
import team.duckie.quackquack.ui.typography.QuackTextStyle

@Composable
fun QuackLimitedTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholder: String,
    isError: Boolean,
    errorText: String,
    maxLength: Int,
    icon: QuackIcon = QuackIcon.Delete,
    onClickButton: () -> Unit,
) {
    val currentLengthTextColor = if (text.isEmpty()) {
        QuackColor.Greyish
    } else {
        QuackColor.Black
    }
    QuackBasicTextField(
        text = text,
        onTextChanged = {
            if (it.length <= maxLength) {
                onTextChanged(it)
            }
        },
        placeholder = placeholder,
        isError = isError,
        errorText = errorText,
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuackBody1(
                    text = "${text.length}",
                    color = currentLengthTextColor,
                )
                Spacer(
                    modifier = Modifier.width(
                        width = QuackTextFieldDefaults.lengthTextSpacing,
                    ),
                )
                QuackBody1(
                    text = "/",
                    color = QuackColor.Greyish,
                )
                Spacer(
                    modifier = Modifier.width(
                        width = QuackTextFieldDefaults.lengthTextSpacing,
                    ),
                )
                QuackBody1(
                    text = "$maxLength",
                    color = QuackColor.Greyish,
                )
                if (text.isNotEmpty()) {
                    Spacer(
                        modifier = Modifier.width(
                            width = QuackTextFieldDefaults.smallIconSpacing,
                        )
                    )
                    QuackSimpleIconImage(
                        modifier = Modifier.clickable {
                            onClickButton()
                        },
                        icon = icon,
                        contentDescription = "textFieldIcon",
                    )
                }
            }
        },
        style = QuackTextStyle.Title1
    )
}

@Composable
fun QuackTrailingTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    buttonText: String,
    placeholder: String,
    onClickButton: () -> Unit,
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
            QuackSubtitle(
                modifier = Modifier
                    .clickable(text.isNotEmpty()) {
                        onClickButton()
                    },
                text = buttonText,
                color = if (text.isEmpty()) {
                    QuackColor.Greyish
                } else {
                    QuackColor.DuckieOrange
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
    placeholder: String,
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
    placeholder: String,
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
    height: Dp = QuackTextFieldDefaults.height,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    trailing: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String = "",
    margin: PaddingValues = QuackTextFieldDefaults.textFieldPadding(),
    iconSpacing: Dp = QuackTextFieldDefaults.iconSpacing,
    singleLine: Boolean = true,
    colors: QuackTextFieldColors = QuackTextFieldDefaults.textFieldColors(),
    shape: Shape = QuackTextFieldDefaults.TextFieldShape,
    style: QuackTextStyle = QuackTextStyle.Body1,
) {
    BasicTextField(
        modifier = Modifier.testTag(
            tag = "TextFieldTag"
        ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = style.toComposeStyle(
            textAlign = TextAlign.Left,
        ),
        cursorBrush = SolidColor(
            value = colors.cursorColor().value,
        ),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Column {
                QuackSurface(
                    modifier = Modifier
                        .fillMaxWidth() //applyQuackSize로 변경 해야함
                        .height(
                            height = height,
                        )
                        .clip(shape)
                        .bottomIndicatorLine(
                            QuackTextFieldDefaults.indicatorStroke(
                                isError = isError,
                            )
                        ),
                    contentAlignment = Alignment.CenterStart,
                    backgroundColor = colors.backgroundColor().value,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                paddingValues = margin,
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            modifier = Modifier.weight(
                                weight = 1f,
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            icon?.run {
                                this()
                                Spacer(
                                    modifier = Modifier.width(
                                        width = iconSpacing,
                                    ),
                                )
                            }
                            Box {
                                if (text.isEmpty()) {
                                    QuackBody1(
                                        text = placeholder,
                                        color = QuackColor.Greyish,
                                    )
                                }
                                innerTextField()
                            }
                        }
                        trailing?.run {
                            Spacer(
                                modifier = Modifier.width(
                                    iconSpacing,
                                )
                            )
                            this()
                        }
                    }
                }
                if (isError) {
                    Spacer(
                        modifier = Modifier.height(
                            QuackTextFieldDefaults.errorTextSpacing,
                        ),
                    )
                    QuackBody1(
                        text = errorText,
                        color = QuackColor.OrangeRed,
                    )
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

@Composable
@Preview
fun QuackLimitedTextFieldPreview() {
    val (text, setText) = remember { mutableStateOf("") }
    val placeholder = "placeholder"
    val errorText = "특수문자가 포함되어 있습니다"
    val maxLength = 30
    val isError = text.endsWith("E")
    QuackLimitedTextField(
        text = text,
        onTextChanged = setText,
        placeholder = placeholder,
        errorText = errorText,
        isError = isError,
        maxLength = maxLength,
        onClickButton = {}
    )
}
