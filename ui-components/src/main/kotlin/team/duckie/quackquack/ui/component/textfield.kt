/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:NoLiveLiterals

package team.duckie.quackquack.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackTextFieldLayoutId
import team.duckie.quackquack.ui.component.internal.QuackTextFieldLeadingContentLayoutId
import team.duckie.quackquack.ui.component.internal.QuackTextFieldMeasurePolicy.Companion.rememberQuackTextFieldMeasurePolicy
import team.duckie.quackquack.ui.component.internal.QuackTextFieldPlaceholderLayoutId
import team.duckie.quackquack.ui.component.internal.QuackTextFieldTrailingContentLayoutId
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.modifier.drawUnderBarWithAnimation
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.theme.LocalQuackTextFieldColors
import team.duckie.quackquack.ui.util.DoNotUseDirectly

/**
 * QuackTextField 에서 표시할 텍스트의 [FontWeight] 에 따라 QuackTextField 의
 * 높이가 달라집니다. 이를 계산하기 위해 사용됩니다.
 *
 * 만약 [FontWeight] 가 [FontWeight.Bold] 라면 TextField 에서 16dp 만큼 추가 높이를 갖고,
 * 그렇지 않다면 18dp 만큼 추가 높이를 갖습니다.
 *
 * 좀 더 많은 경우를 신경쓰기 위해선 [FontWeight.Bold] 보다 큰 경우도 체크하면 좋지만,
 * 현재 QuackTextField 의 디자인에는 최대 [FontWeight.Bold] 만 사용하고 있기 때문에
 * [FontWeight.Bold] 만 체크합니다.
 *
 * @receiver [FontWeight] 가 [FontWeight.Bold] 인지 검사할 [QuackTextStyle]
 * @return QuackTextField 에서 표시할 텍스트의 [FontWeight] 가 [FontWeight.Bold] 인지 여부
 */
private val QuackTextStyle.isBold get() = weight == FontWeight.Bold

/**
 * This padding adds spaces in between the text-field and decoration content i.e leading and trailing content
 * ```
 * Decoration - TextField - Decoration
 * ```
 *
 * As an example, see [this](https://user-images.githubusercontent.com/40740128/189829870-cba93fd6-d5f4-4016-b826-c6093cfbb386.png) image,
 * the decoration item is the part inside the orange rectangle,
 * and the TextField is the "placeholder" part.
 */
private val QuackTextFieldDecorationContentHorizontalPadding = 8.dp

/**
 * QuackTextField 밑에 표시될 ErrorText 와의 간격
 */
private val QuackTextFieldErrorTextTopPadding = 4.dp

/**
 * QuackTextField 에서 표시하는 텍스트의 [FontWeight] 가
 * [FontWeight.Bold] 가 아닐 때 TextField 위에 들어갈 패딩
 *
 * QuackTextField 의 높이를 결정짓는 중요한 요소가 됩니다.
 *
 * **이 API 는 직접적으로 사용하면 안됩니다.**
 * 대신에 [QuackTextStyle.calcQuackTextFieldTopPadding] 를 사용하세요.
 *
 * @see QuackTextStyle.isBold
 */
@DoNotUseDirectly
private val QuackTextFieldTopPaddingWithNonBold = 18.dp

/**
 * QuackTextField 에서 표시하는 텍스트의 [FontWeight] 가
 * [FontWeight.Bold] 일 때 TextField 위에 들어갈 패딩
 *
 * QuackTextField 의 높이를 결정짓는 중요한 요소가 됩니다.
 *
 * **이 API 는 직접적으로 사용하면 안됩니다.**
 * 대신에 [QuackTextStyle.calcQuackTextFieldTopPadding] 를 사용하세요.
 *
 * @see QuackTextStyle.isBold
 */
@DoNotUseDirectly
private val QuackTextFieldTopPaddingWithBold = 16.dp

/**
 * QuackTextField 에서 표시하는 텍스트의 [FontWeight] 에 따라 TextField 위에 들어갈 패딩을 계산합니다.
 *
 * @return TextField 위에 들어갈 패딩
 * @see QuackTextStyle.isBold
 */
@OptIn(DoNotUseDirectly::class)
private fun QuackTextStyle.calcQuackTextFieldTopPadding() = when (isBold) {
    true -> QuackTextFieldTopPaddingWithBold
    else -> QuackTextFieldTopPaddingWithNonBold
}

/**
 * QuackTextField 에 사용되는 TextField 의 하단에 들어갈 패딩
 */
private val QuackTextFieldBottomPadding = 8.dp

/**
 * QuackTextField 에 표시될 언더바의 높이
 */
private val QuackTextFieldUnderBarHeight = 1.dp

/**
 * QuackTextField 에서 사용할 아이템들의 색상을 계산하기 위한
 * 유틸 함수들이 있는 클래스 입니다.
 */
