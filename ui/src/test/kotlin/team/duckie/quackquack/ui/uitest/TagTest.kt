/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.uitest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.assertions.throwables.shouldThrowWithMessage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.QuackTagErrors.GrayscaleFlatStyleUnselectedState
import team.duckie.quackquack.ui.common.setQuackContent
import team.duckie.quackquack.ui.sugar.QuackGrayscaleFlatTag
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@RunWith(AndroidJUnit4::class)
class TagTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun GrayscaleFlatStyleDotAllowUnselectedState() {
    shouldThrowWithMessage<IllegalStateException>(GrayscaleFlatStyleUnselectedState) {
      compose.setQuackContent {
        QuackGrayscaleFlatTag("", selected = false) {}
      }
    }
  }
}
