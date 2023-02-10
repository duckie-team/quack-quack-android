/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.applyAnimatedQuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackTextFieldDecorationBox
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.theme.LocalQuackTextFieldColors
import team.duckie.quackquack.ui.util.runIf

/**
 * QuackTextArea 의 리소스 모음
 */
private object QuackTextAreaDefaults {
    // 제플린상 Write 영역에 속함
    object Basic {
        val TextPadding = PaddingValues(
            vertical = 9.dp,
        )

        /**
         * 적용될 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태인지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        val BackgroundColor = QuackColor.White

        /**
         * Placeholder 로 표시될 컴포저블을 나타냅니다.
         *
         * @param isPlaceholder 현재 placeholder 상태에 있는지 여부
         * @param placeholderText placeholder 로 표시될 텍스트
         *
         * @return placeholder 로 표시될 컴포저블
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun Placeholder(
            isPlaceholder: Boolean,
            placeholderText: String,
        ): (@Composable () -> Unit)? {
            return if (isPlaceholder) {
                {
                    Text(
                        text = placeholderText,
                        style = typographyFor(
                            isPlaceholder = true,
                        ).asComposeStyle(),
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            } else {
                null
            }
        }
    }

    object Review {
        val TextPadding = PaddingValues(
            all = 16.dp,
        )

        /**
         * 적용될 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태인지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        val BackgroundColor = QuackColor.White

        /**
         * 적용될 [QuackBorder] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태인지 여부
         * @param focused 현재 focusing 상태인지 여부
         *
         * @return 상태에 따라 사용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            isPlaceholder: Boolean,
            focused: Boolean,
        ) = QuackBorder(
            color = when (isPlaceholder || !focused) {
                true -> QuackColor.Gray3
                else -> QuackColor.DuckieOrange
            },
        )

        val Shape = RoundedCornerShape(
            size = 8.dp,
        )

        /**
         * Review 타입의 TextArea 는 최소 높이 값을 갖습니다.
         */
        val MinHeight = 108.dp

        /**
         * Placeholder 로 표시될 컴포저블을 나타냅니다.
         *
         * @param isPlaceholder 현재 placeholder 상태에 있는지 여부
         * @param placeholderText placeholder 로 표시될 텍스트
         *
         * @return placeholder 로 표시될 컴포저블
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun Placeholder(
            isPlaceholder: Boolean,
            placeholderText: String,
        ): (@Composable () -> Unit)? {
            return if (isPlaceholder) {
                {
                    Text(
                        text = placeholderText,
                        style = typographyFor(
                            isPlaceholder = true,
                        ).asComposeStyle(),
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            } else {
                null
            }
        }
    }

    object GoodsDetail {
        val TextPadding = PaddingValues(
            vertical = 18.dp,
        )

        /**
         * 적용될 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태인지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        val BackgroundColor = QuackColor.White

        /**
         * Goods Detail 타입의 TextArea 는 최소 높이 값을 갖습니다.
         */
        val MinHeight = 224.dp