@Immutable
private object QuackTextFieldColors {
    /**
     * QuackTextField 에 표시될 텍스트의 색상을 계산합니다.
     * placeholder 인지 여부에 따라 색상이 달라집니다.
     *
     * @param isPlaceholder placeholder 로 보여지고 있는지 여부
     * @return QuackTextField 에 표시될 텍스트의 색상
     */
    @Stable
    fun textColor(
        isPlaceholder: Boolean,
    ) = when (isPlaceholder) {
        true -> QuackColor.Gray2
        else -> QuackColor.Black
    }

    /**
     * QuackTextField 에 표시될 언더바의 색상을 계산합니다.
     * 현재 에러 상태인지에 따라 색상이 달라집니다.
     *
     * @param isError 현재 QuackTextField 가 에러 상태인지 여부
     * @return QuackTextField 에 표시될 언더바의 색상
     */
    @Stable
    fun underBarColor(
        isError: Boolean,
    ) = when (isError) {
        true -> QuackColor.OrangeRed
        else -> QuackColor.Gray3
    }
}

/**
 * Draws the most basic QuackQuack's TextField.
 * Add only decoration items that fit QuackTextField to BasicTextField.
 *
 * Always use [QuackAnimationSpec] as animationSpec.
 *
 * @param width Width of QuackTextField
 * @param height height of QuackTextField
 * @param text text to display
 * @param onTextChanged Callback to be invoked when new text is entered
 * @param textStyle The style of the text to be displayed.
 * @param placeholderText A placeholder text to display when the entered
 * [text] is empty. According to the design guide of QuackTextField, only
 * single text can be placed in placeholder.
 * @param isError Whether the QuackTextField is in error state
 * @param errorText A text to display when the QuackTextField is in error state.
 * @param errorTextStyle The style of the [errorText] to be displayed.
 * @param leadingContent leading decoration content of QuackTextField
 * @param trailingContent trailing decoration content of QuackTextField
 * @param keyboardOptions keyboard options in QuackTextField
 * @param keyboardActions Keyboard actions in QuackTextField
 */
@Composable
public fun QuackTextField(
    width: QuackWidth = QuackWidth.Fill,
    height: QuackHeight = QuackHeight.Wrap,
    text: String,
    onTextChanged: (text: String) -> Unit,
    textStyle: QuackTextStyle = QuackTextStyle.Body1,
    placeholderText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    // needs remember?
    errorTextStyle: QuackTextStyle = remember {
        QuackTextStyle.Body1.change(
            color = QuackColor.OrangeRed,
        )
    },
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    focusRequester: FocusRequester = FocusRequester(),
    onFocusChanged: (Boolean) -> Unit = {},
) {
    val errorComposeTextStyle = remember(
        key1 = errorTextStyle,
    ) {
        errorTextStyle.asComposeStyle()
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .focusRequester(focusRequester)
            .onFocusChanged {
                onFocusChanged(it.isFocused)
            },
    ) {
        QuackBasicTextField(
            width = width,
            height = height,
            text = text,
            onTextChanged = onTextChanged,
            textStyle = textStyle,
            placeholderText = placeholderText,
            isError = isError,
            leadingContent = leadingContent,
            trailingContent = trailingContent,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
        // 에러 상태 가능성이 있는 경우 에러 메시지가 표시될 공간을 항상
        // 확보하기 위해 미리 에러 메시지의 공간만 차지합니다.
        // 그렇지 않으면 에러 메시지가 보일 때 없던 공간이 갑자기 확장되면서
        // 순간적으로 레이아웃의 높이에 변동이 생깁니다.
        Box(
            modifier = Modifier.wrapContentSize(),
        ) {
            if (errorText != null) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = QuackTextFieldErrorTextTopPadding,
                        )
                        .zIndex(
                            zIndex = 1f,
                        )
                        .drawWithContent {},
                    text = errorText,
                    style = errorComposeTextStyle,
                )
            }
            this@Column.AnimatedVisibility(
                visible = isError,
                modifier = Modifier.zIndex(
                    zIndex = 2f,
                ),
                enter = fadeIn(
                    animationSpec = QuackAnimationSpec(),
                ) + expandVertically(
                    animationSpec = QuackAnimationSpec(),
                ),
                exit = fadeOut(
                    animationSpec = QuackAnimationSpec(),
                ) + shrinkVertically(
                    animationSpec = QuackAnimationSpec(),
                ),
            ) {
                checkNotNull(
                    value = errorText,
                ) {
                    "errorText must not be null when isError is true"
                }
                Text(
                    modifier = Modifier.padding(
                        top = QuackTextFieldErrorTextTopPadding,
                    ),
                    text = errorText,
                    style = errorComposeTextStyle,
                )
            }
        }
    }
}

