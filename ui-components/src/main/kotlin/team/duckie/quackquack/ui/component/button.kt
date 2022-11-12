/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import team.duckie.quackquack.ui.component.QuackLargeButtonType.Border as LargeBorder
import team.duckie.quackquack.ui.component.QuackLargeButtonType.Compat as LargeCompat
import team.duckie.quackquack.ui.component.QuackLargeButtonType.Fill as LargeFill
import team.duckie.quackquack.ui.component.QuackSmallButtonType.Border as SmallBorder
import team.duckie.quackquack.ui.component.QuackSmallButtonType.Fill as SmallFill
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.constant.applyQuackSize
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.runtimeCheck

/**
 * [QuackLargeButton] 의 타입을 정의합니다.
 * [QuackLargeButton] 은 사용 사례에 따라 여러 타입이 나올 수 있습니다.
 *
 * @param height 해당 사용 사례에 적용되는 높이를 나타냅니다.
 *
 * @property LargeFill 44.dp 의 세로 길이를 갖고, 배경색이 적용됩니다.
 * @property LargeBorder 44.dp 의 세로 길이를 갖고, 배경색 대신 테두리가 적용됩니다.
 * 추가로 왼쪽에 아이콘을 표시할 수 있습니다.
 * @property LargeCompat 40.dp 의 세로 길이를 갖고, 배경색 대신 테두리가 적용됩니다.
 */
public enum class QuackLargeButtonType(
    private val height: Dp,
) {
    Fill(
        height = 44.dp,
    ),
    Border(
        height = 44.dp,
    ),
    Compat(
        height = 40.dp,
    )
}

/**
 * [QuackSmallButton] 의 타입을 정의합니다.
 * [QuackSmallButton] 은 사용 사례에 따라 여러 타입이 나올 수 있습니다.
 *
 * @property SmallFill 배경색이 적용됩니다.
 * @property SmallBorder 배경색 대신 테두리가 적용됩니다.
 */
public enum class QuackSmallButtonType {
    Fill,
    Border,
}

/**
 * QuackButton 들의 테마 리소스 모음
 */
private object QuackButtonDefaults {
    object LargeButton {
        val Padding = PaddingValues(
            horizontal = 16.dp,
            vertical = 13.dp,
        )

        val Typography = QuackTextStyle.Subtitle.change(
            color = QuackColor.White,
            textAlign = TextAlign.Center,
        )

        val Shape = RoundedCornerShape(
            size = 8.dp,
        )

        private val LeadingIconSize = DpSize(
            all = 24.dp,
        )

        private val LeadingIconTint = QuackColor.Gray1

        /**
         * 조건에 맞는 배경 색을 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         * @param type 버튼의 타입
         *
         * @return 버튼 활성화 여부에 따라 사용할 배경 색상
         */
        @Stable
        fun backgroundColorFor(
            enabled: Boolean,
            type: QuackLargeButtonType,
        ) = when (type) {
            LargeFill -> when (enabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray2
            }
            LargeBorder, LargeCompat -> QuackColor.White
        }

        /**
         * 조건에 맞는 [QuackBorder] 를 계산합니다.
         *
         * @param type 버튼의 타입
         *
         * @return 버튼 타입에 따라 적용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            type: QuackLargeButtonType,
        ) = when (type) {
            LargeFill -> null
            LargeBorder, LargeCompat -> QuackBorder(
                color = QuackColor.Gray3,
            )
        }

        /**
         * leading content 으로 표시될 컴포저블을 구현합니다.
         *
         * @param leadingIcon 버튼의 leading icon
         *
         * @return leading content 을 나타내는 컴포저블 람다 혹은 null.
         * [leadingIcon] 이 null 일 때 null 을 반환합니다.
         */
        @SuppressLint("ComposableNaming")
        @Composable
        fun LeadingContent(
            leadingIcon: QuackIcon?,
        ): (@Composable () -> Unit)? {
            return if (leadingIcon != null) {
                {
                    QuackImage(
                        src = leadingIcon,
                        size = LeadingIconSize,
                        tint = LeadingIconTint,
                    )
                }
            } else {
                null
            }
        }
    }

