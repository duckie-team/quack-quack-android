/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.util.Empty

// wip
class TextFieldTest {
  @get:Rule
  val compose = createComposeRule()

  @Test
  fun QuackFilledTextField_backgroundColorGetter() {
    var currentColor = QuackColor.Unspecified

    compose.setContent {
      QuackTheme {
        QuackFilledTextField(
          value = String.Empty,
          onValueChange = {},
          style = QuackTextFieldStyle.FilledLarge {
            colors = colors.copy(
              backgroundColorGetter = { _, focus ->
                when (focus) {
                  is FocusInteraction.Focus -> QuackColor.Success
                  is FocusInteraction.Unfocus -> QuackColor.Alert
                  else -> QuackColor.Dimmed
                }.also { currentColor = it }
              }
            )
          },
        )
      }
    }

    val textfield = compose.onNodeWithTag("textfield", useUnmergedTree = true)
    compose.waitForIdle()

    currentColor shouldBe QuackColor.Dimmed

    textfield.performClick()
    textfield.assertIsFocused()
  }
}
