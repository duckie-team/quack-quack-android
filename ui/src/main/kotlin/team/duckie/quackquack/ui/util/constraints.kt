/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Constraints

/**
 * 주어진 [Constraints]의 min 필드를 0으로 설정하여 loose하게 반환합니다.
 *
 * @param height [Constraints.minHeight]을 loose 할지 여부
 * @param width [Constraints.minWidth]을 loose 할지 여부
 *
 * @return 주어진 조건에 맞게 loose해진 [Constraints]
 */
@Stable
public fun Constraints.asLoose(
    width: Boolean = false,
    height: Boolean = false,
): Constraints {
    return copy(
        minWidth = if (width) 0 else minWidth,
        minHeight = if (height) 0 else minHeight,
    )
}
