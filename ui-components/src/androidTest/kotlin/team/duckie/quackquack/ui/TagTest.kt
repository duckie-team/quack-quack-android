/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TagTest.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:41
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBoldTag
import team.duckie.quackquack.ui.component.QuackIcon
import team.duckie.quackquack.ui.component.QuackIconTag
import team.duckie.quackquack.ui.constant.SimpleTagPadding

@RunWith(AndroidJUnit4::class)
class TagTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * TODO : Border 관련 테스트 코드 작성
     */
    @Test
    fun `quack_bold_tag_test`() {

        composeTestRule.setContent {
            var isSelected by remember { mutableStateOf(false)}
            QuackBoldTag(
                isSelected = isSelected,
                text = TEST_TAG,
                onClick = {
                    isSelected = !(isSelected)
                }
            )
        }

        composeTestRule.onNodeWithText(TEST_TAG)
            .assertHasClickAction()
            .assertHorizontallyCenterInRoot()
            .assertVerticallyCenterInRoot()
            .assertLeftPositionInRootIsEqualTo(SimpleTagPadding.calculateLeftPadding(LayoutDirection.Ltr))
            .assertRightPositionInRootIsEqualTo(SimpleTagPadding.calculateRightPadding(LayoutDirection.Ltr))
            .assertTopPositionInRootIsEqualTo(SimpleTagPadding.calculateTopPadding())
            .assertBottomPositionInRootIsEqualTo(SimpleTagPadding.calculateBottomPadding())
            .assertTextColor(QuackColor.Black)
            .performClick()
            .assertTextColor(QuackColor.DuckieOrange)
            .assertBackgroundColor(QuackColor.White)
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
            .assertTextColor(QuackColor.Black)
            .assertBackgroundColor(QuackColor.White)
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
            .assertTextColor(QuackColor.White)
            .assertBackgroundColor(QuackColor.DuckieOrange)
    }

    companion object {
        const val TEST_TAG = "TEST_TAG"
    }

}
