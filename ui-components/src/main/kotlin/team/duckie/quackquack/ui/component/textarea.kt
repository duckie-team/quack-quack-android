/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.animatedQuackBorderAsState
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.modifier.applyQuackBorder
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.ProvideTextSelectionColors

/**
 * [QuackBorderTextArea] 의 테두리 속성을 계산합니다.
 *
 * @param isFocused 포커스 여부
 * @return 포커스 여부에 따라 계산된 [테두리 속성][QuackBorder]
 */
@Suppress("KDocUnresolvedReference")
private val QuackTextAreaBorder: (
    isFocused: Boolean,
) -> QuackBorder = { isFocused ->
    when (isFocused) {
        true -> QuackBorder(
            color = QuackColor.DuckieOrange,
            width = 1.dp,
        )
        false -> QuackBorder(
            color = QuackColor.Gray3,
            width = 1.dp,
        )
    }
}

/**
 * [QuackBorderTextArea] 의 [QuackTextStyle] 을 계산합니다.
 *
 * @param isPlaceholder 보여자고 있는 텍스트가 플레이스홀더인지 여부
 * @return 플레이스홀더 여부에 따라 계산된 [QuackTextStyle]
 */
@Suppress("KDocUnresolvedReference")
private val QuackBorderTextAreaTextStyle: (
    isPlaceholder: Boolean,
) -> QuackTextStyle = { isPlaceholder ->
    when (isPlaceholder) {
        true -> QuackTextStyle.Subtitle2.change(
            color = QuackColor.Gray2,
            weight = FontWeight.Normal,
        )
        else -> QuackTextStyle.Subtitle2.change(
            color = QuackColor.Black,
            weight = FontWeight.Normal,
        )
    }
}

/**
 * [QuackTextArea] 의 [QuackTextStyle] 을 계산합니다.
 *
 * @param isPlaceholder 보여자고 있는 텍스트가 플레이스홀더인지 여부
 * @return 플레이스홀더 여부에 따라 계산된 [QuackTextStyle]
 */
@Suppress("KDocUnresolvedReference")
private val QuackTextAreaTextStyle: (
    isPlaceholder: Boolean,
) -> QuackTextStyle = { isPlaceholder ->
    when (isPlaceholder) {
        true -> QuackTextStyle.Subtitle.change(
            color = QuackColor.Gray2,
            weight = FontWeight.Normal,
        )
        else -> QuackTextStyle.Subtitle.change(
            color = QuackColor.Black,
            weight = FontWeight.Normal,
        )
    }
}

/**
 * [QuackBorderTextArea] 의 안쪽에 들어갈 패딩 값
 */
private val QuackBorderTextAreaPadding = PaddingValues(
    all = 16.dp,
)

/**
 * [QuackTextArea] 의 안쪽에 들어갈 패딩 값
 */
private val QuackTextAreaPadding = PaddingValues(
    top = 10.dp,
    bottom = 8.dp,
)

/**
 * [QuackBorderTextArea] 의 최소 높이 값
 *
 * QuackTextArea 는 최소 높이 값을 갖습니다. 이 높이를
 * 초과할 경우 패딩에 맞게 늘어나야 합니다.
 */
private val QuackBorderTextAreaDefaultHeight = 140.dp

/**
 * [QuackTextArea] 의 최소 높이 값
 *
 * QuackTextArea 는 최소 높이 값을 갖습니다. 이 높이를
 * 초과할 경우 패딩에 맞게 늘어나야 합니다.
 */
private val QuackTextAreaDefaultHeight = 250.dp

private val QuackBorderTextAreaShape = RoundedCornerShape(
    size = 12.dp,
)

/**
 * 테두리를 갖는 TextArea 를 구현합니다.
 * TextArea 는 항상 현재 화면의 가로 길이를 꽉 채워서 width 가
 * 지정됩니다.
 *
 * 모든 값에는 자동으로 [QuackAnimationSpec] 애니메이션이 적용됩니다.
 *
 * @param text 표시할 텍스트
 * @param onTextChanged IME 로 텍스트가 입력됐을 때 호출되는 람다.
 * 람다의 인자로는 입력된 텍스트가 들어옵니다.
 * @param placeholderText [text] 가 비어있을 때 표시할 대체 텍스트
 * @param doneAction [IME 버튼][ImeAction.Done] 클릭 이벤트를 받았을 때 호출될 람다
 */
@Composable
public fun QuackBorderTextArea(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String = "",
    doneAction: KeyboardActionScope.() -> Unit = {},
): Unit = QuackTextAreaInternal(
    text = text,
    onTextChanged = onTextChanged,
    placeholderText = placeholderText,
    doneAction = doneAction,
    isBordered = true,
)

