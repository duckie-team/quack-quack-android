/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.foundation.text.BasicText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TypographySnapshot {
  @Test
  fun no_break_text() {
    captureRoboImage("src/test/snapshots/NoBreakText.png") {
      BasicText(
        text = "퍠뜨로늄",
        style = QuackTypography.Body1.asComposeStyle(),
      )
    }
  }
}
