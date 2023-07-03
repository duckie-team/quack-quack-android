/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.plugin.interceptor

import com.github.takahirom.roborazzi.RoborazziRule.Ignore as NoSnapshot
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RoborazziRule
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.matchers.maps.shouldMatchExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.TagStyleMarker
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotName
import team.duckie.quackquack.util.compose.snapshot.test.snapshotPath

@RunWith(AndroidJUnit4::class)
class QuackInterceptorPluginTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @get:Rule
  val roborazzi = RoborazziRule(
    composeRule = compose,
    captureRoot = compose.onRoot(),
    options = RoborazziRule.Options(
      outputFileProvider = { description, _, fileExtension ->
        val snapshotName = description.getAnnotation(SnapshotName::class.java)?.name ?: description.methodName
        snapshotPath(
          domain = "QuackInterceptorPlugin",
          snapshotName = snapshotName,
          isGif = fileExtension == "gif",
        )
      },
    ),
  )

  @SnapshotName("QuackTagRadiusStyleIntercepted")
  @Test
  fun `style intercept works fine`() {
    val map = mutableMapOf<String, Any?>()

    var interceptedStyle: QuackTagStyle<TagStyleMarker>? = null
    val interceptedRadius = Int.MAX_VALUE.dp

    compose.setContent {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackInterceptorPlugin.DesignToken { componentName, componentDesignToken, componentModifier, _ ->
            map["componentName"] = componentName
            map["componentDesignToken"] = componentDesignToken
            map["componentModifier"] = componentModifier

            (if (componentName == "QuackTag") {
              @Suppress("UNCHECKED_CAST")
              componentDesignToken as QuackTagStyle<TagStyleMarker>
              object : QuackTagStyle<TagStyleMarker> by componentDesignToken {
                override val radius = interceptedRadius
                override val colors =
                  componentDesignToken.colors.copy(
                    backgroundColor = QuackColor.Gray3,
                    contentColor = QuackColor.Black,
                  )
              }
            } else {
              componentDesignToken
            }).also { intercepttedResult ->
              @Suppress("UNCHECKED_CAST")
              interceptedStyle = intercepttedResult as QuackTagStyle<TagStyleMarker>
            }
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

    map.shouldMatchExactly(
      "componentName" to { it shouldBe "QuackTag" },
      "componentDesignToken" to { it.toString() shouldBe QuackTagStyle.Filled.toString() },
      "componentModifier" to { it shouldBe Modifier },
    )
    interceptedStyle.shouldNotBeNull().radius shouldBe interceptedRadius
  }

  @NoSnapshot
  @Test
  fun InterceptedStyleTypeExceptionMessage() {
    shouldThrowWithMessage<IllegalStateException>(InterceptedStyleTypeExceptionMessage) {
      compose.setContent {
        QuackTheme(
          plugins = rememberQuackPlugins {
            +QuackInterceptorPlugin.DesignToken { _, _, _, _ -> Unit }
          },
        ) {
          QuackTag(
            text = "",
            style = QuackTagStyle.Filled,
            onClick = {},
          )
        }
      }
    }
  }
}
