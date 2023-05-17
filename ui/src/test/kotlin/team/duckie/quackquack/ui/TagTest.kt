/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.sugar.QuackGrayscaleFlatTag
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.ui.util.setQuackContent

@RunWith(AndroidJUnit4::class)
class TagTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun GrayscaleFlatStyleDotAllowUnselectedState() {
    val result = runCatching {
      compose.setQuackContent {
        QuackGrayscaleFlatTag("", selected = false) {}
      }
    }
    val exception = result.exceptionOrNull()

    assertTrue(exception is IllegalStateException)
    assertEquals(QuackTagErrors.GrayscaleFlatStyleUnselectedState, exception?.message)
  }
}
