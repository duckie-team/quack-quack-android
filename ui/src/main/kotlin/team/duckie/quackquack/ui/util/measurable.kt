/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.util.fastFirstOrNull
import team.duckie.quackquack.util.fastFilter

/**
 * 주어진 [Measurable] 중 [LayoutIdParentData]가 [layoutId]와 일치하는
 * 첫 번째 [Measurable]을 반환합니다. 만약 일치하는 [Measurable]이 없다면
 * [IllegalStateException]이 발생합니다.
 */
@Stable
public operator fun List<Measurable>.get(layoutId: String): Measurable =
  fastFirstOrNull { measurable -> measurable.layoutId == layoutId }
    ?: error("No Measurable was found for the given layoutId($layoutId).")

/**
 * 주어진 [Measurable] 중 [LayoutIdParentData]가 [layoutId]와 일치하는
 * 첫 번째 [Measurable]을 반환합니다. 만약 일치하는 [Measurable]이 없다면
 * `null`을 반환합니다.
 */
@Stable
public fun List<Measurable>.fastGetByIdOrNull(layoutId: String): Measurable? =
  fastFirstOrNull { measurable -> measurable.layoutId == layoutId }

/**
 * 주어진 [Measurable] 중 [LayoutIdParentData]가 [layoutId]와 일치하는
 * [Measurable]들만 반환합니다.
 */
@Stable
public fun List<Measurable>.fastFilterById(layoutId: String): List<Measurable> =
  fastFilter { measurable -> measurable.layoutId == layoutId }