        /**
         * Placeholder 로 표시될 컴포저블을 나타냅니다.
         *
         * @param isPlaceholder 현재 placeholder 상태에 있는지 여부
         * @param placeholderText placeholder 로 표시될 텍스트
         *
         * @return placeholder 로 표시될 컴포저블
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun Placeholder(
            isPlaceholder: Boolean,
            placeholderText: String,
        ): (@Composable () -> Unit)? {
            return if (isPlaceholder) {
                {
                    Text(
                        text = placeholderText,
                        style = typographyFor(
                            isPlaceholder = true,
                        ).asComposeStyle(),
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            } else {
                null
            }
        }
    }
}

/**
 * 가장 기초적인 TextArea 를 그립니다.
 * [QuackBasicTextArea] 는 다음과 같은 특징이 있습니다.
 *
 * 1. 항상 상위 컴포저블의 가로 길이에 꽉차게 그려집니다.
 * 2. 항상 [KeyboardType.Number] 타입의 키보드를 사용합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param onTextChanged 새로운 텍스트가 입력됐을 때 호출될 람다
 * @param placeholderText 텍스트가 입력되지 않았을 때 표시할 텍스트
 * @param imeAction IME 액션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackBasicTextArea(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(
    receiver = QuackTextAreaDefaults.Basic,
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current
    val isPlaceholder = text.isEmpty()

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BackgroundColor.composeColor,
            )
            .padding(
                paddingValues = TextPadding,
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = typographyFor(
            isPlaceholder = false,
        ).asComposeStyle(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
        ),
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = false,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                isTextArea = true,
                textField = textField,
                placeholderContent = Placeholder(
                    isPlaceholder = isPlaceholder,
                    placeholderText = placeholderText,
                ),
            )
        },
    )
}

/**
 * Review 작성을 위한 TextArea 를 그립니다.
 * [QuackReviewTextArea] 는 아래와 같은 특징이 있습니다.
 *
 * 1. 최소 높이 값을 갖습니다.
 * 2. 항상 상위 컴포저블의 가로 길이에 꽉차게 그려집니다.
 * 3. 테두리를 갖습니다. 단, focusing + unplaceholder 상태일 때만 활성화됩니다.
 * 4. Rounding 된 모양을 갖습니다.
 * 5. 항상 [KeyboardType.Number] 타입의 키보드를 사용합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param onTextChanged 새로운 텍스트가 입력됐을 때 호출될 람다
 * @param focused 현재 TextArea 가 focus 상태에 있는지 여부
 * @param placeholderText 텍스트가 입력되지 않았을 때 표시할 텍스트
 * @param imeAction IME 액션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackReviewTextArea(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    focused: Boolean,
    placeholderText: String,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(QuackTextAreaDefaults.Review) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current

    // 리컴포지션이 되는 메인 조건은 Text 가 바뀌었을 때인데 그러면
    // 어차피 항상 재계산 되므로 굳이 remember 를 할 필요가 없음
    val isPlaceholder = text.isEmpty()

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = MinHeight)
            .clip(shape = Shape)
            .background(
                color = BackgroundColor.composeColor,
                shape = Shape,
            )
            .applyAnimatedQuackBorder(
                border = borderFor(
                    isPlaceholder = isPlaceholder,
                    focused = focused,
                ),
                shape = Shape,
            )
            .padding(paddingValues = TextPadding),
        value = text,
        onValueChange = onTextChanged,
        textStyle = typographyFor(isPlaceholder = false).asComposeStyle(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
        ),
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = false,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                isTextArea = true,
                textField = textField,
                placeholderContent = Placeholder(
                    isPlaceholder = isPlaceholder,
                    placeholderText = placeholderText,
                ),
            )
        },
    )
}

/**
 * Goods Detail 작성을 위한 TextArea 를 그립니다.
 * [QuackGoodsDetailTextArea] 는 아래와 같은 특징이 있습니다.
 *
 * 1. 최소 높이 값을 갖습니다.
 * 2. 항상 상위 컴포저블의 가로 길이에 꽉차게 그려집니다.
 * 3. 항상 [KeyboardType.Number] 타입의 키보드를 사용합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param onTextChanged 새로운 텍스트가 입력됐을 때 호출될 람다
 * @param placeholderText 텍스트가 입력되지 않았을 때 표시할 텍스트
 * @param imeAction IME 액션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackGoodsDetailTextArea(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(
    receiver = QuackTextAreaDefaults.GoodsDetail,
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current
    val isPlaceholder = text.isEmpty()

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(
                min = MinHeight,
            )
            .background(
                color = BackgroundColor.composeColor,
            )
            .padding(
                paddingValues = TextPadding,
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = typographyFor(
            isPlaceholder = false,
        ).asComposeStyle(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
        ),
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = false,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                isTextArea = true,
                textField = textField,
                placeholderContent = Placeholder(
                    isPlaceholder = isPlaceholder,
                    placeholderText = placeholderText,
                ),
            )
        },
    )
}
