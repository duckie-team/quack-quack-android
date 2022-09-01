/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [button.kt] created by Ji Sungbin on 22. 8. 14. 오후 6:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.NoPadding
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.typography.QuackTextStyle
import team.duckie.quackquack.ui.typography.animateQuackTextStyleAsState

@Stable
private val QuackLargeButtonShape = RoundedCornerShape(
    size = 8.dp,
)

@Stable
private val QuackMediumButtonShape = RoundedCornerShape(
    size = 12.dp,
)

// 2개 스팩이 동일함, 하지만 도메인적 의미를 위해 분리
@Stable
private val QuackSmallButtonShape = QuackLargeButtonShape

@Stable
private val QuackChipShape = RoundedCornerShape(
    size = 18.dp,
)

@Stable
private val QuackLargeButtonTextPadding = PaddingValues(
    vertical = 13.dp,
)

@Stable
private val QuackLargeButton40TextPadding = PaddingValues(
    vertical = 11.dp,
)

@Stable
private val QuackMediumButtonTextPadding = PaddingValues(
    horizontal = 62.dp,
    vertical = 11.dp,
)

@Stable
private val QuackSmallButtonTextPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)

@Stable
private val QuackChipTextPadding = PaddingValues(
    horizontal = 8.dp,
    vertical = 4.dp,
)

@Stable
private fun quackButtonStandardBackgroundColorFor(enabled: Boolean) = when (enabled) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.Gray2
}

@Composable
@NonRestartableComposable
fun QuackLargeButton(
    text: String,
    active: Boolean = true,
    // TODO: imeAnimation: Boolean = false,
    onClick: () -> Unit,
) = QuackBasicButton(
    // TODO: imeAnimation; modifier = Modifier,
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    text = text,
    textPadding = QuackLargeButtonTextPadding,
    textStyle = QuackTextStyle.Subtitle.change(
        newColor = QuackColor.White,
    ),
    backgroundColor = quackButtonStandardBackgroundColorFor(
        enabled = active,
    ),
    onClick = onClick,
)

@Composable
@NonRestartableComposable
fun QuackLargeWhiteButton(
    text: String,
    leadingIcon: QuackIcon? = null,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    leadingIcon = leadingIcon,
    text = text,
    textStyle = QuackTextStyle.Subtitle.change(
        newColor = QuackColor.White,
    ),
    textPadding = QuackLargeButtonTextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = QuackColor.Gray3,
    ),
    onClick = onClick,
)

@Composable
@NonRestartableComposable
fun QuackLarge40WhiteButton(
    text: String,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    text = text,
    textStyle = QuackTextStyle.Subtitle.change(
        newColor = QuackColor.White,
    ),
    textPadding = QuackLargeButton40TextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = QuackColor.Gray3,
    ),
    onClick = onClick,
)

@Composable
@NonRestartableComposable
fun QuackMediumBorderToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Wrap,
    shape = QuackMediumButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        newColor = when (selected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Black
        },
        newWeight = when (selected) {
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

@Composable
@NonRestartableComposable
fun QuackSmallButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Wrap,
    shape = QuackSmallButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        newColor = QuackColor.White,
    ),
    textPadding = QuackSmallButtonTextPadding,
    backgroundColor = quackButtonStandardBackgroundColorFor(
        enabled = enabled,
    ),
    onClick = onClick,
)

@Composable
@NonRestartableComposable
fun QuackSmallBorderToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Wrap,
    shape = QuackSmallButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        newColor = when (selected) {
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

@Composable
@NonRestartableComposable
fun QuackToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Wrap,
    shape = QuackChipShape,
    text = text,
    textStyle = QuackTextStyle.Body2.change(
        newColor = when (selected) {
            true -> QuackColor.Gray2
            else -> QuackColor.DuckieOrange
        },
        newWeight = FontWeight.Medium,
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
 * QuackButton 컴포넌트를 구성하는데 사용되는 Button 의 최하위 컴포넌트 입니다.
 */
@Composable
private fun QuackBasicButton(
    modifier: Modifier = Modifier,
    width: QuackWidth,
    height: QuackHeight = QuackHeight.Wrap,
    elevation: Dp = 0.dp,
    shape: Shape = RectangleShape,
    leadingIcon: QuackIcon? = null,
    text: String,
    textStyle: QuackTextStyle = QuackTextStyle.Subtitle,
    textPadding: PaddingValues = NoPadding,
    backgroundColor: QuackColor,
    border: QuackBorder? = null,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: () -> Unit,
) {
    val textStyleAnimation by animateQuackTextStyleAsState(
        targetValue = textStyle,
    )
    QuackSurface(
        modifier = Modifier
            .applyQuackSize(
                width = width,
                height = height,
            )
            // 컴포넌트 사이즈와 마진이 먼저 적용돼야 함
            .then(
                other = modifier,
            ),
        backgroundColor = backgroundColor,
        border = border,
        elevation = elevation,
        shape = shape,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (leadingIcon != null) {
                QuackImage(
                    icon = leadingIcon,
                    tint = QuackColor.Gray1,
                )
            }
            // 아이콘 - 텍스트 사이 간격 없음
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = textPadding,
                ),
                text = text,
                style = textStyleAnimation,
            )
        }
    }
}
