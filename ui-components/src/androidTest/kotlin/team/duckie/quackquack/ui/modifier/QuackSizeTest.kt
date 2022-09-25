/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class QuackSizeTest {
    @get:Rule
    val testRule = createComposeRule()

        @Test
        fun all_quack_size_is_500_x_500() {
            val size = 500.dp

            testRule.setContent {
                Box(
                    modifier = Modifier.size(
                        size = size,
                    ),
                )
            }
            testRule
                .onRoot()
                .assertWidthIsEqualTo(
                    expectedWidth = size,
                )
                .assertHeightIsEqualTo(
                    expectedHeight = size,
                )
        }

    @Test
    fun all_quack_size_is_500_x_100() {
        testRule.setContent {
            Box(
                /*modifier = Modifier.applyQuackSize(
                    width = QuackWidth.Custom(
                        width = 500.dp,
                    ),
                    height = QuackHeight.Custom(
                        height = 100.dp,
                    )
                ),*/
                modifier = Modifier.size(
                    width = 500.dp,
                    height = 100.dp,
                ),
            )
        }
        testRule
            .onRoot()
            .assertWidthIsEqualTo(
                expectedWidth = 500.dp,
            )
            .assertHeightIsEqualTo(
                expectedHeight = 100.dp,
            )
    }
}
