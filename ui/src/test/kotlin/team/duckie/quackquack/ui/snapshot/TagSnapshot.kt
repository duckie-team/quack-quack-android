/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.snapshot

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RoborazziRule
import java.io.File
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.common.setQuackContent
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.util.compose.snapshot.test.BaseSnapshotPath
import team.duckie.quackquack.util.compose.snapshot.test.withIncreaseFontScale

@RunWith(AndroidJUnit4::class)
class TagSnapshot {
  private val testNameToSelectState = mutableMapOf<String, Boolean>()

  private fun stateMarkedTestName(testName: String): String {
    val selected = testNameToSelectState.getOrPut(testName) { true }
    return (testName + (if (selected) "_selected" else "_unselected"))
      .also { testNameToSelectState[testName] = false }
  }

  @get:Rule
  val compose = createAndroidComposeRule<ComponentActivity>()

  @get:Rule
  val roborazzi = RoborazziRule(
    composeRule = compose,
    captureRoot = compose.onRoot(),
    options = RoborazziRule.Options(
      captureType = RoborazziRule.CaptureType.AllImage,
      outputFileProvider = { description, _, fileExtension ->
        val snapshotPath = "$BaseSnapshotPath/tag/${description.methodName}"
        val countedSnapshotPath = stateMarkedTestName(snapshotPath)
        File("$countedSnapshotPath.$fileExtension")
      },
    ),
  )

  @Test
  fun Outlined() {
    compose.setQuackContent {
      var selected by remember { mutableStateOf(true) }
      QuackTag(
        modifier = Modifier.trailingIcon(),
        text = "QuackOutlinedTag",
        selected = selected,
        style = QuackTagStyle.Outlined,
      ) {
        selected = !selected
      }
    }
    toggle()
  }

  @Test
  fun Filled() {
    compose.setQuackContent {
      var selected by remember { mutableStateOf(true) }
      QuackTag(
        modifier = Modifier.trailingIcon(),
        text = "QuackFilledTag",
        selected = selected,
        style = QuackTagStyle.Filled,
      ) {
        selected = !selected
      }
    }
    toggle()
  }

  @Ignore("QuackText에 fontscale 대응 필요함")
  @Test
  fun FilledX4() {
    compose.setQuackContent {
      var selected by remember { mutableStateOf(true) }

      withIncreaseFontScale(4f) {
        QuackTag(
          modifier = Modifier.trailingIcon(),
          text = "QuackFilledTag",
          selected = selected,
          style = QuackTagStyle.Filled,
        ) {
          selected = !selected
        }
      }
    }
    toggle()
  }

  @Test
  fun GrayscaleFlat() {
    // no toggable
    // no decoratable
    compose.setQuackContent {
      QuackTag(
        modifier = Modifier.trailingIcon(),
        text = "QuackGrayscaleFlatTag",
        onClick = {},
        style = QuackTagStyle.GrayscaleFlat,
      )
    }
  }

  @Test
  fun GrayscaleOutlined() {
    compose.setQuackContent {
      var selected by remember { mutableStateOf(true) }
      QuackTag(
        modifier = Modifier.trailingIcon(),
        text = "QuackGrayscaleOutlinedTag",
        selected = selected,
        style = QuackTagStyle.GrayscaleOutlined,
      ) {
        selected = !selected
      }
    }
    toggle()
  }

  private fun toggle() {
    compose.onNodeWithTag("tag").performClick()
  }

  private fun Modifier.trailingIcon() =
    trailingIcon(icon = QuackIcon.Outlined.Heart, onClick = {})
}
