/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.compose.snapshot.test

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import org.robolectric.RuntimeEnvironment

public const val LargestFontScale: Float = 2f // Maximum font scale based on Galaxy A31

@SuppressLint("ComposableNaming")
@Composable
public fun withIncreaseFontScale(fontScale: Float, content: @Composable () -> Unit) {
  RuntimeEnvironment.setFontScale(fontScale)

  val testConfiguration = LocalConfiguration.current.apply {
    this.fontScale = fontScale
  }

  CompositionLocalProvider(
    LocalConfiguration provides testConfiguration,
    content = content,
  )
}
