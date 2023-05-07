/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

/**
 * 0으로 고정된 [Constraints]을 나타냅니다.
 */
@Stable
public val Constraints.Companion.Zero: Constraints
    get() = fixed(width = 0, height = 0)

/**
 * 주어진 사이즈로 고정된 [Constraints]를 생성합니다.
 *
 * 각각 주어진 사이즈로 min과 max를 모두 설정하는 [Constraints]의 유틸 함수 입니다.
 * 만약 인자로 주어진 값이 `null`이라면 min과 max에 해당하는 원래의 값을 그대로 사용합니다.
 *
 * @param width [Constraints]의 고정된 width 사이즈
 * @param height [Constraints]의 고정된 height 사이즈
 *
 * @see Constraints
 */
@Stable
public fun Constraints.fixedCopy(width: Int? = null, height: Int? = null): Constraints {
    return Constraints(
        minWidth = width ?: this.minWidth,
        maxWidth = width ?: this.maxWidth,
        minHeight = height ?: this.minHeight,
        maxHeight = height ?: this.maxHeight,
    )
}

/**
 * 주어진 [Placeable]에서 width 값을 가져오거나, 유효하지 않을 경우 0을 반환합니다.
 */
@Stable
public fun Placeable?.widthOrZero(): Int = this?.width ?: 0

/**
 * 주어진 [Placeable]에서 height 값을 가져오거나, 유효하지 않을 경우 0을 반환합니다.
 */
@Stable
public fun Placeable?.heightOrZero(): Int = this?.height ?: 0
