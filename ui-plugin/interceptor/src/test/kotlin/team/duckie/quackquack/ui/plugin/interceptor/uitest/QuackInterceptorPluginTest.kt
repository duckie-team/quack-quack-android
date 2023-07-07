/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.plugin.interceptor.uitest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.plugin.interceptor.InterceptedStyleTypeExceptionMessage
import team.duckie.quackquack.ui.plugin.interceptor.QuackInterceptorPlugin
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@RunWith(AndroidJUnit4::class)
class QuackInterceptorPluginTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun InnerComponentsCanBeIntercepted() {
    val componentNames = mutableListOf<String>()

    compose.setContent {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackInterceptorPlugin.DesignToken { componentName, componentDesignToken, _, _ ->
            componentNames += componentName
            componentDesignToken
          }
        },
      ) {
        QuackTag(
          text = "",
          style = QuackTagStyle.Filled,
          onClick = {},
        )
      }
    }

    // 맹글링 제거 후 assertion
    componentNames.map { it.split("-").first() } shouldContainExactly listOf("QuackTag", "QuackText")
  }

  @Test
  fun InterceptedStyleTypeException() {
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
