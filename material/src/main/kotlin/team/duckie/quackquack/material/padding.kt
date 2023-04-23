/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
public interface QuackPadding {
    public val horizontal: Dp
    public val vertical: Dp

    @Stable
    public fun asPaddingValues(): PaddingValues {
        return PaddingValues(horizontal = horizontal, vertical = vertical)
    }

    @Stable
    public fun copy(
        horizontal: Dp = this.horizontal,
        vertical: Dp = this.vertical,
    ): QuackPadding {
        return QuackPadding(horizontal = horizontal, vertical = vertical)
    }
}

@Stable
public fun QuackPadding(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): QuackPadding {
    return object : QuackPadding {
        override val horizontal: Dp = horizontal
        override val vertical: Dp = vertical
    }
}
