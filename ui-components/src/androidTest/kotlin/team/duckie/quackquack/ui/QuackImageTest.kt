/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "KDocFields",
)

package team.duckie.quackquack.ui

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon

public class QuackImageTest {
    @get:Rule
    public val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    public fun quack_image_composable_size_is_100() {
        val size = DpSize(
            width = 100.dp,
            height = 100.dp,
        )

        testRule.setContent {
            QuackImage(
                src = QuackIcon.Heart,
                size = size,
            )
        }

        testRule
            .onRoot()
            .assertWidthIsEqualTo(
                expectedWidth = size.width,
            )
            .assertHeightIsEqualTo(
                expectedHeight = size.height,
            )
    }
}
