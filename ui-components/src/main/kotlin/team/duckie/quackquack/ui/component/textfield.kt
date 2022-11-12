/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:NoLiveLiterals

package team.duckie.quackquack.ui.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.zIndex
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.component.internal.QuackTextFieldLayoutId
import team.duckie.quackquack.ui.component.internal.QuackTextFieldLeadingContentLayoutId
import team.duckie.quackquack.ui.component.internal.QuackTextFieldPlaceholderLayoutId
import team.duckie.quackquack.ui.component.internal.QuackTextFieldTrailingContentLayoutId
import team.duckie.quackquack.ui.component.internal.rememberQuackTextFieldMeasurePolicy
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.drawAnimatedLine
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.theme.LocalQuackTextFieldColors
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.npe
import team.duckie.quackquack.ui.util.runIf

/**
 * QuackTextField 의 리소스 모음
 */
private object QuackTextFieldDefaults {
    object Basic {
        val InputTextPadding = PaddingValues(
            top = 18.dp,
            bottom = 8.dp,
        )

        /**
         * leading content 의 padding 을 계산합니다.
         *
         * @param isPriceTextField 가격을 나타내는 TextField 인지 여부
         *
         * @return [isPriceTextField] 값에 따른 leading content 의 padding
         */
        @Stable
        private fun leadingContentPaddingFor(
            isPriceTextField: Boolean,
        ) = PaddingValues(
            end = when (isPriceTextField) {
                true -> 4.dp
                else -> 8.dp
            },
        )

        private val TrailingContentPadding = PaddingValues(
            start = 8.dp,
        )

        /**
         * 입력되는 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태에 있는지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun inputTypographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        /**
         * trailing content 에 표시되는 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isEnabled trailing content 가 활성화 되어 있는지 여부
         *
         * @return [isEnabled] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        private fun trailingTextTypographyFor(
            isEnabled: Boolean,
        ) = QuackTextStyle.Subtitle.change(
            color = when (isEnabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray2
            }
        )

        val BackgroundColor = QuackColor.White

        private val LeadingIconSize = DpSize(
            all = 16.dp,
        )

        /**
         * TextField 의 leading content 에 표시되는 아이콘의 틴트를 계산합니다.
         *
         * @param isEnabled leading content 가 활성화 되어 있는지 여부
         *
         * @return [isEnabled] 여부에 따라 사용할 틴트
         */
        @Stable
        private fun leadingIconTintFor(
            isEnabled: Boolean,
        ) = when (isEnabled) {
            true -> QuackColor.Black
            else -> QuackColor.Gray2
        }

