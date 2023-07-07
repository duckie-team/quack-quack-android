/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.image.uitest

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.maps.shouldMatchExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.plugin.QuackPluginLocal
import team.duckie.quackquack.ui.plugin.image.common.QuackImageCoilBuilderIntercepter
import team.duckie.quackquack.ui.plugin.quackPluginLocal
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins

@RunWith(AndroidJUnit4::class)
class QuackImagePluginTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun `QuackImage plugin intercept test`() {
    val map = mutableMapOf<String, Any?>()

    var context: Context? = null
    val src = "Hello, World!"
    val contentDescription = "orange string"
    val fontSize = 30f

    compose.setContent {
      context = LocalContext.current

      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackImageCoilBuilderIntercepter(map)
        },
      ) {
        QuackImage(
          modifier = Modifier.quackPluginLocal(fontSize),
          src = src,
          contentDescription = contentDescription,
        )
      }
    }

    map.shouldMatchExactly(
      "context" to { it shouldBeSameInstanceAs context },
      "src" to { it shouldBe src },
      "contentDescription" to { it shouldBe contentDescription },
      "quackPluginLocal" to {
        it.shouldBeInstanceOf<QuackPluginLocal>().value shouldBe fontSize
      },
    )
  }
}
