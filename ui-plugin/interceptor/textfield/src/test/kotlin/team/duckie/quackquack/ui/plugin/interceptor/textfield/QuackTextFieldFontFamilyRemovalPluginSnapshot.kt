/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.plugin.interceptor.textfield

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackFilledTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class QuackTextFieldFontFamilyRemovalPluginSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("QuackTextFieldFontFamilyRemovalPlugin")

  @Test
  fun DefaultTextField() {
    captureRoboImage(snapshotPath()) {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackTextFieldFontFamilyRemovalPlugin
        },
      ) {
        QuackDefaultTextField(
          value = "퍠꿻땗뷃휉퉶뷟퍯 <- 잘 보이니?",
          onValueChange = {},
          style = QuackTextFieldStyle.Default,
        )
      }
    }
  }

  @Test
  fun FilledTextField() {
    captureRoboImage(snapshotPath()) {
      QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackTextFieldFontFamilyRemovalPlugin
        },
      ) {
        QuackFilledTextField(
          value = "퍠꿻땗뷃휉퉶뷟퍯 <- 잘 보이니?",
          onValueChange = {},
          style = QuackTextFieldStyle.FilledLarge,
        )
      }
    }
  }
}
