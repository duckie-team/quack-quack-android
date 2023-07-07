/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule

@Ignore("인터넷 권한이 안되는 듯...?")
@RunWith(AndroidJUnit4::class)
class ImageSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("image")

  @Test
  fun QuackImage_url_png() {
    captureRoboImage(snapshotPath()) {
      val quackquackBanner =
        "https://github.com/duckie-team/quack-quack-android/blob/main/website/static/images/meta-banner.png?raw=true"
      QuackImage(quackquackBanner)
    }
  }
}
