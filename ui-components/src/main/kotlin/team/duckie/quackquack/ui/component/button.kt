/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [button.kt] created by Ji Sungbin on 22. 8. 14. 오후 6:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("ModifierParameter")

package team.duckie.quackquack.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.common.runIf
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.constant.NoPadding
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.typography.QuackTextStyle

@Composable
fun QuackLargeButton(
    enabled: Boolean = true,
    text: String,
    imeAnimation: Boolean = false,
    onClick: () -> Unit,
) {
    QuackBasicButton(
        width = QuackWidth.Fill,
        height = QuackHeight.Custom(
            height = 44.dp,
        ),
        imeAnimation = imeAnimation,
        backgroundColor = when (enabled) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Greyish
        },
        radius = 8.dp,
        text = text,
        textStyle = QuackTextStyle.M1420.changeColor(
            newColor = QuackColor.White,
        ),
        onClick = onClick
    )
}

@Composable
internal fun QuackBasicButton(
    width: QuackWidth,
    height: QuackHeight,
    margin: PaddingValues = NoPadding,
    imeAnimation: Boolean = false,
    backgroundColor: QuackColor = QuackColor.Unspecified,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    radius: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    text: String,
    textStyle: QuackTextStyle = QuackTextStyle.M1420,
    textPadding: PaddingValues = NoPadding,
    onClick: () -> Unit,
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor.value,
        animationSpec = quackTween(),
    )

    QuackSurface(
        modifier = Modifier
            .applyQuackSize(
                width = width,
                height = height,
            )
            .padding(
                paddingValues = margin,
            )
            // TODO: ime 애니메이션에 따른 컴포넌트 사이즈 조정 애니메이션 지원
            .runIf(imeAnimation) {
                imePadding()
            },
        border = border,
        elevation = elevation,
        shape = RoundedCornerShape(
            size = radius,
        ),
        backgroundColor = animatedBackgroundColor,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor.value,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues = textPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            QuackText(
                text = text,
                style = textStyle,
            )
        }
    }
}
