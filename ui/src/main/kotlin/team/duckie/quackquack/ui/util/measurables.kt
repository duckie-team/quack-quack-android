/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.util.fastFirstOrNull
import team.duckie.quackquack.util.fastFilter

@Stable
public operator fun List<Measurable>.get(layoutId: String): Measurable =
  fastFirstOrNull { measurable -> measurable.layoutId == layoutId }
    ?: error("No Measurable was found for the given layoutId($layoutId).")

@Stable
public fun List<Measurable>.getByIdOrNull(layoutId: String): Measurable? =
  fastFirstOrNull { measurable -> measurable.layoutId == layoutId }

@Stable
public fun List<Measurable>.filterById(layoutId: String): List<Measurable> =
  fastFilter { measurable -> measurable.layoutId == layoutId }
