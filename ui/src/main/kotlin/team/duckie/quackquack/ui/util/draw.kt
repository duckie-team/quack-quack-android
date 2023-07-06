/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.graphics.drawscope.DrawScope

/** Issue drawing commands to be executed after the layout content is drawn. */
@Stable
public fun CacheDrawScope.onDrawFront(block: DrawScope.() -> Unit): DrawResult =
  onDrawWithContent {
    drawContent()
    block()
  }
