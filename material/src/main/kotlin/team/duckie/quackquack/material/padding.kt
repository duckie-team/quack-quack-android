/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packFloats
import androidx.compose.ui.util.unpackFloat1
import androidx.compose.ui.util.unpackFloat2

// TOOD: 문서화
@Immutable
@JvmInline
public value class QuackPadding(@PublishedApi internal val packedValue: Long) {
    public val horizontal: Dp
        inline get() = unpackFloat1(packedValue).dp

    public val vertical: Dp
        inline get() = unpackFloat2(packedValue).dp

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

// TOOD: 문서화
@Stable
public fun QuackPadding(horizontal: Dp = 0.dp, vertical: Dp = 0.dp): QuackPadding {
    return QuackPadding(packFloats(horizontal.value, vertical.value))
}
