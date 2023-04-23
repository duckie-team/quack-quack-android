/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration

@SuppressLint("ComposableNaming")
@Composable
@ReadOnlyComposable
public inline fun <T> currentFontScale(content: (fontScale: Float) -> T): T {
    val configration = LocalConfiguration.current
    return content(configration.fontScale)
}
