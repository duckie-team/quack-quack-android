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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.ints.shouldBeZero
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.common.setQuackContent
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@RunWith(AndroidJUnit4::class)
class ButtonTest {
  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  // `enabled`가 false일 때는 `onClick` 이벤트가 작동하면 안됨
  @Test
  fun onClick_shouldnt_works_when_enabled_is_false() {
    var clicked = 0

    compose.setQuackContent {
      QuackButton(
        enabled = false,
        style = QuackButtonStyle.PrimaryLarge,
        text = "button",
        onClick = { clicked++ },
      )
    }

    val button = compose.onNodeWithTag("button")
    button.performClick()

    compose.runOnIdle { clicked.shouldBeZero() }
  }
}
