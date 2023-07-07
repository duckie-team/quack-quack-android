/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.image.gif.snapshot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.plugin.image.gif.QuackImageGifPlugin
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotName
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@Ignore("캡처가 정상적으로 안됨")
@RunWith(AndroidJUnit4::class)
class QuackImageGifPluginSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("QuackImageGifPlugin")

  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @SnapshotName("sdk-28")
  @Config(sdk = [28])
  @Test
  fun `QuackImageGifPlugin gif snapshot sdk28`() {
    `QuackImageGifPlugin gif snapshot`()
    compose.onRoot().captureRoboImage(snapshotPath())
  }

  @SnapshotName("sdk-23")
  @Config(sdk = [23])
  @Test
  fun `QuackImageGifPlugin gif snapshot sdk23`() {
    `QuackImageGifPlugin gif snapshot`()
    compose.onRoot().captureRoboImage(snapshotPath())
  }

  private fun `QuackImageGifPlugin gif snapshot`() {
    val rainbow = compose.activity.assets.open("colors-rainbow.gif")
    compose.setContent {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackImageGifPlugin
        },
      ) {
        QuackImage(src = rainbow)
      }
    }
  }
}
