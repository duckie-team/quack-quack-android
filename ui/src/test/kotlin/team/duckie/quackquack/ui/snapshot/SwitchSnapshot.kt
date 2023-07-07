/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.QuackSwitch
import team.duckie.quackquack.ui.snapshot.util.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class SwitchSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("switch")

  @Test
  fun Enabled() {
    captureRoboImage(snapshotPath()) {
      QuackSwitch(enabled = true) {}
    }
  }

  @Test
  fun Disabled() {
    captureRoboImage(snapshotPath()) {
      QuackSwitch(enabled = false) {}
    }
  }
}
