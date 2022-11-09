/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

internal fun Modifier.noRippleClickable(
    onClick: () -> Unit,
) = composed {
    clickable(
        onClick = onClick,
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
    )
}
