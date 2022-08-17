/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [size.kt] created by Ji Sungbin on 22. 8. 15. 오전 1:15
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.constant

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
internal sealed class QuackWidth {
    object Fill : QuackWidth()
    object Wrap : QuackWidth()

    @Immutable
    class Custom(val width: Dp) : QuackWidth()
}

@Immutable
internal sealed class QuackHeight {
    object Fill : QuackHeight()
    object Wrap : QuackHeight()

    @Immutable
    class Custom(val height: Dp) : QuackHeight()
}
