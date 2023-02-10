/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

/**
 * [IntrinsicMeasurable] 에서 컴포저블의 layoutId 를 찾습니다.
 */
internal val IntrinsicMeasurable.layoutId
    get() = (parentData as? LayoutIdParentData)?.layoutId

/**
 * 0 으로 고정된 [Constraints] 을 나타냅니다.
 */
internal val Constraints.Companion.Zero
    get() = fixed(
        width = 0,
        height = 0,
    )

/**
 * 주어진 사이즈로 고정된 [Constraints] 를 생성합니다.
 *
 * 각각 주어진 사이즈로 min 과 max 를 모두 설정하는 [Constraints] 의
 * 유틸 함수 입니다. 만약 인자로 주어진 값이 null 이라면
 * min 과 max 에 맞는 원래의 값을 그대로 사용합니다.
 *
 * @param width [Constraints] 의 고정된 width 사이즈
 * @param height [Constraints] 의 고정된 height 사이즈
 * @return 인자로 주어진 고정된 사이즈를 갖는 [Constraints]
 *
 * @see Constraints
 */
internal fun Constraints.fixedCopy(
    width: Int? = null,
    height: Int? = null,
) = Constraints(
    minWidth = width ?: this.minWidth,
    maxWidth = width ?: this.maxWidth,
    minHeight = height ?: this.minHeight,
    maxHeight = height ?: this.maxHeight,
)

/**
 * 주어진 [Placeable] 에서 width 값을 가져오거나, 유효하지 않을 경우
 * 0 을 반환합니다.
 *
 * @receiver width 값을 가져올 [Placeable]
 *
 * @return receiver 로 주어진 [Placeable] 이 유효하다면 해당 [Placeable] 의
 * width 값을 반환하고, 유효하지 않다면 0 을 반환합니다.
 */
internal fun Placeable?.widthOrZero() = this?.width ?: 0

/**
 * 주어진 [Placeable] 에서 height 값을 가져오거나, 유효하지 않을 경우
 * 0 을 반환합니다.
 *
 * @receiver width 값을 가져올 [Placeable]
 *
 * @return receiver 로 주어진 [Placeable] 이 유효하다면 해당 [Placeable] 의
 * height 값을 반환하고, 유효하지 않다면 0 을 반환합니다.
 */
internal fun Placeable?.heightOrZero() = this?.height ?: 0
