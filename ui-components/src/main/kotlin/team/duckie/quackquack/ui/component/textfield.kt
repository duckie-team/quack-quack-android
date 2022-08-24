/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [textfield.kt] created by Ji Sungbin on 22. 8. 21. 오후 3:26
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.*
import team.duckie.quackquack.ui.typography.QuackTextStyle


@Stable
private val QuackTextFieldIconSpace = 8.dp


@Composable
fun QuackTrailingTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    buttonText: String,
    placeholder: String,
    onClickButton: () -> Unit
) {
    val style = if (text.isEmpty()) {
        QuackTextStyle.M1420
    } else {
        QuackTextStyle.M1420.changeColor(
            newColor = QuackColor.DuckieOrange
        )
    }
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = placeholder,
        trailing = {
            QuackText(
                modifier = Modifier
                    .clickable(text.isNotEmpty()) {
                        onClickButton()
                    },
                text = buttonText,
                style = style,
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
        icon = {
            QuackSimpleIconImage(
                icon = icon,
                contentDescription = "textFieldIcon"
            )
        }
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
    width: Dp = QuackTextFieldDefaults.width,
    height: Dp = QuackTextFieldDefaults.height,
    placeholder: String,
    trailing: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    margin: PaddingValues = QuackTextFieldDefaults.textFieldPadding(),
    singleLine: Boolean = true,
    colors: QuackTextFieldColors = QuackTextFieldDefaults.textFieldColors(),
    style: QuackTextStyle = QuackTextStyle.M1420
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Log.d("사이즈", size.toString())
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = style.toComposeStyle(),
        cursorBrush = SolidColor(colors.cursorColor().value),
        singleLine = singleLine,
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        icon?.run {
                            this()
                            Spacer(modifier = Modifier.width(QuackTextFieldIconSpace))
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
                        Spacer(modifier = Modifier.width(QuackTextFieldIconSpace))
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
    QuackIconTextField(
        text = text,
        onTextChanged = setText,
        icon = QuackIcon.Search,
        placeholder = placeholder,
    )
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
