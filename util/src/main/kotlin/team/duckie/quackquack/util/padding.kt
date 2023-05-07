/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

/** 패딩이 없는 상태를 도메인적으로 나타내기 위해 사용됩니다. */
@Stable
public val NoPadding: PaddingValues = PaddingValues(all = 0.dp)
