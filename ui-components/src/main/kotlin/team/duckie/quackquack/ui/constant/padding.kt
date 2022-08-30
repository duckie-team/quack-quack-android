/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [padding.kt] created by Ji Sungbin on 22. 8. 31. 오전 5:36
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.constant

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

/**
 * 패딩이 없는 상태를 도메인적으로 나타내기 위해 사용됩니다.
 */
@Stable
internal val NoPadding = PaddingValues(
    all = 0.dp,
)
