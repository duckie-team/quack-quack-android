/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RoborazziRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class TextRoborazzi {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val roborazziRule = RoborazziRule(
        composeRule = composeTestRule,
        captureRoot = composeTestRule.onRoot(),
        options = RoborazziRule.Options(
            outputDirectoryPath = "src/test/snapshots",
        ),
    )

    @Test
    fun ModifierSpan() {
        composeTestRule.setContent {
            QuackText(
                modifier = Modifier
                    .background(color = Color.White)
                    .span(
                        texts = listOf("QuackQuack", "Duckie"),
                        style = SpanStyle(
                            color = QuackColor.DuckieOrange.value,
                            fontWeight = FontWeight.Black,
                        ),
                    )
                    .padding(30.dp),
                text = "QuackQuack is an awesome design system created by the Duckie team.",
                typography = QuackTypography.Body1,
            )
        }
    }
}
