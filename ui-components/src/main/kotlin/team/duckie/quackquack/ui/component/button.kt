/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
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
import team.duckie.quackquack.ui.component.QuackLargeButtonType.Border
import team.duckie.quackquack.ui.component.QuackLargeButtonType.Compat
import team.duckie.quackquack.ui.component.QuackLargeButtonType.Fill
import team.duckie.quackquack.ui.component.QuackSmallButtonType.Border
import team.duckie.quackquack.ui.component.QuackSmallButtonType.Fill
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.NoPadding

/**
 * [QuackLargeButton] 의 타입을 정의합니다.
 * [QuackLargeButton] 은 사용 사례에 따라 여러 타입이 나올 수 있습니다.
 *
 * @param height 해당 사용 사례에 적용되는 높이를 나타냅니다.
 *
 * @property Fill 44.dp 의 세로 길이를 갖고, 배경색이 적용됩니다.
 * @property Border 44.dp 의 세로 길이를 갖고, 배경색 대신 테두리가 적용됩니다.
 * 추가로 왼쪽에 아이콘을 표시할 수 있습니다.
 * @property Compat 40.dp 의 세로 길이를 갖고, 배경색 대신 테두리가 적용됩니다.
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
 * @property Fill 배경색이 적용됩니다.
 * @property Border 배경색 대신 테두리가 적용됩니다.
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
        val TextPadding = PaddingValues(
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

        val IconSize = DpSize(
            all = 24.dp,
        )

        /**
         * 조건에 맞는 배경 색을 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         * @param type 버튼의 타입
         *
         * @return 버튼 활성화 여부에 따라 사용할 배경 색상
         */
        fun backgroundColorFor(
            enabled: Boolean,
            type: QuackLargeButtonType,
        ) = when (type) {
            QuackLargeButtonType.Fill -> when (enabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray2
            }
            QuackLargeButtonType.Border, QuackLargeButtonType.Compat -> QuackColor.White
        }

        /**
         * 조건에 맞는 [QuackBorder] 를 계산합니다.
         *
         * @param type 버튼의 타입
         *
         * @return 버튼 타입에 따라 적용할 [QuackBorder]
         */
        fun borderFor(
            type: QuackLargeButtonType,
        ) = when (type) {
            QuackLargeButtonType.Fill -> null
            QuackLargeButtonType.Border, QuackLargeButtonType.Compat -> QuackBorder(
                color = QuackColor.Gray3,
            )
        }
    }

    object MediumButton {
        val TextPadding = PaddingValues(
            horizontal = 62.dp,
            vertical = 11.dp,
        )

        /**
         * 조건에 맞는 [QuackTextStyle] 를 계산합니다.
         *
         * @param enabled 현재 버튼이 활성화 상태인지 여부
         *
         * @return 버튼 활성화 여부에 따라 사용할 [QuackTextStyle]
         */
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
        val TextPadding = PaddingValues(
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
        fun typographyFor(
            enabled: Boolean,
            type: QuackSmallButtonType,
        ) = when (type) {
            QuackSmallButtonType.Fill -> QuackTextStyle.Body1.change(
                color = QuackColor.White,
                textAlign = TextAlign.Center,
            )
            QuackSmallButtonType.Border -> when (enabled) {
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
        fun backgroundColorFor(
            enabled: Boolean,
            type: QuackSmallButtonType,
        ) = when (type) {
            QuackSmallButtonType.Fill -> when (enabled) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray2
            }
            QuackSmallButtonType.Border -> when (enabled) {
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
        fun borderColorFor(
            enabled: Boolean,
            type: QuackSmallButtonType,
        ) = when (type) {
            QuackSmallButtonType.Fill -> null
            QuackSmallButtonType.Border -> QuackBorder(
                color = when (enabled) {
                    true -> QuackColor.DuckieOrange
                    else -> QuackColor.Gray3
                },
            )
        }
    }

    object ChipButton {
        val TextPadding = PaddingValues(
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
        fun borderColorFor(
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
 * 메인 버튼을 구현합니다.
 * [QuackLargeButton] 은 활성 상태에 따라 다른 배경 색상을 가집니다.
 *
 * 자동으로 모든 영역에 애니메이션이 적용됩니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param active 버튼 활성화 여부. 배경 색상에 영향을 미칩니다.
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackLargeButton(
    text: String,
    active: Boolean = true,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackButtonDefaults.LargeButton.Shape,
    text = text,
    textPadding = QuackLargeButtonTextPadding,
    textStyle = QuackButtonDefaults.LargeButton.Typography,
    backgroundColor = QuackButtonDefaults.backgroundColorFor(
        enabled = active,
    ),
    onClick = onClick,
    enabled = active,
)

/**
 * QuackLargeButton 의 변형을 구현합니다.
 * [QuackLargeButton] 과 형태는 동일하지만, 차이점은
 * 배경 색상이 흰색으로 고정돼 있습니다. 또한 아이콘을
 * 추가로 설정할 수 있습니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param leadingIcon 버튼 텍스트 왼쪽에 표시될 아이콘.
 * 만약 null 이 들어온 경우 아이콘을 표시하지 않으며,
 * 기본값은 null 입니다.
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
@NonRestartableComposable
public fun QuackLargeWhiteButton(
    text: String,
    leadingIcon: QuackIcon? = null,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    leadingIcon = leadingIcon,
    text = text,
    textPadding = QuackLargeButtonTextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = QuackColor.Gray3,
    ),
    onClick = onClick,
)

/**
 * QuackLargeButton 의 변형을 구현합니다.
 * [QuackLargeWhiteButton] 과 형태는 동일하지만, 차이점은
 * [QuackLargeWhiteButton] 보다 세로 길이가 약간 작습니다.
 * 또한 아이콘을 설정할 수 없습니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
@NonRestartableComposable
public fun QuackLarge40WhiteButton(
    text: String,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    text = text,
    textPadding = QuackLargeButton40TextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = QuackColor.Gray3,
    ),
    onClick = onClick,
)

/**
 * QuackMediumButton 의 변형을 구현합니다.
 * 버튼이 테두리로 강조되고, 토글 형식으로 디자인 됐으므로
 * 컴포넌트 이름에 Border 와 Toggle 을 추가로 붙여 강조했습니다.
 *
 * 버튼의 형태는 QuackMediumButton 와 동일하지만, 이름에서 부터
 * 알 수 있듯이 선택 여부에 따라 테두리 색상과 텍스트 스타일이 달라집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param selected 버튼이 선택되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackMediumBorderToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    shape = QuackMediumButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        color = when (selected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Black
        },
        weight = when (selected) {
            true -> FontWeight.Bold
            else -> FontWeight.Normal
        },
    ),
    textPadding = QuackMediumButtonTextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = when (selected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Gray3
        },
    ),
    onClick = onClick,
)

/**
 * QuackSmallButton 을 구현합니다.
 * [QuackLargeButton] 과 형태는 동일하며, 유일한 차이점은
 * 사이즈가 아주 작습니다. 역시 활성 상태에 따라 배경 색상이 달라집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param enabled 버튼이 활성화 되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackSmallButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    shape = QuackSmallButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        color = QuackColor.White,
    ),
    textPadding = QuackSmallButtonTextPadding,
    backgroundColor = quackButtonStandardBackgroundColorFor(
        enabled = enabled,
    ),
    onClick = onClick,
)

/**
 * QuackSmallButton 의 변형을 구현합니다.
 * [QuackSmallButton] 과 형태는 동일하지만, [QuackMediumBorderToggleButton] 과
 * 마찬가지로 버튼이 테두리로 강조되고, 토글 형식으로 디자인 됐으므로
 * 컴포넌트 이름에 Border 와 Toggle 을 추가로 붙여 강조했습니다.
 *
 * 이 컴포넌트 역시 이름에서 부터 알 수 있듯이
 * 선택 여부에 따라 테두리 색상과 텍스트 스타일이 달라집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param selected 버튼이 선택되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
public fun QuackSmallBorderToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    shape = QuackSmallButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        color = when (selected) {
            true -> QuackColor.Gray2
            else -> QuackColor.DuckieOrange
        },
    ),
    textPadding = QuackSmallButtonTextPadding,
    backgroundColor = when (selected) {
        true -> QuackColor.Gray3
        else -> QuackColor.White
    },
    border = QuackBorder(
        color = when (selected) {
            true -> QuackColor.Gray3
            else -> QuackColor.DuckieOrange
        },
    ),
    onClick = onClick,
)

/**
 * QuackToggleChip 을 구현합니다.
 * Button 의 역할보다는 Chip 의 역할이 더 크기에
 * Button 대신 Chip 네이밍을 사용했습니다.
 *
 * 선택 여부에 따라 텍스트 스타일과 배경 색상 그리고
 * 테두리 색상이 달라집니다.
 *
 * @param text chip 에 표시될 텍스트
 * @param selected chip 이 선택되었는지 여부
 * @param onClick chip 클릭 시 호출될 콜백
 */
@Composable
public fun QuackToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
): Unit = QuackBasicButton(
    shape = QuackChipShape,
    text = text,
    textStyle = QuackTextStyle.Body2.change(
        color = when (selected) {
            true -> QuackColor.Gray2
            else -> QuackColor.DuckieOrange
        },
        weight = FontWeight.Medium,
    ),
    textPadding = QuackChipTextPadding,
    backgroundColor = when (selected) {
        true -> QuackColor.Gray3
        else -> QuackColor.White
    },
    border = QuackBorder(
        color = when (selected) {
            true -> QuackColor.Gray3
            else -> QuackColor.DuckieOrange
        },
    ),
    onClick = onClick,
)

/**
 * QuackButton 컴포넌트를 구성하는데 사용되는 Button 의 최하위 컴포넌트 입니다
 *
 * IME 애니메이션이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param width 버튼의 가로 길이. [Modifier] 을 그대로 받는게 아닌
 * 필요한 사이즈 정보만 받기 위해 [QuackWidth] 를 통해 값을 받습니다.
 * 덕키 디자인 시스템의 버튼 컴포넌트 경우는 기본값이 [QuackWidth.Wrap] 입니다.
 * @param height 버튼의 세로 길이. [Modifier] 을 그대로 받는게 아닌
 * 필요한 사이즈 정보만 받기 위해 [QuackHeight] 를 통해 값을 받습니다.
 * 덕키 디자인 시스템의 버튼 컴포넌트 경우는 기본값이 [QuackHeight.Wrap] 입니다.
 * @param elevation 버튼의 그림자 강도. 기본 값은 0 입니다.
 * 즉, 그림자가 표시되지 않습니다.
 * @param shape 버튼의 모양. 기본값은 [RectangleShape] 입니다.
 * @param leadingIcon 버튼의 텍스트 왼쪽에 표시할 아이콘.
 * null 이 들어오면 아이콘을 표시하지 않으며, 기본값은 null 입니다.
 * @param text 버튼에 표시될 텍스트
 * @param textStyle 버튼의 텍스트 스타일.
 * 덕키 디자인 시스템의 버튼 컴포넌트 경우는 기본값이 [QuackTextStyle.Subtitle] 입니다.
 * @param textPadding 버튼의 텍스트에 적용될 [PaddingValues].
 * 덕키 디자인 시스템의 경우 컴포넌트의 사이즈를 정적으로 고정시키지 않고,
 * 컴포넌트 안에 들어가는 요소의 사이즈에 따라 동적으로 사이즈를 결정합니다.
 * QuackButton 의 경우에는 안에 들어가는 요소가 텍스트가 됩니다.
 * 즉, 이 패딩 값에 따라 QuackButton 의 텍스트 컴포넌트 사이즈가 조정되므로
 * QuackButton 의 사이즈를 결정짓는 중요한 인자가 됩니다.
 * 기본값은 [NoPadding] 입니다. 즉, 텍스트 컴포넌트의 사이즈를 늘리지 않습니다.
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
 * @see Modifier.applyQuackBorder
 * @see QuackSurface
 */
@Composable
private fun QuackBasicButton(
    width: QuackWidth = QuackWidth.Wrap,
    height: QuackHeight = QuackHeight.Wrap,
    elevation: Dp = 0.dp,
    shape: Shape = RectangleShape,
    leadingContent: (@Composable () -> Unit)? = null,
    text: String,
    textStyle: QuackTextStyle,
    textPadding: PaddingValues = NoPadding,
    backgroundColor: QuackColor,
    border: QuackBorder? = null,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = Modifier
            .imePadding()
            .applyQuackSize(
                width = width,
                height = height,
            )
            .then(
                other = modifier,
            ),
        backgroundColor = backgroundColor,
        border = border,
        elevation = elevation,
        shape = shape,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = when (enabled) {
            true -> onClick
            else -> null
        },
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            QuackImage(
                src = leadingIcon,
                overrideSize = QuackButtonIconSize,
                tint = QuackButtonIconTint,
            )
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = textPadding,
                ),
                text = text,
                style = textStyle.change(
                    textAlign = TextAlign.Center, // 버튼은 항상 Center 를 요구함
                ),
                singleLine = true, // 버튼은 항상 SingleLine 을 요구함
            )
        }
    }
}
