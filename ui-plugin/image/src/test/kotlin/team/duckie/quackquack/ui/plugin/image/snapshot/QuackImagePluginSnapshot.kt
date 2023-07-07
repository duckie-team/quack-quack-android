/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.image.snapshot

import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.plugin.image.common.QuackImageCoilBuilderIntercepter
import team.duckie.quackquack.ui.plugin.quackPluginLocal
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class QuackImagePluginSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("QuackImagePlugin")

  @Test
  fun OrangeStringIntercepted() {
    captureRoboImage(snapshotPath()) {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackImageCoilBuilderIntercepter()
        },
      ) {
        QuackImage(
          modifier = Modifier.quackPluginLocal(30f),
          src = "Hello, World!",
          contentDescription = "orange string",
        )
      }
    }
  }
}
