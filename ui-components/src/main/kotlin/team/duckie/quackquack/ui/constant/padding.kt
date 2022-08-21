/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [padding.kt] created by Ji Sungbin on 22. 8. 15. 오전 12:06
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.constant

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

@Stable
internal val NoPadding = PaddingValues(
    all = 0.dp,
)

@Stable
internal val SimpleTagPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)

@Stable
internal val IconTagPadding = PaddingValues(
    start = 16.dp,
    end = 10.dp,
    top = 8.dp,
    bottom = 8.dp,
)
