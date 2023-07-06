/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import team.duckie.quackquack.ui.QuackTab
import team.duckie.quackquack.ui.snapshot.util.SnapshotPathGeneratorRule

@Config(qualifiers = "w400dp-h200dp")
@RunWith(AndroidJUnit4::class)
class TabSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("tab")

  @Test
  fun EmptyContent() {
    captureRoboImage(snapshotPath()) {
      QuackTab(
        index = 0,
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        content = {},
      )
    }
  }

  @Test
  fun FiveSameTabsAndIndexIsZero() {
    captureRoboImage(snapshotPath()) {
      QuackTab(index = 0, modifier = Modifier.fillMaxWidth()) {
        repeat(5) {
          tab("Tab#$candidateIndex") {}
        }
      }
    }
  }

  @Test
  fun FiveSameTabsAndIndexIsTwo() {
    captureRoboImage(snapshotPath()) {
      QuackTab(index = 2, modifier = Modifier.fillMaxWidth()) {
        repeat(5) {
          tab("Tab#$candidateIndex") {}
        }
      }
    }
  }

  @Test
  fun FiveSameTabsAndIndexIsFour() {
    captureRoboImage(snapshotPath()) {
      QuackTab(index = 4, modifier = Modifier.fillMaxWidth()) {
        repeat(5) {
          tab("Tab#$candidateIndex") {}
        }
      }
    }
  }

  @Test
  fun ThreeDynamicTabsAndIndexIsZero() {
    captureRoboImage(snapshotPath()) {
      QuackTab(index = 0, modifier = Modifier.fillMaxWidth()) {
        repeat(3) {
          tab("Tab".repeat(candidateIndex + 1)) {}
        }
      }
    }
  }

  @Test
  fun ThreeDynamicTabsAndIndexIsTwo() {
    captureRoboImage(snapshotPath()) {
      QuackTab(index = 2, modifier = Modifier.fillMaxWidth()) {
        repeat(3) {
          tab("Tab".repeat(candidateIndex + 1)) {}
        }
      }
    }
  }
}