    object MediumButton {
        val Padding = PaddingValues(
            horizontal = 58.dp,
            vertical = 10.dp,
        )

        /**
         * 조건에 맞는 [QuackTextStyle] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         *
         * @return 버튼 활성화 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            enabled: Boolean,
        ) = when (enabled) {
            true -> QuackTextStyle.Title2.change(
                color = QuackColor.DuckieOrange,
                textAlign = TextAlign.Center,
            )
            else -> QuackTextStyle.Body1.change(
                color = QuackColor.Black,
                textAlign = TextAlign.Center,
            )
        }

        val Shape = RoundedCornerShape(
            size = 12.dp,
        )

        val BackgroundColor = QuackColor.White

        /**
         * 조건에 맞는 [QuackBorder] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         *
         * @return 버튼 활성화 여부에 따라 적용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            enabled: Boolean,
        ) = QuackBorder(
            color = when (enabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        )
    }

    object SmallButton {
        val Padding = PaddingValues(
            horizontal = 12.dp,
            vertical = 8.dp,
        )

        /**
         * 조건에 맞는 [QuackTextStyle] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         * @param type 버튼의 타입
         *
         * @return 버튼 활성화 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            enabled: Boolean,
            type: QuackSmallButtonType,
        ) = when (type) {
            SmallFill -> QuackTextStyle.Body1.change(
                color = QuackColor.White,
                textAlign = TextAlign.Center,
            )
            SmallBorder -> when (enabled) {
                true -> QuackTextStyle.Body1.change(
                    color = QuackColor.DuckieOrange,
                    textAlign = TextAlign.Center,
                )
                else -> QuackTextStyle.Body1.change(
                    color = QuackColor.Gray2,
                    textAlign = TextAlign.Center,
                )
            }
        }

        val Shape = RoundedCornerShape(
            size = 8.dp,
        )

        /**
         * 조건에 맞는 배경 색을 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         * @param type 버튼의 타입
         *
         * @return 버튼 활성화 여부에 따라 사용할 배경 색상
         */
        @Stable
        fun backgroundColorFor(
            enabled: Boolean,
            type: QuackSmallButtonType,
        ) = when (type) {
            SmallFill -> when (enabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray2
            }
            SmallBorder -> when (enabled) {
                true -> QuackColor.White
                else -> QuackColor.Gray3
            }
        }

        /**
         * 조건에 맞는 [QuackBorder] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         * @param type 버튼의 타입
         *
         * @return 버튼 활성화 여부에 따라 적용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            enabled: Boolean,
            type: QuackSmallButtonType,
        ) = when (type) {
            SmallFill -> null
            SmallBorder -> QuackBorder(
                color = when (enabled) {
                    true -> QuackColor.DuckieOrange
                    else -> QuackColor.Gray3
                },
            )
        }
    }

    object ChipButton {
        val Padding = PaddingValues(
            horizontal = 8.dp,
            vertical = 4.dp,
        )

        // Body2 에서 weight 만 다름
        private val DefaultTypography = QuackTextStyle(
            size = 12.sp,
            weight = FontWeight.Medium,
            letterSpacing = 0.sp,
            lineHeight = 15.sp,
            textAlign = TextAlign.Center,
        )

        /**
         * 조건에 맞는 [QuackTextStyle] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         *
         * @return 버튼 활성화 여부에 따라 사용할 [QuackTextStyle]
         */
        @Stable
        fun typographyFor(
            enabled: Boolean,
        ) = when (enabled) {
            true -> DefaultTypography.change(
                color = QuackColor.DuckieOrange,
            )
            else -> DefaultTypography.change(
                color = QuackColor.Gray2,
            )
        }

        val Shape = RoundedCornerShape(
            size = 18.dp,
        )

        /**
         * 조건에 맞는 배경 색을 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         *
         * @return 버튼 활성화 여부에 따라 사용할 배경 색상
         */
        @Stable
        fun backgroundColorFor(
            enabled: Boolean,
        ) = when (enabled) {
            true -> QuackColor.White
            else -> QuackColor.Gray3
        }

