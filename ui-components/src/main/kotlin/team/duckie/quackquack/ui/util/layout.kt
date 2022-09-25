/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

internal val IntrinsicMeasurable.layoutId
    get() = (parentData as? LayoutIdParentData)?.layoutId

internal val ZeroConstraints = Constraints.fixed(
    width = 0,
    height = 0,
)

/**
 * 주어진 [Placeable] 에서 width 값을 가져오거나, 유효하지 않을 경우
 * 0 을 반환합니다.
 *
 * @receiver width 값을 가져올 [Placeable]
 * @return receiver 로 주어진 [Placeable] 이 유효하다면 해당 [Placeable] 의
 * width 값을 반환하고, 유효하지 않다면 0 을 반환합니다.
 */
internal fun Placeable?.widthOrZero() = this?.width ?: 0

/**
 * 주어진 [Placeable] 에서 height 값을 가져오거나, 유효하지 않을 경우
 * 0 을 반환합니다.
 *
 * @receiver width 값을 가져올 [Placeable]
 * @return receiver 로 주어진 [Placeable] 이 유효하다면 해당 [Placeable] 의
 * height 값을 반환하고, 유효하지 않다면 0 을 반환합니다.
 */
internal fun Placeable?.heightOrZero() = this?.height ?: 0
