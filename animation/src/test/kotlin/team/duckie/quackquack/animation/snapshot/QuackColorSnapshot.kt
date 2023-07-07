/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.animation.snapshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.animation.animateQuackColorAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class QuackColorSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("color")

  @Test
  fun animated_without_exception() {
    captureRoboImage(snapshotPath()) {
      var state by remember { mutableStateOf(false) }

      LaunchedEffect(Unit) {
        state = true
      }

      Box(
        Modifier
          .size(30.dp)
          .background(
            color = animateQuackColorAsState(
              if (state) QuackColor.Success else QuackColor.Alert,
            ).value.value,
          ),
      )
    }
  }
}
