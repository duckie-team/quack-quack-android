/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [surface.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:28
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [surface.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:14
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.common.runIf
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.modifier.surface

@Composable
internal fun QuackSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: Color = Color.Unspecified,
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
            .runIf(onClick != null) {
                clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = rippleColor,
                    ).takeIf {
                        rippleEnabled
                    },
                    role = Role.Button,
                    onClick = onClick!!,
                )
            }
            .animateContentSize(
                animationSpec = quackTween(),
            ),
        propagateMinConstraints = true,
        contentAlignment = contentAlignment,
        content = content,
    )
}
