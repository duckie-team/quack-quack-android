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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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

/**
 * 글자 수를 제한할 수 있는 TextField 입니다.
 *
 * [maxLength]를 지정하여, 최대로 입력할 수 있는 Length를 표시합니다.
 * 또한 현재까지 입력한 텍스트의 Length를 확인할 수 있습니다.
 *
 * error 여부를 [isError]에 제공할 수 있으며,
 * true인 경우 bottom Line이 표시되며 메시지가 출력됩니다.
 *
 * @param text TextField 에 표시할 input [String]
 * @param onTextChanged Text가 업데이트 될 때 마다 트리거 되는 콜백,
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param placeholder Text가 비어있을 때 보여지는 holder text
 * @param isError TextField 현재 값이 오류인지 여부
 * @param errorText [isError]가 true일 때 표시 될 Text
 * @param MaxLength TextField의 최대 입력 길이
 * @param icon TextField의 끝 부분에 표시할 아이콘
 * @param onClickIcon [icon]을 클릭했을 때 발생하는 이벤트
 */
@Composable
fun QuackLimitedTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholder: String,
    isError: Boolean,
    errorText: String,
    maxLength: Int,
    icon: QuackIcon = QuackIcon.Delete,
    onClickIcon: () -> Unit,
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
                        isFocused = text.isNotEmpty(),
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
                        onClick = onClickIcon,
                    )
                }
            }
        },
        style = QuackTextStyle.Title1,
    )
}

/**
 * Trailing Text를 제공할 수 있는 TextField 입니다.
 *
 * [buttonText]를 통해 TextField의 끝에 클릭 가능한 text를 제공합니다.
 *
 * @param text TextField 에 표시할 input [String]
 * @param onTextChanged Text가 업데이트 될 때 마다 트리거 되는 콜백,
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param buttonText TextField의 끝에 배치 될 text, 클릭 이벤트 적용 가능
 * @param placeholder Text가 비어있을 때 보여지는 holder text
 * @param onClickText [buttonText]를 클릭했을 때 발생하는 이벤트
 */
@Composable
fun QuackTrailingTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    buttonText: String,
    placeholder: String,
    onClickText: () -> Unit,
) {
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = placeholder,
        keyboardActions = KeyboardActions(
            onDone = {
                onClickText()
            },
        ),
        trailing = {
            QuackSubtitle(
                modifier = Modifier.clickable(
                    enabled = text.isNotEmpty(),
                ) {
                    onClickText()
                },
                text = buttonText,
                color = QuackTextFieldDefaults.textFieldColors().trailingIconColor(
                    isFocused = text.isNotEmpty(),
                ),
            )
        },
    )
}

/**
 * Leading Icon을 제공할 수 있는 TextField 입니다.
 *
 * [icon]을 통해 TextField의 앞 부분에 QuackIcon을 제공할 수 있습니다.
 *
 * @param text TextField 에 표시할 input [String]
 * @param onTextChanged Text가 업데이트 될 때 마다 트리거 되는 콜백,
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param icon TextField의 앞 부분에 표시되는 [QuackIcon]
 * @param placeholder Text가 비어있을 때 보여지는 holder text
 */
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
                    isFocused = text.isNotEmpty(),
                ),
            )
        },
    )
}

/**
 * 값을 입력할 수 있는 기본적인 TextField 입니다.
 *
 * @param text TextField 에 표시할 input [String]
 * @param onTextChanged Text가 업데이트 될 때 마다 트리거 되는 콜백,
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param placeholder Text가 비어있을 때 보여지는 holder text
 */
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

/**
 * QuackTextField의 기반이 되는 컴포넌트 입니다.
 *
 * 기본적으로 [text]만 주어지며
 * 상황에 따라 [trailing], [leading] 등을 제공할 수 있습니다.
 *
 * @param text TextField 에 표시할 input [String]
 * @param onTextChanged Text가 업데이트 될 때 마다 트리거 되는 콜백,
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param placeholder Text가 비어있을 때 보여지는 holder text
 * @param width TextField의 가로 길이
 * @param height TextField의 높이
 * @param keyboardOptions 소프트웨어 키보드 옵션 및 ImeAction
 * @param keyboardActions 입력 서비스가 IME 작업을 실행하면 실행되는 콜백,
 * [KeyboardOptions.imeAction] 에서 지정한 Action과 다를 수 있음
 * @param trailing TextField 컨테이너의 끝에 표시할 요소
 * @param leading TextField 컨테이너의 시작 부분에 표시할 요소
 * @param isError TextField 현재 값이 오류인지 여부,
 * true를 제공하면 기본적으로 하단 바 색깔과, 에러 메시지를 표시
 * @param errorText [isError]가 true일 때 보여지는 메시지
 * @param margin TextField 안쪽 여백
 * @param iconSpacing [trailing], [leading]과의 간격
 * @param singleLine true일 때, 단일 수평으로 스크롤 되는 텍스트 필드가 됨
 * @param colors TextField의 다양한 상태에 대한 색을 제공
 * (label 색상, placeholder 색상 등을 포함)
 * @param shape TextField 컨테이너의 모양
 * @param style 입력 텍스트에 적용할 스타일
 * @see QuackTextFieldDefaults
 */
@Composable
internal fun QuackBasicTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholder: String = "",
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
                leading = leading,
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

/**
 * TextField에 Custom Design을 제공하기 위한 DecorationBox 입니다.
 *
 *
 * @param text TextField 에 표시할 input [String]
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param placeholder Text가 비어있을 때 보여지는 holder text
 * @param width TextField의 가로 길이
 * @param height TextField의 높이
 * @param innerTextField 해당 [QuackCommonDecorationBox]가 감싸는 TextField,
 * decorationBox 람다에서 이를 전달
 * @param trailing TextField 컨테이너의 끝에 표시할 요소
 * @param leading TextField 컨테이너의 시작 부분에 표시할 요소
 * @param isError TextField 현재 값이 오류인지 여부,
 * true를 제공하면 기본적으로 하단 바 색깔과, 에러 메시지를 표시
 * @param errorText [isError]가 true일 때 보여지는 메시지
 * @param margin TextField 안쪽 여백
 * @param iconSpacing [trailing], [leading]과의 간격
 * @param colors TextField의 다양한 상태에 대한 색을 제공
 * (label 색상, placeholder 색상 등을 포함)
 * @param shape TextField 컨테이너의 모양
 * @see QuackTextFieldDefaults
 */
@Composable
private fun QuackCommonDecorationBox(
    text: String,
    width: QuackWidth,
    height: QuackHeight,
    innerTextField: @Composable () -> Unit,
    trailing: @Composable (() -> Unit)?,
    leading: @Composable (() -> Unit)?,
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
                    leading?.also { leadingContent ->
                        leadingContent()
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
                trailing?.also { trailingContent ->
                    Spacer(
                        modifier = Modifier.width(
                            iconSpacing,
                        )
                    )
                    trailingContent()
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
