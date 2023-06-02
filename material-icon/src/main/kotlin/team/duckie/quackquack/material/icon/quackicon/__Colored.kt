/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.quackicon

import kotlin.collections.List as ____KtList
import androidx.compose.ui.graphics.vector.ImageVector
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.colored.Explore
import team.duckie.quackquack.material.icon.quackicon.colored.Home2
import team.duckie.quackquack.material.icon.quackicon.colored.Notice
import team.duckie.quackquack.material.icon.quackicon.colored.Profile
import team.duckie.quackquack.material.icon.quackicon.colored.Ranking
import team.duckie.quackquack.material.icon.quackicon.colored.Search2

public object ColoredGroup

public val QuackIcon.Colored: ColoredGroup
  get() = ColoredGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val ColoredGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons = listOf(Notice, Home2, Ranking, Explore, Search2, Profile)
    return __AllIcons!!
  }