        /**
         * 조건에 맞는 [QuackBorder] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         *
         * @return 버튼 활성화 여부에 따라 적용할 [QuackBorder]
         */
        @Stable
        fun borderFor(
            enabled: Boolean,
        ) = QuackBorder(
            color = when (enabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        )
    }
}

/**
 * 덕키의 메인 버튼을 구현합니다.
 * [QuackLargeButton] 은 활성 상태에 따라 다른 배경 색상을 가지며,
 * 여러 사용 사례에 따라 디자인이 약간씩 달라집니다.
 *
 * 자동으로 모든 영역에 애니메이션이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param type 이 버튼의 사용 사례에 적합한 버튼 타입
 * @param text 버튼에 표시될 텍스트
 * @param active 버튼 활성화 여부. 배경 색상에 영향을 미칩니다.
 * @param leadingIcon 버튼 왼쪽에 표시될 아이콘.
 * [type] 이 [LargeBorder] 일 경우에만 유효합니다.
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackLargeButton(
    modifier: Modifier = Modifier,
    type: QuackLargeButtonType,
    text: String,
    active: Boolean = true,
    leadingIcon: QuackIcon? = null,
    onClick: () -> Unit,
): Unit = with(
    receiver = QuackButtonDefaults.LargeButton,
) {
    if (leadingIcon != null) {
        runtimeCheck(
            value = type == LargeBorder,
        ) {
            "QuackLargeButton 의 leadingIcon 은 Border 타입에서만 사용할 수 있습니다."
        }
    }

    QuackBasicButton(
        modifier = modifier,
        width = QuackWidth.Fill,
        shape = Shape,
        leadingContent = LeadingContent(
            leadingIcon = leadingIcon,
        ),
        text = text,
        textStyle = Typography,
        padding = Padding,
        backgroundColor = backgroundColorFor(
            enabled = active,
            type = type,
        ),
        border = borderFor(
            type = type,
        ),
        onClick = onClick,
        enabled = active,
    )
}

/**
 * 토글 용도로 사용되는 Medium Button 을 구현합니다.
 *
 * [QuackMediumToggleButton] 은 선택 여부에 따라 테두리가 달라집니다.
 *
 * 자동으로 모든 영역에 애니메이션이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 버튼에 표시될 텍스트
 * @param selected 버튼이 선택되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackMediumToggleButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
): Unit = with(
    receiver = QuackButtonDefaults.MediumButton,
) {
    QuackBasicButton(
        modifier = modifier,
        shape = Shape,
        text = text,
        textStyle = typographyFor(
            enabled = selected,
        ),
        padding = Padding,
        backgroundColor = BackgroundColor,
        border = borderFor(
            enabled = selected,
        ),
        onClick = onClick,
    )
}

/**
 * 토글 용도로 사용되는 Small Button 을 구현합니다.
 *
 * [QuackSmallButton] 은 선택 여부에 따라 다른 배경 색상과 테두리를 가지며,
 * 여러 사용 사례에 따라 디자인이 약간씩 달라집니다.
 *
 * 자동으로 모든 영역에 애니메이션이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param type 이 버튼의 사용 사례에 적합한 버튼 타입
 * @param text 버튼에 표시될 텍스트
 * @param enabled 버튼이 활성화 되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackSmallButton(
    modifier: Modifier = Modifier,
    type: QuackSmallButtonType,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
): Unit = with(
    receiver = QuackButtonDefaults.SmallButton,
) {
    QuackBasicButton(
        modifier = modifier,
        shape = Shape,
        text = text,
        textStyle = typographyFor(
            enabled = enabled,
            type = type,
        ),
        padding = Padding,
        backgroundColor = backgroundColorFor(
            enabled = enabled,
            type = type,
        ),
        border = borderFor(
            enabled = enabled,
            type = type,
        ),
        onClick = onClick,
    )
}

/**
 * Chip 역할을 하는 버튼을 구현합니다.
 * Button 의 역할보다는 Chip 의 역할이 더 크기에 Button 대신
 * Chip 네이밍을 사용했습니다.
 *
 * 선택 여부에 따라 텍스트 스타일과 배경 색상 그리고
 * 테두리 색상이 달라집니다.
 *
 * 자동으로 모든 영역에 애니메이션이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text chip 에 표시될 텍스트
 * @param selected chip 이 선택되었는지 여부
 * @param onClick chip 클릭 시 호출될 콜백
 */
@Composable
public fun QuackToggleChip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
): Unit = with(
    receiver = QuackButtonDefaults.ChipButton,
) {
    QuackBasicButton(
        modifier = modifier,
        shape = Shape,
        text = text,
        textStyle = typographyFor(
            enabled = selected,
        ),
        padding = Padding,
        backgroundColor = backgroundColorFor(
            enabled = selected,
        ),
        border = borderFor(
            enabled = selected,
        ),
        onClick = onClick,
    )
}

