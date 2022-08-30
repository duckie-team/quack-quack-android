/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [surface.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:02
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.modifier.surface

@Composable
internal fun QuackSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: QuackColor = QuackColor.Unspecified,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: (() -> Unit)? = null,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .surface(
                shape = shape,
                backgroundColor = backgroundColor,
                border = border,
                elevation = elevation,
            )
            .quackClickable(
                onClick = onClick,
                rippleEnabled = rippleEnabled,
                rippleColor = rippleColor,
            )
            .animateContentSize(
                animationSpec = quackTween(),
            ),
        propagateMinConstraints = true,
        contentAlignment = contentAlignment,
        content = content,
    )
}
