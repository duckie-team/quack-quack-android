/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TagTest.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:41
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
import team.duckie.quackquack.ui.component.QuackBoldTag
import team.duckie.quackquack.ui.component.QuackIcon
import team.duckie.quackquack.ui.component.QuackIconTag

@RunWith(AndroidJUnit4::class)
class TagTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * TODO : Border, Padding 관련 테스트 코드 작성
     */
    @Test
    fun `enabled_simple_tag_tst`() {

        composeTestRule.setContent {
            QuackBoldTag(
                isSelected = false,
                text = TEST_TAG,
                onClick = {

                }
            )
        }

        composeTestRule.onNodeWithText(TEST_TAG)
            .assertHorizontallyCenterInRoot()
            .assertVerticallyCenterInRoot()
            .assertTextColor(QuackColor.Black.value)
    }
    @Test
    fun `default_icon_tag_test`() {
        composeTestRule.setContent {
            QuackIconTag(
                isSelected = false,
                text = TEST_TAG,
                icon = QuackIcon.Close,
                onClickTag = {},
                onClickIcon = {},
            )
        }

        composeTestRule.onNodeWithText(TEST_TAG)
            .assertTextColor(QuackColor.Black.value)
            .assertBackgroundColor(QuackColor.White.value)
    }

    @Test
    fun `selected_icon_tag_test`(){
        composeTestRule.setContent {
            QuackIconTag(
                isSelected = true,
                text = TEST_TAG,
                icon = QuackIcon.Close,
                onClickTag = {},
                onClickIcon = {},
            )
        }

        composeTestRule.onNodeWithText(TEST_TAG)
            .assertTextColor(QuackColor.White.value)
            .assertBackgroundColor(QuackColor.PumpkinOrange.value)
    }

    companion object {
        const val TEST_TAG = "TEST_TAG"
    }

}
