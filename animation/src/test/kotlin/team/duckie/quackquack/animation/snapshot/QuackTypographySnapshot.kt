/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.animation.snapshot

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.animation.animatedQuackTypographyAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class QuackTypographySnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("typography")

  @Test
  fun animated_without_exception() {
    captureRoboImage(snapshotPath()) {
      var state by remember { mutableStateOf(false) }

      LaunchedEffect(Unit) {
        state = true
      }

      BasicText(
        text = "Hello, World!",
        style = animatedQuackTypographyAsState(
          if (state) QuackTypography.Large1.change(color = QuackColor.Success)
          else QuackTypography.Body3.change(color = QuackColor.Alert),
        ).asComposeStyle(),
      )
    }
  }
}
