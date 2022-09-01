/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TypographyTest.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:15
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.typography.QuackHeadLine1

@RunWith(AndroidJUnit4::class)
class TypographyTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `head_line_1_test`() {
        composeTestRule.setContent {
            QuackHeadLine1(text = TEST_TEXT)
        }

        composeTestRule.onNodeWithText(TEST_TEXT).assertTextColor(
            QuackColor.Black
        )
    }

    companion object {
        const val TEST_TEXT = "TEST_TEXT"
    }
}