/**
 * QuackButton 컴포넌트를 구성하는데 사용되는 Button 의 최하위 컴포넌트 입니다
 *
 * IME 을 포함한 모든 요소에 애니메이션이 적용됩니다.
 *
 * @param modifier QuackButton 의 뼈대가 될 [Modifier]
 * @param width 버튼의 가로 길이. 기본값은 [QuackWidth.Wrap] 입니다.
 * @param height 버튼의 세로 길이. 기본값은 [QuackHeight.Wrap] 입니다.
 * @param shape 버튼의 모양. 기본값은 [RectangleShape] 입니다.
 * @param leadingContent 버튼의 텍스트 왼쪽에 표시할 컨텐츠. 기본값은 null 입니다.
 * @param text 버튼에 표시될 텍스트
 * @param textStyle 버튼의 텍스트 스타일
 * @param padding 버튼의 텍스트에 적용될 [PaddingValues].
 * 덕키 디자인 시스템의 경우 컴포넌트의 사이즈를 정적으로 고정시키지 않고,
 * 컴포넌트 안에 들어가는 요소의 사이즈에 따라 동적으로 사이즈를 결정합니다.
 * QuackButton 의 경우에는 안에 들어가는 요소가 텍스트가 됩니다.
 * 즉, 이 패딩 값에 따라 QuackButton 의 텍스트 컴포넌트 사이즈가 조정되므로
 * QuackButton 의 사이즈를 결정짓는 중요한 인자가 됩니다.
 * @param backgroundColor 버튼의 배경 색상
 * @param border 버튼에 적용할 테두리 옵션.
 * null 이 들어오면 테두리를 표시하지 않으며, 기본값은 null 입니다.
 * @param rippleEnabled 버튼을 눌렀을 때, 버튼의 배경에 리플 효과를 표시할지 여부.
 * 기본값은 true 입니다.
 * @param rippleColor 버튼이 눌렸을 때, 버튼의 배경에 표시할 리플 효과의 색상.
 * [rippleEnabled] 가 true 일 때만 작용합니다. 기본값은 색상을 지정하지 않은걸 뜻하는
 * [QuackColor.Unspecified] 입니다.
 * @param enabled 버튼을 clickable 하게 만들어주는지 여부
 * @param onClick 버튼을 눌렀을 때 호출될 콜백 함수
 *
 * @see Modifier.applyQuackSize
 * @see QuackSurface
 */
@Composable
private fun QuackBasicButton(
    modifier: Modifier = Modifier,
    width: QuackWidth = QuackWidth.Wrap,
    height: QuackHeight = QuackHeight.Wrap,
    shape: Shape,
    leadingContent: (@Composable () -> Unit)? = null,
    text: String,
    textStyle: QuackTextStyle,
    padding: PaddingValues,
    backgroundColor: QuackColor,
    border: QuackBorder? = null,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier
            .imePadding()
            .applyQuackSize(
                width = width,
                height = height,
            ),
        backgroundColor = backgroundColor,
        border = border,
        shape = shape,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = onClick.takeIf {
            enabled
        },
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(
                    paddingValues = padding,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            leadingContent?.invoke()
            // 사이 패딩 없음
            QuackText(
                text = text,
                style = textStyle,
                singleLine = true, // 버튼은 항상 single-line 을 요구함
            )
        }
    }
}
