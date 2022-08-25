/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TextFieldTest.kt] created by Ji Sungbin on 22. 8. 25. 오전 12:11
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.component.QuackIcon
import team.duckie.quackquack.ui.component.QuackIconTextField
import team.duckie.quackquack.ui.component.QuackTextField
import team.duckie.quackquack.ui.component.QuackTrailingTextField

@RunWith(AndroidJUnit4::class)
class TextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun quack_text_field_input_text() {
        val someText = "text for testing"
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            QuackTextField(
                text = text,
                onTextChanged = { text = it },
                placeholder = TEST_TAG
            )
        }
        composeTestRule.onNodeWithText(TEST_TAG).performTextInput(someText)

        composeTestRule.onNodeWithText(someText).assertTextContains(someText)
    }

    @Test
    fun quack_icon_text_field_input_text() {
        val someText = "text for testing"
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            QuackIconTextField(
                text = text,
                onTextChanged = { text = it },
                icon = QuackIcon.Search,
                placeholder = TEST_TAG,
            )
        }
        composeTestRule.onNodeWithText(TEST_TAG).performTextInput(someText)

        composeTestRule.onNodeWithText(someText).assertTextContains(someText)
    }

    @Test
    fun quack_trailing_text_field_input_text() {
        val someText = "text for testing"
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            QuackTrailingTextField(
                text = text,
                onTextChanged = { text = it },
                buttonText = "등록",
                placeholder = TEST_TAG,
                onClickButton = {}
            )
        }
        composeTestRule.onNodeWithText(TEST_TAG).onChild().assertIsNotEnabled()
        composeTestRule.onNodeWithText(TEST_TAG).performTextInput(someText)

        composeTestRule.onNodeWithText(someText).assertTextContains(someText)
        composeTestRule.onNodeWithText(someText).onChild().assertIsEnabled()
        composeTestRule.onNodeWithText(someText).onChild().performClick()
    }

    companion object {
        var TEST_TAG = "TEST_TAG"
    }
}
