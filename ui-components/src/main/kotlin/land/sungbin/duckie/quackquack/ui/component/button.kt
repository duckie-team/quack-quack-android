/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [button.kt] created by Ji Sungbin on 22. 8. 14. 오후 6:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import land.sungbin.duckie.quackquack.ui.animation.quackTween
import land.sungbin.duckie.quackquack.ui.color.QuackColor
import land.sungbin.duckie.quackquack.ui.typography.QuackTextStyle

@Composable
fun QuackButton(
    modifier: Modifier = Modifier,
    backgroundColor: QuackColor,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    radius: Dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified, // vs 그냥 Color 사용 (고민중)
    text: String,
    textStyle: QuackTextStyle,
    onClick: () -> Unit,
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor.value,
        animationSpec = quackTween(),
    )

    QuackSurface(
        modifier = modifier.animateContentSize(
            animationSpec = quackTween(),
        ),
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
        // TODO: add text change animation, migration to QuackText
        Text(
            text = text,
            style = textStyle.toComposeStyle(),
        )
    }
}