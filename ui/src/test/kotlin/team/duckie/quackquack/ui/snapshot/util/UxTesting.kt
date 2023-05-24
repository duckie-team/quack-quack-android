/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration

const val LargestFontScale = 2f // Maximum font scale based on Galaxy A31
const val LargestDeviceQualifier =
  "w8000dp-h8000dp-normal-long-notround-any-420dpi-keyshidden-nonav" // copied form Pixel7 qualifier

@SuppressLint("ComposableNaming")
@Composable
fun withIncreaseFontScale(fontScale: Float, content: @Composable () -> Unit) {
  val testConfiguration = LocalConfiguration.current.apply {
    this.fontScale = fontScale
  }

  CompositionLocalProvider(
    LocalConfiguration provides testConfiguration,
    content = content,
  )
}
