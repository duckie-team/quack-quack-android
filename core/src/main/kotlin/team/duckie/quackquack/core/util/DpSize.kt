/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.core.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

/**
 * 고정된 사이즈를 갖는 [DpSize]를 반환합니다.
 *
 * @param all 고정된 사이즈
 */
public fun DpSize(all: Dp): DpSize {
    return DpSize(width = all, height = all)
}
