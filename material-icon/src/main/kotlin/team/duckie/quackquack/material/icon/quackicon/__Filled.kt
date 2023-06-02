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
import team.duckie.quackquack.material.icon.quackicon.filled.Delete
import team.duckie.quackquack.material.icon.quackicon.filled.Home
import team.duckie.quackquack.material.icon.quackicon.filled.ImageEdit
import team.duckie.quackquack.material.icon.quackicon.filled.ImageSelect
import team.duckie.quackquack.material.icon.quackicon.filled.Message
import team.duckie.quackquack.material.icon.quackicon.filled.Search

public object FilledGroup

public val QuackIcon.Filled: FilledGroup
  get() = FilledGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val FilledGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons = listOf(Message, Search, Home, ImageEdit, Delete, ImageSelect)
    return __AllIcons!!
  }
