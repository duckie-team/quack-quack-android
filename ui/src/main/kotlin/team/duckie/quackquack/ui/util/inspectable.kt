/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.ui.platform.debugInspectorInfo as DebugInspectorInfo
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.InspectorInfo

@Stable
public fun Modifier.wrappedDebugInspectable(debugInspectorInfo: InspectorInfo.() -> Unit): Modifier {
    val begin = InspectableModifier { DebugInspectorInfo(debugInspectorInfo) }
    return Modifier
        .then(begin)
        .then(this)
        .then(begin.end)
}
