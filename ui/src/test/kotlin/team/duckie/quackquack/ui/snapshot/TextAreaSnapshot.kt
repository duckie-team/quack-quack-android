/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot

import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackTextArea
import team.duckie.quackquack.ui.textAreaCounter
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class TextAreaSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("textarea")

  @Test
  fun MinHeightAppliedPlaceholder() {
    captureRoboImage(snapshotPath()) {
      QuackTheme {
        QuackTextArea(
          value = "",
          onValueChange = {},
          placeholderText = "안녕\n하세\n요!",
        )
      }
    }
  }

  @Test
  fun MinHeightAppliedContent() {
    captureRoboImage(snapshotPath()) {
      QuackTheme {
        QuackTextArea(
          value = "안녕\n하세\n요!",
          onValueChange = {},
        )
      }
    }
  }

  @Test
  fun MinHeightAppliedContentWithCounter() {
    captureRoboImage(snapshotPath()) {
      QuackTheme {
        QuackTextArea(
          modifier = Modifier.textAreaCounter(maxLength = 10),
          value = "안녕\n하세\n요!",
          onValueChange = {},
        )
      }
    }
  }

  @Test
  fun ExtraHeightAppliedContentWithCounter() {
    captureRoboImage(snapshotPath()) {
      QuackTheme {
        QuackTextArea(
          modifier = Modifier.textAreaCounter(maxLength = 10),
          value = "안녕\n하세\n요!\n".repeat(5),
          onValueChange = {},
        )
      }
    }
  }
}
