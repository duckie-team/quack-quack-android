/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import android.app.Activity
import android.widget.TextView
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

@RunWith(AndroidJUnit4::class)
class TypographySnapshot {
  @Test
  fun NoBreakText_Compose() {
    captureRoboImage("src/test/snapshots/NoBreakText_Compose.png") {
      BasicText(
        text = "퍠꿻땗뷃휉퉶뷟퍯 <- 잘 보이니?",
        style = QuackTypography.Body1.asComposeStyle(),
      )
    }
  }

  @Test
  fun NoBreakText_AndroidView() {
    captureRoboImage("src/test/snapshots/NoBreakText_AndroidView.png") {
      AndroidView(
        factory = { context ->
          TextView(context).apply {
            text = "퍠꿻땗뷃휉퉶뷟퍯 <- 잘 보이니?"
            val typeface = ResourcesCompat.getFont(context, R.font.quack_suit_medium)!!
            setTypeface(typeface)
          }
        },
      )
    }
  }

  @Test
  fun NoBreakText_TextView() {
    TextView(getActivityViaRobolectric).apply {
      text = "퍠꿻땗뷃휉퉶뷟퍯 <- 잘 보이니?"
      val typeface = ResourcesCompat.getFont(context, R.font.quack_suit_medium)!!
      setTypeface(typeface)
    }.captureRoboImage("src/test/snapshots/NoBreakText_TextView.png")
  }
}

private val getActivityViaRobolectric: Activity
  inline get() {
    val activityController = Robolectric.buildActivity(Activity::class.java)
      .also(ActivityController<Activity>::setup)
    return activityController.get()
  }
