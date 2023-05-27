/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Immutable
@JvmInline
public value class QuackPluginLocal(public val value: Any?) : Modifier.Element

@Stable
public fun Modifier.quackPluginLocal(value: Any?): Modifier = this then QuackPluginLocal(value)
