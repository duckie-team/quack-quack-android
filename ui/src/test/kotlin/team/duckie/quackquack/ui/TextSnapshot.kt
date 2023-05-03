/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.util.snapshotPath

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class TextSnapshot {
    @Test
    fun ModifierSpan() {
        captureRoboImage(snapshotPath("text")) {
            QuackText(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(30.dp)
                    .span(
                        texts = listOf("QuackQuack", "Duckie"),
                        style = SpanStyle(
                            color = QuackColor.DuckieOrange.value,
                            fontWeight = FontWeight.Bold,
                        ),
                    ),
                text = "QuackQuack is an awesome ui kit created by the Duckie team.",
                typography = QuackTypography.Large1,
            )
        }
    }
}