/**
 * Draws the most basic QuackQuack's TextField.
 * Add only decoration items that fit QuackTextField to [BasicTextField].
 *
 * @param width Width of QuackTextField
 * @param height height of QuackTextField
 * @param text text to display
 * @param onTextChanged Callback to be invoked when new text is entered
 * @param textStyle The style of the text to be displayed.
 * @param placeholderText A placeholder text to display when the entered [text] is empty
 * @param isError Whether the QuackTextField is in error state
 * @param leadingContent leading decoration content of QuackTextField
 * @param trailingContent trailing decoration content of QuackTextField
 * @param keyboardOptions keyboard options in QuackTextField
 * @param keyboardActions Keyboard actions in QuackTextField
 */
@Composable
internal fun QuackBasicTextField(
    width: QuackWidth = QuackWidth.Fill,
    height: QuackHeight = QuackHeight.Wrap,
    text: String,
    onTextChanged: (text: String) -> Unit,
    textStyle: QuackTextStyle = QuackTextStyle.Body1,
    placeholderText: String? = null,
    isError: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current

    // 리컴포지션이 되는 메인 조건은 Text 가 바뀌었을 때인데 그러면
    // 어차피 항상 재계산 되므로 굳이 remember 를 할 필요가 없음
    val isPlaceholder = text.isEmpty()

    // TextField's TextStyle should not be animated
    val composeTextStyle = remember(
        key1 = textStyle,
        key2 = isPlaceholder,
    ) {
        textStyle.change(
            color = QuackTextFieldColors.textColor(
                isPlaceholder = isPlaceholder,
            ),
        ).asComposeStyle()
    }

    BasicTextField(
        modifier = Modifier
            .applyQuackSize(
                width = width,
                height = height,
            )
            .drawUnderBarWithAnimation(
                width = QuackTextFieldUnderBarHeight,
                color = QuackTextFieldColors.underBarColor(
                    isError = isError,
                ),
            )
            .background(
                color = QuackColor.White.composeColor,
            )
            .padding(
                top = textStyle.calcQuackTextFieldTopPadding(),
                bottom = QuackTextFieldBottomPadding,
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = composeTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = true,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                textField = textField,
                // placeholder is displayed when text is empty
                placeholderContent = when (isPlaceholder && placeholderText != null) {
                    true -> {
                        {
                            Text(
                                text = placeholderText,
                                style = composeTextStyle,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    else -> null
                },
                leadingContent = leadingContent,
                trailingContent = trailingContent,
            )
        },
    )
}

/**
 * A decoration box used to draw decoration items for [QuackBasicTextField].
 *
 * @param textField BasicTextField to be treated as QuackTextField
 * @param placeholderContent A placeholder content to display when the entered text is empty
 * @param leadingContent The leading content of QuackTextField
 * @param trailingContent The trailing content of QuackTextField
 *
 * @see QuackTextFieldDecorationContentHorizontalPadding
 */
@Composable
private fun QuackTextFieldDecorationBox(
    textField: @Composable () -> Unit,
    placeholderContent: (@Composable () -> Unit)?,
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
) {
    Layout(
        content = {
            val padding = remember(
                key1 = leadingContent != null,
                key2 = trailingContent != null,
            ) {
                PaddingValues(
                    start = if (leadingContent != null) {
                        QuackTextFieldDecorationContentHorizontalPadding
                    } else {
                        0.dp
                    },
                    end = if (trailingContent != null) {
                        QuackTextFieldDecorationContentHorizontalPadding
                    } else {
                        0.dp
                    },
                )
            }

            if (leadingContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldLeadingContentLayoutId,
                    ),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingContent()
                }
            }
            if (trailingContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldTrailingContentLayoutId,
                    ),
                    contentAlignment = Alignment.Center,
                ) {
                    trailingContent()
                }
            }
            Box(
                modifier = Modifier
                    .layoutId(
                        layoutId = QuackTextFieldLayoutId,
                    )
                    .padding(
                        paddingValues = padding,
                    ),
                propagateMinConstraints = true,
            ) {
                textField()
            }
            if (placeholderContent != null) {
                Box(
                    modifier = Modifier
                        .layoutId(
                            layoutId = QuackTextFieldPlaceholderLayoutId,
                        )
                        .padding(
                            paddingValues = padding,
                        ),
                    propagateMinConstraints = true,
                ) {
                    placeholderContent()
                }
            }
        },
        measurePolicy = rememberQuackTextFieldMeasurePolicy(),
    )
}

/**
 * [QuackCountableTextField]
 *
 * @param text
 * @param onTextChanged
 * @param textStyle
 * @param placeholderText
 * @param maxLength
 */
@Composable
public fun QuackCountableTextField(
    text: String,
    onTextChanged: (text: String) -> Unit,
    textStyle: QuackTextStyle = QuackTextStyle.Body1,
    placeholderText: String? = null,
    maxLength: Int,
) {
    QuackTextField(
        text = text,
        onTextChanged = { text ->
            if (text.length <= maxLength) {
                onTextChanged(text)
            }
        },
        textStyle = textStyle,
        placeholderText = placeholderText,
        trailingContent = {
            QuackBody2(
                text = "${text.length} / $maxLength",
                color = QuackColor.Gray2,
            )
        },
    )
}
