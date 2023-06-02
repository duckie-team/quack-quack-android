/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalLayoutApi::class)

package team.duckie.quackquack.material.icon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class QuackIconSnapshot {
  @Config(qualifiers = RobolectricDeviceQualifiers.NexusOne)
  @Test
  fun icons() {
    captureRoboImage("src/test/snapshots/icons.png") {
      FlowRow(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(
          space = 4.dp,
          alignment = Alignment.CenterHorizontally,
        ),
      ) {
        QuackIcon.AllIcons.forEach { icon ->
          Box(
            modifier = Modifier
              .size(24.dp)
              .paint(
                painter = rememberVectorPainter(image = icon),
                contentScale = ContentScale.Fit,
              ),
          )
        }
      }
    }
  }
}
