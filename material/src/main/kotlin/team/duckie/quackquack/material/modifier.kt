/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.foundation.background
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
public fun Modifier.quackSurface(
    shape: Shape = RectangleShape,
    backgroundColor: QuackColor = QuackColor.Unspecified,
    border: QuackBorder? = null,
    elevation: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: (() -> Unit)? = null,
): Modifier {
    return this
        .shadow(
            elevation = elevation,
            shape = shape,
            clip = false,
        )
        .clip(shape)
        .background(
            color = backgroundColor.value,
            shape = shape,
        )
        .quackClickable(
            onClick = onClick,
            rippleEnabled = rippleEnabled,
            rippleColor = rippleColor,
        )
        .quackBorder(
            border = border,
            shape = shape,
        )
}
