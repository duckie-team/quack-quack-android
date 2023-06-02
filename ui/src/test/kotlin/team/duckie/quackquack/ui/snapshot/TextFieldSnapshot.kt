/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.quackquack.ui.snapshot

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.TextFieldPlaceholderStrategy
import team.duckie.quackquack.ui.TextFieldValidationState
import team.duckie.quackquack.ui.counter
import team.duckie.quackquack.ui.defaultTextFieldIcon
import team.duckie.quackquack.ui.defaultTextFieldIndicator
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.snapshot.util.LargestFontScale
import team.duckie.quackquack.ui.snapshot.util.SnapshotPathGeneratorRule
import team.duckie.quackquack.ui.snapshot.util.TestColumn
import team.duckie.quackquack.ui.snapshot.util.WithLabel
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.token.VerticalDirection
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private const val ShortText = "가나다라마"
private const val MediumText = "가나다라마바사아자차카타파하"
private const val LongText = "그러므로 주며, 없으면 우리 보라. 이것은 온갖 안고, 거선의 황금시대다."
private const val MultilineText = "$ShortText\n$ShortText\n$ShortText\n$ShortText\n$ShortText"

private const val SuccessText = "성공!"
private const val ErrorText = "실패!"

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class TextFieldSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("textfield")

  @Test
  fun QuackDefaultTextField_default() {
    captureRoboImage(snapshotPath()) {
      QuackDefaultTextField(
        value = ShortText,
        onValueChange = {},
        style = QuackTextFieldStyle.Default,
      )
    }
  }

  @Test
  fun QuackDefaultTextField_default_placeholders() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("Hidable") {
          QuackDefaultTextField(
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            placeholderText = MediumText,
            placeholderStrategy = TextFieldPlaceholderStrategy.Hidable,
          )
        }
        WithLabel("Always") {
          QuackDefaultTextField(
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            placeholderText = MediumText,
            placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          )
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextField_default_validations() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("default") {
          QuackDefaultTextField(
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            validationState = TextFieldValidationState.Default,
          )
        }
        WithLabel("success") {
          QuackDefaultTextField(
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            validationState = TextFieldValidationState.Success(SuccessText),
          )
        }
        WithLabel("error") {
          QuackDefaultTextField(
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            validationState = TextFieldValidationState.Error(ErrorText),
          )
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextField_default_indicators() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("top-default") {
          QuackDefaultTextField(
            modifier = Modifier.defaultTextFieldIndicator(direction = VerticalDirection.Top),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("bottom-default") {
          QuackDefaultTextField(
            modifier = Modifier.defaultTextFieldIndicator(),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("bottom-success") {
          QuackDefaultTextField(
            modifier = Modifier.defaultTextFieldIndicator(),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            validationState = TextFieldValidationState.Success(SuccessText),
          )
        }
        WithLabel("bottom-error") {
          QuackDefaultTextField(
            modifier = Modifier.defaultTextFieldIndicator(),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            validationState = TextFieldValidationState.Error(ErrorText),
          )
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextField_default_icons() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("both") {
          QuackDefaultTextField(
            modifier = Modifier
              .defaultTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Left,
              )
              .defaultTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Right,
              ),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("leading") {
          QuackDefaultTextField(
            modifier = Modifier.defaultTextFieldIcon(
              icon = QuackIcon.Outlined.Heart,
              tint = QuackColor.Unspecified,
              direction = HorizontalDirection.Left,
            ),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("trailing") {
          QuackDefaultTextField(
            modifier = Modifier.defaultTextFieldIcon(
              icon = QuackIcon.Outlined.Heart,
              tint = QuackColor.Unspecified,
              direction = HorizontalDirection.Right,
            ),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextField_default_counters() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("default") {
          QuackDefaultTextField(
            modifier = Modifier.counter(maxLength = 10),
            value = ShortText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("over") {
          QuackDefaultTextField(
            modifier = Modifier.counter(maxLength = 10),
            value = LongText,
            onValueChange = {},
            singleLine = true,
            style = QuackTextFieldStyle.Default,
          )
        }
      }
    }
  }

  @Config(qualifiers = MultilinesSnapshotQualifier)
  @Test
  fun QuackDefaultTextField_default_multilines() {
    QuackDefaultTextField_default_multilines_catpurer(fillMaxWidth = false)
  }

  @Config(fontScale = LargestFontScale, qualifiers = MultilinesSnapshotQualifier)
  @Test
  fun QuackDefaultTextField_default_multilines_x2() {
    QuackDefaultTextField_default_multilines_catpurer(fillMaxWidth = true)
  }

  private fun QuackDefaultTextField_default_multilines_catpurer(fillMaxWidth: Boolean) {
    val widthModifier = if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("default") {
          QuackDefaultTextField(
            modifier = Modifier.then(widthModifier),
            value = MultilineText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("placeholder") {
          QuackDefaultTextField(
            modifier = Modifier.then(widthModifier),
            value = MediumText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            placeholderText = MultilineText,
            placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          )
        }
        WithLabel("indicator") {
          QuackDefaultTextField(
            modifier = Modifier
              .then(widthModifier)
              .defaultTextFieldIndicator(),
            value = MultilineText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
            placeholderText = MultilineText,
            validationState = TextFieldValidationState.Success(SuccessText),
          )
        }
        WithLabel("icons") {
          QuackDefaultTextField(
            modifier = Modifier
              .then(widthModifier)
              .defaultTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Left,
              )
              .defaultTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Right,
              ),
            value = MultilineText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("counter") {
          QuackDefaultTextField(
            modifier = Modifier
              .then(widthModifier)
              .counter(maxLength = 10),
            value = MultilineText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
      }
    }
  }

  private companion object {
    // Copied form Pixel7 qualifier
    const val MultilinesSnapshotQualifier = "w400dp-h8000dp-normal-long-notround-any-420dpi-keyshidden-nonav"
  }
}
