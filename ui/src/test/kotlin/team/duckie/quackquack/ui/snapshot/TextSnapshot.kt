/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.snapshot.util.SnapshotPathGeneratorRule
import team.duckie.quackquack.ui.snapshot.util.TestColumn
import team.duckie.quackquack.ui.span

@RunWith(AndroidJUnit4::class)
class TextSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("text")

  @Test
  fun QuackText_span() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        QuackText(
          modifier = Modifier.span(
            texts = listOf("Hello"),
            style = SpanStyle(
              color = QuackColor.DuckieOrange.value,
              fontWeight = FontWeight.SemiBold,
            ),
          ),
          text = "Hello, World!",
          typography = QuackTypography.Body1,
        )
      }
    }
  }
}
