/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

/**
 * 고정된 사이즈를 갖는 [DpSize] 를 반환합니다.
 *
 * @param all 고정된 사이즈
 *
 * @return [all] 로 모든 사이즈가 지정된 [DpSize]
 */
@Stable
public fun DpSize(
    all: Dp,
): DpSize = DpSize(
    width = all,
    height = all,
)
