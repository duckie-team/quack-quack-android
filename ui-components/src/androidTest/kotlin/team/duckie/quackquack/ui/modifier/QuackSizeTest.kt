/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "KDocFields",
)

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.util.assertColor

public class QuackSizeTest {
    @get:Rule
    public val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    public fun all_quack_size_is_100_x_100() {
        val size = 100.dp

        testRule.setContent {
            Box(
                modifier = Modifier.applyQuackSize(
                    width = QuackWidth.Custom(
                        width = size,
                    ),
                    height = QuackHeight.Custom(
                        height = size,
                    ),
                )
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
    public fun all_quack_size_is_100_x_10() {
        val width = 100.dp
        val height = 10.dp

        testRule.setContent {
            Box(
                modifier = Modifier.applyQuackSize(
                    width = QuackWidth.Custom(
                        width = width,
                    ),
                    height = QuackHeight.Custom(
                        height = height,
                    ),
                ),
            )
        }

        testRule
            .onRoot()
            .assertWidthIsEqualTo(
                expectedWidth = width,
            )
            .assertHeightIsEqualTo(
                expectedHeight = height,
            )
    }

    @Test
    public fun size_is_match_device() {
        val color = Color.Green

        testRule.setContent {
            Box(
                modifier = Modifier.applyQuackSize(
                    width = QuackWidth.Fill,
                    height = QuackHeight.Fill,
                ),
            ) {
                Box(
                    modifier = Modifier
                        .applyQuackSize(
                            width = QuackWidth.Fill,
                            height = QuackHeight.Fill,
                        )
                        .background(
                            color = color,
                        ),
                )
            }
        }

        testRule
            .onRoot()
            .assertColor(
                expectedColor = color,
            )
    }

    @Test
    public fun size_is_wrap_content() {
        val size = 100.dp

        testRule.setContent {
            Box(
                modifier = Modifier.applyQuackSize(
                    width = QuackWidth.Wrap,
                    height = QuackHeight.Wrap,
                ),
            ) {
                Box(
                    modifier = Modifier.applyQuackSize(
                        width = QuackWidth.Custom(
                            width = size,
                        ),
                        height = QuackHeight.Custom(
                            height = size,
                        ),
                    ),
                )
            }
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
}
