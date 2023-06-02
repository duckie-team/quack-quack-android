/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon

import kotlin.collections.List as ____KtList
import androidx.compose.ui.graphics.vector.ImageVector
import team.duckie.quackquack.material.icon.quackicon.AllIcons
import team.duckie.quackquack.material.icon.quackicon.Colored
import team.duckie.quackquack.material.icon.quackicon.Filled
import team.duckie.quackquack.material.icon.quackicon.Outlined

public object QuackIcon

private var __AllIcons: ____KtList<ImageVector>? = null

public val QuackIcon.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons = Colored.AllIcons + Outlined.AllIcons + Filled.AllIcons + listOf()
    return __AllIcons!!
  }