/**
 * 기본 TextArea 를 구현합니다.
 * TextArea 는 항상 현재 화면의 가로 길이를 꽉 채워서 width 가
 * 지정됩니다.
 *
 * 모든 값에는 자동으로 [QuackAnimationSpec] 애니메이션이 적용됩니다.
 *
 * @param text 표시할 텍스트
 * @param onTextChanged IME 로 텍스트가 입력됐을 때 호출되는 람다.
 * 람다의 인자로는 입력된 텍스트가 들어옵니다.
 * @param placeholderText [text] 가 비어있을 때 표시할 대체 텍스트
 * @param doneAction [IME 버튼][ImeAction.Done] 클릭 이벤트를 받았을 때 호출될 람다
 */
@Composable
public fun QuackTextArea(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String = "",
    doneAction: KeyboardActionScope.() -> Unit = {},
): Unit = QuackTextAreaInternal(
    text = text,
    onTextChanged = onTextChanged,
    placeholderText = placeholderText,
    doneAction = doneAction,
    isBordered = false,
)

/**
 * TextArea 들을 구현합니다. 덕키 내부용으로 사용됩니다.
 * TextArea 는 항상 현재 화면의 가로 길이를 꽉 채워서 width 가
 * 지정됩니다.
 *
 * 모든 값에는 자동으로 [QuackAnimationSpec] 애니메이션이 적용됩니다.
 *
 * @param text 표시할 텍스트
 * @param onTextChanged IME 로 텍스트가 입력됐을 때 호출되는 람다.
 * 람다의 인자로는 입력된 텍스트가 들어옵니다.
 * @param placeholderText [text] 가 비어있을 때 표시할 대체 텍스트
 * @param doneAction [IME 버튼][ImeAction.Done] 클릭 이벤트를 받았을 때 호출될 람다
 * @param isBordered 구현해야 할 TextArea 가 [QuackBorderTextArea] 인지 여부.
 * 이 값에 따라 디자인이 달라집니다.
 */
@Composable
private fun QuackTextAreaInternal(
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    doneAction: KeyboardActionScope.() -> Unit,
    isBordered: Boolean,
) {
    val isPlaceholder = text.isEmpty()
    var isFocused by remember {
        mutableStateOf(
            value = false,
        )
    }

    val shape = remember {
        when (isBordered) {
            true -> QuackBorderTextAreaShape
            else -> RectangleShape
        }
    }
    val textStyle = remember {
        when (isBordered) {
            true -> QuackBorderTextAreaTextStyle(
                /*isPlaceholder = */
                false,
            )
            else -> {
                QuackTextAreaTextStyle(
                    /*isPlaceholder = */
                    false,
                )
            }
        }.asComposeStyle()
    }
    val placeholderTextStyle = remember {
        when (isBordered) {
            true -> QuackBorderTextAreaTextStyle(
                /*isPlaceholder = */
                true,
            )
            else -> {
                QuackTextAreaTextStyle(
                    /*isPlaceholder = */
                    true,
                )
            }
        }
    }
    val animatedQuackBorderOrNull = when (isBordered) {
        true -> animatedQuackBorderAsState(
            targetValue = QuackTextAreaBorder(
                /*isFocused = */
                isFocused,
            )
        )
        else -> null
    }

    ProvideTextSelectionColors {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(
                    shape = shape,
                )
                .applyQuackBorder(
                    enabled = isBordered,
                    border = animatedQuackBorderOrNull,
                    shape = shape,
                )
                .padding(
                    paddingValues = when (isBordered) {
                        true -> QuackBorderTextAreaPadding
                        else -> QuackTextAreaPadding
                    },
                ),
        ) {
            if (isPlaceholder) {
                QuackText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(
                            zIndex = 0f,
                        ),
                    text = placeholderText,
                    style = placeholderTextStyle,
                )
            }
            BasicTextField(
                modifier = Modifier
                    .zIndex(
                        zIndex = 1f,
                    )
                    .fillMaxWidth()
                    .requiredHeightIn(
                        min = when (isBordered) {
                            true -> QuackBorderTextAreaDefaultHeight
                            else -> QuackTextAreaDefaultHeight
                        },
                    )
                    .onFocusEvent { event ->
                        isFocused = event.isFocused
                    },
                value = text,
                onValueChange = onTextChanged,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onAny = doneAction,
                ),
                textStyle = textStyle,
                cursorBrush = textFieldCursorColor.toBrush(),
            )
        }
    }
}

