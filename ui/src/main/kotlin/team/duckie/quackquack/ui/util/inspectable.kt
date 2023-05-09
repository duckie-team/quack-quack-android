/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled

/**
 * 주어진 [Modifier]를 [InspectableModifier]로 래핑합니다.
 *
 * [isDebugInspectorInfoEnabled]가 true일 때만 [debugInspectorInfo]가 적용됩니다.
 */
@Stable
public fun Modifier.wrappedDebugInspectable(debugInspectorInfo: InspectorInfo.() -> Unit): Modifier {
  if (!isDebugInspectorInfoEnabled) return this

  val begin = InspectableModifier(debugInspectorInfo)
  return Modifier
    .then(begin)
    .then(this)
    .then(begin.end)
}
