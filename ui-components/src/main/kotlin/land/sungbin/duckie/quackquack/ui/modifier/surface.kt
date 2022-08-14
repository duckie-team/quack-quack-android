/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [surface.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:00
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.ui.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import land.sungbin.duckie.quackquack.common.runIf

@Stable
internal fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    elevation: Dp,
) = this
    .shadow(
        elevation = elevation,
        shape = shape,
        clip = false,
    )
    .runIf(border != null) {
        border(
            border = border!!,
            shape = shape,
        )
    }
    .background(
        color = backgroundColor,
        shape = shape,
    )
    .clip(
        shape = shape,
    )