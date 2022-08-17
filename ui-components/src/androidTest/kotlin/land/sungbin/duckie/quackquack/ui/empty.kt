/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [empty.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:57
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package land.sungbin.duckie.quackquack.ui

import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleText {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Test
    fun `sample_test`(){
        composeTestRule.setContent{
            Text("Hello World")
        }
        composeTestRule.onNodeWithText("Hello World").assertExists()
    }
}


