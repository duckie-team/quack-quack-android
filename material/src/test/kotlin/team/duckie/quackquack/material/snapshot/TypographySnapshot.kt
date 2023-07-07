/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.snapshot

import android.widget.TextView
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.R
import team.duckie.quackquack.util.compose.snapshot.test.SnapshotPathGeneratorRule
import team.duckie.quackquack.util.compose.snapshot.test.getActivityViaRobolectric

private const val TestMessage = "퍠꿻땗뷃휉퉶뷟퍯 <- 잘 보이니?"

// FIXME: Part of #761.
@RunWith(AndroidJUnit4::class)
class TypographySnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("typography")

  @Test
  fun NoBreakText_Compose_BasicText() {
    captureRoboImage(snapshotPath()) {
      BasicText(
        text = TestMessage,
        style = TextStyle(fontFamily = QuackTypography.Body1.fontFamily),
      )
    }
  }

  @Test
  fun NoBreakText_Compose_AndroidView() {
    captureRoboImage(snapshotPath()) {
      AndroidView(
        factory = { context ->
          TextView(context).apply {
            text = TestMessage
            val typeface = ResourcesCompat.getFont(context, R.font.quack_suit_medium)!!
            setTypeface(typeface)
          }
        },
      )
    }
  }

  @Test
  fun NoBreakText_View_TextView() {
    TextView(getActivityViaRobolectric).apply {
      text = TestMessage
      val typeface = ResourcesCompat.getFont(context, R.font.quack_suit_medium)!!
      setTypeface(typeface)
    }.captureRoboImage(snapshotPath())
  }
}
