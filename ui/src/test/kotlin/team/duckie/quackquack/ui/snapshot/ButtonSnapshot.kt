/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.snapshot

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.sugar.QuackPrimaryOutlinedSmallButton
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@RunWith(AndroidJUnit4::class)
class ButtonSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("button")

  @Test
  fun Enabled() {
    captureRoboImage(snapshotPath()) {
      QuackPrimaryOutlinedSmallButton(enabled = true, text = "BUTTON", onClick = {})
    }
  }

  @Test
  fun Disabled() {
    captureRoboImage(snapshotPath()) {
      QuackPrimaryOutlinedSmallButton(enabled = false, text = "BUTTON", onClick = {})
    }
  }
}