        val UnderlineColor = QuackColor.Gray3
        val UnderlineWidth = 1.dp

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
                    // QuackText XX / 애니메이션이 적용되면 안됨
                    Text(
                        text = placeholderText,
                        style = inputTypographyFor(
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

        /**
         * leading content 로 표시될 컴포저블을 나타냅니다.
         *
         * @param icon 표시할 아이콘
         * @param isEnabled leading content 가 활성화 되어 있는지 여부
         * @param isPriceTextField 가격을 나타내는 TextField 인지 여부
         *
         * @return leading content 로 표시될 컴포저블.
         * [icon] 과 [isEnabled] 의 정보중에 null 이 있으면 null 이 반환됩니다.
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun LeadingIcon(
            icon: QuackIcon?,
            isEnabled: Boolean?,
            isPriceTextField: Boolean,
        ): (@Composable () -> Unit)? {
            return if (icon == null || isEnabled == null) {
                null
            } else {
                {
                    QuackImage(
                        modifier = Modifier.padding(
                            paddingValues = leadingContentPaddingFor(
                                isPriceTextField = isPriceTextField,
                            ),
                        ),
                        src = icon,
                        tint = leadingIconTintFor(
                            isEnabled = isEnabled,
                        ),
                        size = LeadingIconSize,
                    )
                }
            }
        }

        /**
         * trailing content 로 표시될 텍스트 컴포저블을 나타냅니다.
         *
         * @param text 표시할 텍스트
         * @param isEnabled trailing content 가 활성화 되어 있는지 여부
         * @param onClick trailing content 가 클릭되었을 때 호출될 콜백
         *
         * @return trailing content 로 표시될 컴포저블.
         * [text] 혹은 [isEnabled] 값에 null 이 있으면 null 이 반환됩니다.
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun TrailingText(
            text: String?,
            isEnabled: Boolean?,
            onClick: (() -> Unit)?,
        ): (@Composable () -> Unit)? {
            return if (text == null || isEnabled == null) {
                null
            } else {
                {
                    QuackText(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(
                                paddingValues = TrailingContentPadding,
                            ),
                        text = text,
                        style = trailingTextTypographyFor(
                            isEnabled = isEnabled,
                        ),
                        singleLine = true,
                    )
                }
            }
        }
    }

    object Basic2 {
        /**
         * TextField 의 패딩을 계산합니다.
         *
         * @param hasLeadingIcon leading icon 이 있는지 여부
         *
         * @return [hasLeadingIcon] 여부에 따라 사용할 패딩
         */
        @Stable
        fun inputTextPaddingFor(
            hasLeadingIcon: Boolean,
        ) = PaddingValues(
            horizontal = when (hasLeadingIcon) {
                true -> 8.dp
                else -> 16.dp
            },
            vertical = 17.dp,
        )

        private val LeadingIconPadding = PaddingValues(
            start = 12.dp,
        )
        private val TrailingIconPadding = PaddingValues(
            end = 12.dp,
        )

        /**
         * 입력되는 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태에 있는지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.Subtitle.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        val BackgroundColor = QuackColor.White

        private val LeadingIconSize = DpSize(
            all = 24.dp,
        )
        private val LeadingIconTint = QuackColor.Gray1

        private val TrailingIconSize = DpSize(
            all = 24.dp,
        )

        /**
         * TextField 의 leading content 에 표시되는 아이콘의 틴트를 계산합니다.
         *
         * @param isEnabled leading content 가 활성화 되어 있는지 여부
         *
         * @return [isEnabled] 여부에 따라 사용할 틴트
         */
        @Stable
        private fun trailinIconTintFor(
            isEnabled: Boolean,
        ) = when (isEnabled) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Gray2
        }

        val UnderlineWidth = 1.dp
        val UnderlineColor = QuackColor.Gray3

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
            placeholderText: String?,
        ): (@Composable () -> Unit)? {
            return if (isPlaceholder && placeholderText != null) {
                {
                    // QuackText XX / 애니메이션이 적용되면 안됨
                    Text(
                        text = placeholderText,
                        style = Basic.inputTypographyFor(
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

        /**
         * leading content 로 표시될 아이콘 컴포저블을 나타냅니다.
         *
         * @param icon 표시할 아이콘
         * @param onClick leading content 가 클릭됐을 때 호출될 콜백
         *
         * @return leading content 를 나타내는 컴포저블.
         * [icon] 이 null 이면 null 을 반환합니다.
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun LeadingIcon(
            icon: QuackIcon?,
            onClick: (() -> Unit)?,
        ): (@Composable () -> Unit)? {
            return if (icon == null) {
                null
            } else {
                {
                    QuackImage(
                        modifier = Modifier.padding(
                            paddingValues = LeadingIconPadding,
                        ),
                        src = icon,
                        size = LeadingIconSize,
                        tint = LeadingIconTint,
                        onClick = onClick,
                    )
                }
            }
        }

        /**
         * trailing content 로 표시될 아이콘 컴포저블을 나타냅니다.
         *
         * @param icon 표시할 아이콘
         * @param isEnabled trailing content 가 활성화 되어 있는지 여부
         * @param onClick trailing content 가 클릭되었을 때 호출될 콜백
         *
         * @return trailing content 로 표시될 컴포저블.
         * [icon] 혹은 [isEnabled] 값에 null 이 있으면 null 이 반환됩니다.
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun TrailingIcon(
            icon: QuackIcon?,
            isEnabled: Boolean?,
            onClick: (() -> Unit)?,
        ): (@Composable () -> Unit)? {
            return if (icon == null || isEnabled == null) {
                null
            } else {
                {
                    QuackImage(
                        modifier = Modifier.padding(
                            paddingValues = TrailingIconPadding,
                        ),
                        src = icon,
                        size = TrailingIconSize,
                        tint = trailinIconTintFor(
                            isEnabled = isEnabled,
                        ),
                        onClick = onClick,
                    )
                }
            }
        }
    }

    object Profile { // counting text field
        val InputTextPadding = PaddingValues(
            top = 16.dp,
            bottom = 8.dp,
            end = 8.dp,
        )
        private val ErrorTextPadding = PaddingValues(
            top = 4.dp,
        )

        /**
         * trailing content 에 배치되는 counting text 의 개별 요소별 사이 패딩
         *
         * ```
         * 6/10
         * ```
         *
         * 에서 `6`, `/`, `10` 의 사이 패딩을 나타냅니다.
         */
        private val TrailingCountingTextGap = 2.dp

        /**
         * trailing content 에 배치되는 컨텐츠들의 사이 패딩
         *
         * ```
         * 6/10 X
         * ```
         *
         * 에서 `6/10`, `X` 의 사이 패딩을 나타냅니다.
         */
        private val TrailingContentGap = 8.dp

        /**
         * 입력되는 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 상태에 있는지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun inputTypographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.HeadLine2.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        private val ErrorTextTypography = QuackTextStyle.Body1.change(
            color = QuackColor.Alert,
        )

        /**
         * trailing content 로 들어가는 입력된 글자 수 카운팅 텍스트의 [QuackTextStyle]
         *
         * ```
         * 6/10
         * ```
         *
         * 에서 기준이 되는 `/10` 부분에 해당합니다.
         */
        private val TrailingCountingtBaselineTexTypograhy = QuackTextStyle.Body2.change(
            color = QuackColor.Gray2,
        )

        /**
         * trailing content 로 들어가는 입력된 글자 수 카운팅 텍스트의
         * [QuackTextStyle] 를 계산합니다.
         *
         * ```
         * 6/10
         * ```
         *
         * 에서 현재 입력 상태가 되는 `6` 부분에 해당합니다.
         *
         * @param isEmpty 현재 입력된 텍스트가 비어있는지 여부
         *
         * @return [isEmpty] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        private fun trailingCountingStateTextTypographyFor(
            isEmpty: Boolean,
        ) = QuackTextStyle.Body2.runIf(
            condition = isEmpty,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        val BackgroundColor = QuackColor.White

        private val TrailingIconSize = DpSize(
            all = 16.dp,
        )
        private val TrailingIconTint = QuackColor.Gray2

        /**
         * Underline 의 색상을 계산합니다.
         *
         * @param isError 현재 에러 상태를 나타내고 있는지 여부
         *
         * @return [isError] 여부에 따라 사용할 색상
         */
        @Stable
        fun underlineColorFor(
            isError: Boolean,
        ) = when (isError) {
            true -> QuackColor.Alert
            else -> QuackColor.Gray3
        }

        val UnderlineWidth = 1.dp

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
                    // QuackText XX / 애니메이션이 적용되면 안됨
                    Text(
                        text = placeholderText,
                        style = inputTypographyFor(
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

        /**
         * 에러 상태로 표시될 컴포저블을 나타냅니다.
         *
         * @param text 에러 텍스트
         * @param invisible invisible 여부.
         * true 일 경우 에러 메시지가 표시될 공간을 항상 확보하기 위해
         * 미리 에러 메시지의 공간만 차지합니다. 그렇지 않으면 에러 메시지가
         * 보일 때 없던 공간이 갑자기 확장되면서 순간적으로 레이아웃의 높이에 변동이 생깁니다.
         *
         * @return 에러 상태로 표시될 컴포저블
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun ErrorText(
            text: String,
            invisible: Boolean,
        ): @Composable () -> Unit {
            return {
                // QuackText X
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            paddingValues = ErrorTextPadding,
                        )
                        .zIndex(
                            zIndex = when (invisible) {
                                true -> 1f
                                else -> 2f
                            },
                        )
                        .drawWithContent {
                            if (!invisible) {
                                drawContent()
                            }
                        },
                    text = text,
                    style = ErrorTextTypography.asComposeStyle(),
                )
            }
        }

        private const val TrailingCounterLayoutId = "QuackProfileTextFieldTrailingCounterLayout"
        private const val TrailingClearButtonLayoutId = "QuackProfileTextFieldTrailingClearButtonLayout"

        /**
         * trailing content 에 배치되는 컴포저블을 나타냅니다.
         *
         * @param state 입력된 글자 수
         * @param baseline 최대 입력 가능한 글자 수
         * @param onCleared 글자 초기화 버튼을 눌렀을 때 호출될 콜백
         *
         * @return trailing content 에 배치되는 컴포저블
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun TrailingContent(
            state: Int,
            baseline: Int,
            onCleared: () -> Unit,
        ): @Composable () -> Unit {
            // [요구 사항]
            // 기본적으로 profile text field 의 trailing content 는 2개로 나뉨
            // 1. counter
            // 2. clear button
            // 글자가 입력되지 않은 상태에서는 clear button 이 보이지 않음
            // 위 제약을 감안해서 counter + clear button 의 레이아웃을 모두 차지하여
            // trailing content 를 그리고, 글자가 입력되지 않은 상태에서는 clear button 을
            // gone 하고 원래 가로 길이만큼 추가 start 패딩을 갖는 식으로 구현해야 함

            return {
                QuackAnimatedContent(
                    modifier = Modifier.wrapContentSize(),
                    targetState = state == 0,
                ) { isEmpty ->
                    Layout(
                        modifier = Modifier.wrapContentSize(),
                        content = {
                            // counter
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .layoutId(
                                        layoutId = TrailingCounterLayoutId,
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = TrailingCountingTextGap,
                                ),
                            ) {
                                QuackText(
                                    text = state.toString(),
                                    style = trailingCountingStateTextTypographyFor(
                                        isEmpty = state == 0,
                                    ),
                                    singleLine = true,
                                )
                                QuackText(
                                    text = "/",
                                    style = TrailingCountingtBaselineTexTypograhy,
                                    singleLine = true,
                                )
                                QuackText(
                                    text = baseline.toString(),
                                    style = TrailingCountingtBaselineTexTypograhy,
                                    singleLine = true,
                                )
                            }
                            // clear button
                            QuackImage(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .layoutId(
                                        layoutId = TrailingClearButtonLayoutId,
                                    ),
                                src = QuackIcon.Close,
                                size = TrailingIconSize,
                                tint = TrailingIconTint,
                                onClick = onCleared,
                            )
                        },
                    ) { measurables, constraints ->
                        val trailingCounterMeasurable = measurables.fastFirstOrNull { measurable ->
                            measurable.layoutId == TrailingCounterLayoutId
                        } ?: npe()
                        val trailingClearButtonMeasurable = measurables.fastFirstOrNull { measurable ->
                            measurable.layoutId == TrailingClearButtonLayoutId
                        } ?: npe()

                        val trailingCounterPlaceable = trailingCounterMeasurable.measure(
                            constraints = constraints,
                        )
                        val trailingClearButtonPlaceable = trailingClearButtonMeasurable.measure(
                            constraints = constraints,
                        )

                        val maxWidth = trailingCounterPlaceable.width
                            .plus(
                                other = trailingClearButtonPlaceable.width,
                            )
                            .plus(
                                other = TrailingContentGap.roundToPx(),
                            )
                        val maxHeight = maxOf(
                            a = trailingCounterPlaceable.height,
                            b = trailingClearButtonPlaceable.height,
                        )

                        layout(
                            width = maxWidth,
                            height = maxHeight,
                        ) {
                            when (isEmpty) {
                                true -> { // counter 만 표시
                                    trailingCounterPlaceable.place(
                                        x = trailingClearButtonPlaceable.width + TrailingContentGap.roundToPx(),
                                        y = 0,
                                    )
                                }
                                else -> { // counter, clear button 모두 표시
                                    trailingCounterPlaceable.place(
                                        x = 0,
                                        y = 0,
                                    )
                                    trailingClearButtonPlaceable.place(
                                        x = trailingCounterPlaceable.width + TrailingContentGap.roundToPx(),
                                        y = 0,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // TODO: 레이아웃 커스텀으로 만들어야 할 듯 (깊은 고민 필요)
    @Suppress("unused")
    object Search {
        val ContainerPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 6.dp,
        )

        /**
         * 입력되는 텍스트의 [QuackTextStyle] 을 계산합니다.
         *
         * @param isPlaceholder 현재 placeholder 가 보이고 있는지 여부
         *
         * @return [isPlaceholder] 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            isPlaceholder: Boolean,
        ) = QuackTextStyle.Body1.runIf(
            condition = !isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }

        /**
         * TextField 의 배경 색상을 계산합니다.
         *
         * @param isFocused 현재 포커싱이 되어있는지 여부
         *
         * @return [isFocused] 여부에 따라 사용할 색상
         */
        @Stable
        fun textFieldBackgroundColorFor(
            isFocused: Boolean,
        ) = when (isFocused) {
            true -> QuackColor.White
            else -> QuackColor.Gray3
        }

        val ContainerBackgroundColor = QuackColor.White

        val TextFieldShape = RoundedCornerShape(
            size = 8.dp,
        )

        private val LeadingIconSize = DpSize(
            all = 24.dp,
        )
        private val LeadingIconTint = QuackColor.Black

        private val TrailingIconSize = DpSize(
            all = 16.dp,
        )
        private val TrailingIconTint = QuackColor.Gray2
    }
}

/**
 * 가장 기초적인 TextField 를 그립니다.
 * [QuackBasicTextField] 는 크게 다음과 같은 특징을 갖습니다.
 *
 * 1. underline 이 컴포넌트 하단에 표시됩니다.
 * 2. leading icon 을 가질 수 있습니다.
 * 3. trailing text 를 가질 수 있습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param onTextChanged 새로운 텍스트가 입력됐을 때 호출될 람다
 * @param placeholderText 텍스트가 입력되지 않았을 때 표시할 텍스트
 * @param leadingIcon leading content 로 표시할 아이콘
 * @param trailingText trailing content 로 표시할 텍스트
 * @param trailingTextOnClick trailing content 가 클릭됐을 때 호출될 람다
 * @param keyboardOptions 키보드 옵션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackBasicTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    leadingIcon: QuackIcon? = null,
    trailingText: String? = null,
    trailingTextOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(
    receiver = QuackTextFieldDefaults.Basic,
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current

    // 리컴포지션이 되는 메인 조건은 Text 가 바뀌었을 때인데 그러면
    // 어차피 항상 재계산 되므로 굳이 remember 를 할 필요가 없음
    val isPlaceholder = text.isEmpty()

    // 애니메이션 적용 X
    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        inputTypographyFor(
            isPlaceholder = isPlaceholder,
        ).asComposeStyle()
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawAnimatedLine(
                thickness = UnderlineWidth,
                color = UnderlineColor,
                startOffsetProvider = { size ->
                    Offset(
                        x = 0f,
                        y = size.height - UnderlineWidth.toPx(),
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = size.width,
                        y = size.height - UnderlineWidth.toPx(),
                    )
                },
            )
            .background(
                color = BackgroundColor.composeColor,
            )
            .padding(
                paddingValues = InputTextPadding,
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = inputTypography,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = true,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                textField = textField,
                placeholderContent = Placeholder(
                    isPlaceholder = isPlaceholder,
                    placeholderText = placeholderText,
                ),
                leadingContent = LeadingIcon(
                    icon = leadingIcon,
                    isEnabled = !isPlaceholder,
                    isPriceTextField = false,
                ),
                trailingContent = TrailingText(
                    text = trailingText,
                    isEnabled = !isPlaceholder,
                    onClick = trailingTextOnClick,
                ),
            )
        },
    )
}

/**
 * 가격을 입력받는 TextField 를 그립니다.
 * [QuackPriceTextField] 는 크게 다음과 같은 특징을 갖습니다.
 *
 * 1. underline 이 컴포넌트 하단에 표시됩니다.
 * 2. 항상 [QuackIcon.Won] 을 leading icon 으로 표시합니다.
 * 3. 항상 [KeyboardType.Number] 타입의 키보드를 사용합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param priceText 표시할 가격 텍스트
 * @param onPriceTextChanged 새로운 가격이 입력됐을 때 호출될 람다
 * @param placeholderText 가격이 입력되지 않았을 때 표시할 텍스트
 * @param imeAction IME 액션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackPriceTextField(
    modifier: Modifier = Modifier,
    priceText: String,
    onPriceTextChanged: (text: String) -> Unit,
    placeholderText: String,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(
    receiver = QuackTextFieldDefaults.Basic,
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current

    // 리컴포지션이 되는 메인 조건은 Text 가 바뀌었을 때인데 그러면
    // 어차피 항상 재계산 되므로 굳이 remember 를 할 필요가 없음
    val isPlaceholder = priceText.isEmpty()

    // 애니메이션 적용 X
    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        inputTypographyFor(
            isPlaceholder = isPlaceholder,
        ).asComposeStyle()
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawAnimatedLine(
                thickness = UnderlineWidth,
                color = UnderlineColor,
                startOffsetProvider = { size ->
                    Offset(
                        x = 0f,
                        y = size.height - UnderlineWidth.toPx(),
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = size.width,
                        y = size.height - UnderlineWidth.toPx(),
                    )
                },
            )
            .background(
                color = BackgroundColor.composeColor,
            )
            .padding(
                paddingValues = InputTextPadding,
            ),
        value = priceText,
        onValueChange = onPriceTextChanged,
        textStyle = inputTypography,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
        ),
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = true,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                textField = textField,
                placeholderContent = Placeholder(
                    isPlaceholder = isPlaceholder,
                    placeholderText = placeholderText,
                ),
                leadingContent = LeadingIcon(
                    icon = QuackIcon.Won,
                    isEnabled = !isPlaceholder,
                    isPriceTextField = true,
                ),
            )
        },
    )
}

/**
 * [QuackBasicTextField] 의 변형을 그립니다.
 * [QuackBasic2TextField] 는 크게 다음과 같은 특징을 갖습니다.
 *
 * 1. underline 이 컴포넌트 상단에 표시됩니다.
 * 2. leading icon 과 trailing icon 을 모두 가질 수 있습니다.
 * 3. placeholder text 를 선택적으로 받습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param onTextChanged 새로운 텍스트가 입력됐을 때 호출될 람다
 * @param placeholderText 텍스트가 입력되지 않았을 때 표시할 텍스트
 * @param leadingIcon leading content 로 표시할 아이콘
 * @param leadingIconOnClick leading icon 을 클릭했을 때 호출될 람다
 * @param trailingIcon trailing content 로 표시할 아이콘
 * @param trailingIconOnClick trailing content 가 클릭됐을 때 호출될 람다
 * @param keyboardOptions 키보드 옵션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackBasic2TextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String? = null,
    leadingIcon: QuackIcon? = null,
    leadingIconOnClick: (() -> Unit)? = null,
    trailingIcon: QuackIcon? = null,
    trailingIconOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(
    receiver = QuackTextFieldDefaults.Basic2,
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current

    // 리컴포지션이 되는 메인 조건은 Text 가 바뀌었을 때인데 그러면
    // 어차피 항상 재계산 되므로 굳이 remember 를 할 필요가 없음
    val isPlaceholder = text.isEmpty()

    // 애니메이션 적용 X
    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        typographyFor(
            isPlaceholder = isPlaceholder,
        ).asComposeStyle()
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawAnimatedLine(
                thickness = UnderlineWidth,
                color = UnderlineColor,
                startOffsetProvider = {
                    Offset(
                        x = 0f,
                        y = 0f,
                    )
                },
                endOffsetProvider = { size ->
                    Offset(
                        x = size.width,
                        y = 0f,
                    )
                },
            )
            .background(
                color = BackgroundColor.composeColor,
            )
            .padding(
                paddingValues = inputTextPaddingFor(
                    hasLeadingIcon = leadingIcon != null,
                ),
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = inputTypography,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        // TextField is always single line
        // TextArea is always multi line
        singleLine = true,
        cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
        decorationBox = { textField ->
            QuackTextFieldDecorationBox(
                textField = textField,
                placeholderContent = Placeholder(
                    isPlaceholder = isPlaceholder,
                    placeholderText = placeholderText,
                ),
                leadingContent = LeadingIcon(
                    icon = leadingIcon,
                    onClick = leadingIconOnClick,
                ),
                trailingContent = TrailingIcon(
                    icon = trailingIcon,
                    isEnabled = !isPlaceholder,
                    onClick = trailingIconOnClick,
                ),
            )
        },
    )
}

/**
 * 닉네임 입력에 사용되는 TextField 를 그립니다.
 * [QuackProfileTextField] 는 크게 다음과 같은 특징을 갖습니다.
 *
 * 1. underline 이 컴포넌트 하단에 표시됩니다.
 * 2. 항상 [QuackIcon.Close] 을 trailing icon 으로 표시합니다.
 * 3. 입력된 텍스트의 Counter 를 trailing text 로 항상 표시합니다.
 * 4. 항상 [KeyboardType.Text] 타입의 키보드를 사용합니다.
 * 5. 입력 가능한 최대 글자 수를 받습니다.
 * 만약 이 수를 넘어섰다면 에러 텍스트를 표시합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param maxLength 최대 입력 가능한 글자 수
 * @param text 표시할 텍스트
 * @param onTextChanged 새로운 텍스트가 입력됐을 때 호출될 람다
 * @param placeholderText 텍스트가 입력되지 않았을 때 표시할 텍스트
 * @param errorText 최대 입력 가능한 글자 수를 넘었을 때 표시할 에러 텍스트
 * @param onCleared trailing content 가 클릭됐을 때 호출될 람다
 * @param imeAction 키보드 옵션
 * @param keyboardActions 키보드 액션
 */
@Composable
public fun QuackProfileTextField(
    modifier: Modifier = Modifier,
    maxLength: Int,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    errorText: String,
    onCleared: () -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
): Unit = with(
    receiver = QuackTextFieldDefaults.Profile,
) {
    val quackTextFieldColors = LocalQuackTextFieldColors.current

    // 리컴포지션이 되는 메인 조건은 Text 가 바뀌었을 때인데 그러면
    // 어차피 항상 재계산 되므로 굳이 remember 를 할 필요가 없음
    val isPlaceholder = text.isEmpty()
    val isError = text.length > maxLength

    // 애니메이션 적용 X
    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        inputTypographyFor(
            isPlaceholder = isPlaceholder,
        ).asComposeStyle()
    }

    Column(
        modifier = modifier.wrapContentSize(),
    ) {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .drawAnimatedLine(
                    thickness = UnderlineWidth,
                    color = underlineColorFor(
                        isError = isError,
                    ),
                    startOffsetProvider = { size ->
                        Offset(
                            x = 0f,
                            y = size.height - UnderlineWidth.roundToPx(),
                        )
                    },
                    endOffsetProvider = { size ->
                        Offset(
                            x = size.width,
                            y = size.height - UnderlineWidth.roundToPx(),
                        )
                    },
                )
                .background(
                    color = BackgroundColor.composeColor,
                )
                .padding(
                    paddingValues = InputTextPadding,
                ),
            value = text,
            onValueChange = onTextChanged,
            textStyle = inputTypography,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
            ),
            keyboardActions = keyboardActions,
            // TextField is always single line
            // TextArea is always multi line
            singleLine = true,
            cursorBrush = quackTextFieldColors.textFieldCursorColor.toBrush(),
            decorationBox = { textField ->
                QuackTextFieldDecorationBox(
                    textField = textField,
                    placeholderContent = Placeholder(
                        isPlaceholder = isPlaceholder,
                        placeholderText = placeholderText,
                    ),
                    trailingContent = TrailingContent(
                        state = text.length,
                        baseline = maxLength,
                        onCleared = onCleared,
                    ),
                )
            },
        )
        Box(
            modifier = Modifier.wrapContentSize(),
        ) {
            ErrorText(
                text = errorText,
                invisible = true,
            )
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
                ErrorText(
                    text = errorText,
                    invisible = false,
                )
            }
        }
    }
}

/**
 * A decoration box used to draw decoration items for [QuackBasicTextField].
 *
 * @param textField BasicTextField to be treated as QuackTextField
 * @param placeholderContent A placeholder content to display when the entered text is empty
 * @param leadingContent The leading content of QuackTextField
 * @param trailingContent The trailing content of QuackTextField
 */
@Composable
private fun QuackTextFieldDecorationBox(
    textField: @Composable () -> Unit,
    placeholderContent: (@Composable () -> Unit)?,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Layout(
        content = {
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
                modifier = Modifier.layoutId(
                    layoutId = QuackTextFieldLayoutId,
                ),
                propagateMinConstraints = true,
            ) {
                textField()
            }
            if (placeholderContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldPlaceholderLayoutId,
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
