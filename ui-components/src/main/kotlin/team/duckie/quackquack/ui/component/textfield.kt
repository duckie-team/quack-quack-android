/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [textfield.kt] created by EvergreenTree97 on 22. 8. 21. 오후 3:26
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackTextFieldColors
import team.duckie.quackquack.ui.constant.QuackTextFieldDefaults
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.bottomIndicatorLine
import team.duckie.quackquack.ui.typography.QuackBody1
import team.duckie.quackquack.ui.typography.QuackSubtitle
import team.duckie.quackquack.ui.typography.QuackTextStyle

const val TextFieldTestTag = "TextFieldTag"

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
                    text = text.length.toString(),
                    color = QuackTextFieldDefaults.textFieldColors().textColor(
                        isFocused = text.isNotEmpty()
                    )
                )
                Spacer(
                    modifier = Modifier.width(
                        width = QuackTextFieldDefaults.lengthTextSpacing,
                    ),
                )
                QuackBody1(
                    text = stringResource(id = R.string.slash),
                    color = QuackColor.Gray2,
                )
                Spacer(
                    modifier = Modifier.width(
                        width = QuackTextFieldDefaults.lengthTextSpacing,
                    ),
                )
                QuackBody1(
                    text = maxLength.toString(),
                    color = QuackColor.Gray2,
                )
                if (text.isNotEmpty()) {
                    Spacer(
                        modifier = Modifier.width(
                            width = QuackTextFieldDefaults.smallIconSpacing,
                        )
                    )
                    QuackSimpleIcon(
                        icon = icon,
                        onClick = onClickButton,
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
                modifier = Modifier.clickable(text.isNotEmpty()) {
                    onClickButton()
                },
                text = buttonText,
                color = QuackTextFieldDefaults.textFieldColors().trailingIconColor(
                    isFocused = text.isNotEmpty()
                ),
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
        leading = {
            QuackSimpleIcon(
                icon = icon,
                tint = QuackTextFieldDefaults.textFieldColors().textColor(
                    isFocused = text.isNotEmpty()
                ),
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
internal fun QuackBasicTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholder: String,
    width: QuackWidth = QuackWidth.Fill,
    height: QuackHeight = QuackHeight.Custom(QuackTextFieldDefaults.height),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    trailing: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
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
            tag = TextFieldTestTag
        ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = style.changeTextAlign(TextAlign.Left).asComposeStyle(),
        cursorBrush = SolidColor(
            value = colors.cursorColor().value,
        ),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            QuackCommonDecorationBox(
                text = text,
                placeholder = placeholder,
                width = width,
                height = height,
                trailing = trailing,
                icon = leading,
                isError = isError,
                margin = margin,
                errorText = errorText,
                iconSpacing = iconSpacing,
                innerTextField = innerTextField,
                shape = shape,
                colors = colors,
            )
        },
    )
}

@Composable
private fun QuackCommonDecorationBox(
    text: String,
    width: QuackWidth,
    height: QuackHeight,
    innerTextField: @Composable () -> Unit,
    trailing: @Composable (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    isError: Boolean,
    errorText: String,
    placeholder: String,
    margin: PaddingValues,
    iconSpacing: Dp,
    colors: QuackTextFieldColors,
    shape: Shape,
) {
    Column {
        QuackSurface(
            modifier = Modifier
                .applyQuackSize(
                    width = width,
                    height = height,
                )
                .clip(
                    shape = shape,
                )
                .bottomIndicatorLine(
                    QuackTextFieldDefaults.indicatorStroke(
                        isError = isError,
                    )
                ),
            contentAlignment = Alignment.CenterStart,
            backgroundColor = colors.backgroundColor(),
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
                    verticalAlignment = Alignment.CenterVertically,
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
                                color = colors.placeholderColor(),
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
                color = colors.errorTextColor(),
            )
        }
    }
}
