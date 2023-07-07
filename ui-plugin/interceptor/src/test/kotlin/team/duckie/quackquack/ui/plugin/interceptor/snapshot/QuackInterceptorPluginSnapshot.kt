/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)
@file:Suppress("UNCHECKED_CAST")

package team.duckie.quackquack.ui.plugin.interceptor.snapshot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.TagStyleMarker
import team.duckie.quackquack.ui.plugin.interceptor.QuackInterceptorPlugin
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class QuackInterceptorPluginSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("QuackInterceptorPlugin")

  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun QuackTagBackgroundColorIntercepted() {
    compose.setContent {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackInterceptorPlugin.DesignToken { componentName, componentDesignToken, componentModifier, _ ->
            (if (componentName == "QuackTag") {
              componentDesignToken as QuackTagStyle<TagStyleMarker>
              object : QuackTagStyle<TagStyleMarker> by componentDesignToken {
                override val radius = Int.MAX_VALUE.dp
                override val colors =
                  componentDesignToken.colors.copy(
                    backgroundColor = QuackColor.Gray3,
                    contentColor = QuackColor.Black,
                  )
              }
            } else {
              componentDesignToken
            })
          }
        },
      ) {
        QuackTag(
          text = "Intercepted Tag",
          style = QuackTagStyle.Filled,
          onClick = {},
        )
      }
    }

    compose.onRoot().captureRoboImage(snapshotPath())
  }
}
