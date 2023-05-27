/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.image.gif

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RoborazziRule
import java.io.File
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins

@Ignore("GIF 녹화 안됨")
@RunWith(AndroidJUnit4::class)
class QuackImageGifPluginTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @get:Rule
  val roborazzi = RoborazziRule(
    composeRule = compose,
    captureRoot = compose.onRoot(),
    options = RoborazziRule.Options(
      captureType = RoborazziRule.CaptureType.Gif,
      outputFileProvider = { description, _, fileExtension ->
        val sdkLevel = description.methodName.split("sdk").last()
        File("src/test/snapshots/QuackImageGifPlugin/rainbow-sdk${sdkLevel}.$fileExtension").also { file ->
          file.parentFile!!.mkdirs()
        }
      },
    ),
  )

  @Config(sdk = [28])
  @Test
  fun `QuackImageGifPlugin gif snapshot sdk28`() {
    `QuackImageGifPlugin gif snapshot`()
  }

  @Config(sdk = [23])
  @Test
  fun `QuackImageGifPlugin gif snapshot sdk23`() {
    `QuackImageGifPlugin gif snapshot`()
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
