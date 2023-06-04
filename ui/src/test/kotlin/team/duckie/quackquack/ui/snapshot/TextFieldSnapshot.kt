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
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackFilledTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.TextFieldPlaceholderStrategy
import team.duckie.quackquack.ui.TextFieldValidationState
import team.duckie.quackquack.ui.counter
import team.duckie.quackquack.ui.defaultTextFieldIcon
import team.duckie.quackquack.ui.defaultTextFieldIndicator
import team.duckie.quackquack.ui.filledTextFieldIcon
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.snapshot.util.LargestFontScale
import team.duckie.quackquack.ui.snapshot.util.MultilinesSnapshotQualifier
import team.duckie.quackquack.ui.snapshot.util.SnapshotPathGeneratorRule
import team.duckie.quackquack.ui.snapshot.util.TestColumn
import team.duckie.quackquack.ui.snapshot.util.WithLabel
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.token.VerticalDirection
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private const val MediumText = "가나다라마바사아자차카타파하"
private const val LongText = "그러므로 주며, 없으면 우리 보라. 이것은 온갖 안고, 거선의 황금시대다."
private const val MultilineText = "$MediumText\n$MediumText\n$MediumText\n$MediumText\n$MediumText"

private const val SuccessText = "성공!"
private const val ErrorText = "실패!"

// TODO: parameterized test
@Config(qualifiers = MultilinesSnapshotQualifier)
@RunWith(AndroidJUnit4::class)
class TextFieldSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("textfield")

  @Test
  fun QuackDefaultTextFields() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("[Default]", isTitle = true) {
          QuackDefaultTextField(
            value = MediumText,
            onValueChange = {},
            style = QuackTextFieldStyle.Default,
          )
        }
        WithLabel("[DefaultLarge]", isTitle = true) {
          QuackDefaultTextField(
            value = MediumText,
            onValueChange = {},
            style = QuackTextFieldStyle.DefaultLarge,
          )
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextFields_placeholders() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[Default]", isTitle = true) {
          WithLabel("Hidable") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Hidable,
            )
          }
          WithLabel("Always") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Always,
            )
          }
        }
        WithLabel("[DefaultLarge]", isTitle = true) {
          WithLabel("Hidable") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Hidable,
            )
          }
          WithLabel("Always") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Always,
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextFields_validations() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[Default]", isTitle = true) {
          WithLabel("default") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              validationState = TextFieldValidationState.Default,
            )
          }
          WithLabel("success") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              validationState = TextFieldValidationState.Success(SuccessText),
            )
          }
          WithLabel("error") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              validationState = TextFieldValidationState.Error(ErrorText),
            )
          }
        }
        WithLabel("[DefaultLarge]", isTitle = true) {
          WithLabel("default") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              validationState = TextFieldValidationState.Default,
            )
          }
          WithLabel("success") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              validationState = TextFieldValidationState.Success(SuccessText),
            )
          }
          WithLabel("error") {
            QuackDefaultTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              validationState = TextFieldValidationState.Error(ErrorText),
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextFields_indicators() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[Default]", isTitle = true) {
          WithLabel("top / default") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(direction = VerticalDirection.Top),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
            )
          }
          WithLabel("bottom / default") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
            )
          }
          WithLabel("bottom / success") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              validationState = TextFieldValidationState.Success(SuccessText),
            )
          }
          WithLabel("bottom / error") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
              validationState = TextFieldValidationState.Error(ErrorText),
            )
          }
        }
        WithLabel("[DefaultLarge]", isTitle = true) {
          WithLabel("top / default") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(direction = VerticalDirection.Top),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("bottom / default") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("bottom / success") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              validationState = TextFieldValidationState.Success(SuccessText),
            )
          }
          WithLabel("bottom / error") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIndicator(),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
              validationState = TextFieldValidationState.Error(ErrorText),
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextFields_icons() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[Default]", isTitle = true) {
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
              value = MediumText,
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
              value = MediumText,
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
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.Default,
            )
          }
        }
        WithLabel("[DefaultLarge]", isTitle = true) {
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
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("leading") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Left,
              ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("trailing") {
            QuackDefaultTextField(
              modifier = Modifier.defaultTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Right,
              ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextFields_counters() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[Default]", isTitle = true) {
          WithLabel("default") {
            QuackDefaultTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = MediumText,
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
        WithLabel("[DefaultLarge]", isTitle = true) {
          WithLabel("default") {
            QuackDefaultTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("over") {
            QuackDefaultTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = LongText,
              onValueChange = {},
              singleLine = true,
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackDefaultTextFields_multilines() {
    QuackDefaultTextFields_multilines_capturer(fillMaxWidth = false)
  }

  @Config(fontScale = LargestFontScale)
  @Test
  fun QuackDefaultTextFields_multilines_x2() {
    QuackDefaultTextFields_multilines_capturer(fillMaxWidth = true)
  }

  private fun QuackDefaultTextFields_multilines_capturer(fillMaxWidth: Boolean) {
    val widthModifier = if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[Default]", isTitle = true) {
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
        WithLabel("[DefaultLarge]", isTitle = true) {
          WithLabel("default") {
            QuackDefaultTextField(
              modifier = Modifier.then(widthModifier),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("placeholder") {
            QuackDefaultTextField(
              modifier = Modifier.then(widthModifier),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
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
              style = QuackTextFieldStyle.DefaultLarge,
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
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
          WithLabel("counter") {
            QuackDefaultTextField(
              modifier = Modifier
                .then(widthModifier)
                .counter(maxLength = 10),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.DefaultLarge,
            )
          }
        }
      }
    }
  }

  // QuackDefaultTextField
  // -------------------------------------------------- //
  // QuackFilledTextField

  @Test
  fun QuackFilledTextFields() {
    captureRoboImage(snapshotPath()) {
      TestColumn {
        WithLabel("[FilledLarge]", isTitle = true) {
          QuackFilledTextField(
            value = MediumText,
            onValueChange = {},
            style = QuackTextFieldStyle.FilledLarge,
          )
        }
        WithLabel("[FilledFlat]", isTitle = true) {
          QuackFilledTextField(
            value = MediumText,
            onValueChange = {},
            style = QuackTextFieldStyle.FilledFlat,
          )
        }
      }
    }
  }

  @Test
  fun QuackFilledTextField_FilledLarge_placeholders() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[FilledLarge]", isTitle = true) {
          WithLabel("Hidable") {
            QuackFilledTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Hidable,
            )
          }
          WithLabel("Always") {
            QuackFilledTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Always,
            )
          }
        }
        WithLabel("[FilledFlat]", isTitle = true) {
          WithLabel("Hidable") {
            QuackFilledTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Hidable,
            )
          }
          WithLabel("Always") {
            QuackFilledTextField(
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
              placeholderText = MediumText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Always,
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackFilledTextField_FilledLarge_icons() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[FilledLarge]", isTitle = true) {
          WithLabel("both") {
            QuackFilledTextField(
              modifier = Modifier
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Left,
                )
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Right,
                ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
          WithLabel("leading") {
            QuackFilledTextField(
              modifier = Modifier.filledTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Left,
              ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
          WithLabel("trailing") {
            QuackFilledTextField(
              modifier = Modifier.filledTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Right,
              ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
        }
        WithLabel("[FilledFlat]", isTitle = true) {
          WithLabel("both") {
            QuackFilledTextField(
              modifier = Modifier
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Left,
                )
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Right,
                ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
          WithLabel("leading") {
            QuackFilledTextField(
              modifier = Modifier.filledTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Left,
              ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
          WithLabel("trailing") {
            QuackFilledTextField(
              modifier = Modifier.filledTextFieldIcon(
                icon = QuackIcon.Outlined.Heart,
                tint = QuackColor.Unspecified,
                direction = HorizontalDirection.Right,
              ),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackFilledTextField_FilledLarge_counters() {
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[FilledLarge]", isTitle = true) {
          WithLabel("Filled") {
            QuackFilledTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
          WithLabel("over") {
            QuackFilledTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = LongText,
              onValueChange = {},
              singleLine = true,
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
        }
        WithLabel("[FilledFlat]", isTitle = true) {
          WithLabel("Filled") {
            QuackFilledTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
          WithLabel("over") {
            QuackFilledTextField(
              modifier = Modifier.counter(maxLength = 10),
              value = LongText,
              onValueChange = {},
              singleLine = true,
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
        }
      }
    }
  }

  @Test
  fun QuackFilledTextField_FilledLarge_multilines() {
    QuackFilledTextField_FilledLarge_multilines_capturer(fillMaxWidth = false)
  }

  @Config(fontScale = LargestFontScale)
  @Test
  fun QuackFilledTextField_FilledLarge_multilines_x2() {
    QuackFilledTextField_FilledLarge_multilines_capturer(fillMaxWidth = true)
  }

  private fun QuackFilledTextField_FilledLarge_multilines_capturer(fillMaxWidth: Boolean) {
    val widthModifier = if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier
    captureRoboImage(snapshotPath()) {
      TestColumn(contentGap = 30.dp) {
        WithLabel("[FilledLarge]", isTitle = true) {
          WithLabel("Filled") {
            QuackFilledTextField(
              modifier = Modifier.then(widthModifier),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
          WithLabel("placeholder") {
            QuackFilledTextField(
              modifier = Modifier.then(widthModifier),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
              placeholderText = MultilineText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Always,
            )
          }
          WithLabel("icons") {
            QuackFilledTextField(
              modifier = Modifier
                .then(widthModifier)
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Left,
                )
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Right,
                ),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
          WithLabel("counter") {
            QuackFilledTextField(
              modifier = Modifier
                .then(widthModifier)
                .counter(maxLength = 10),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledLarge,
            )
          }
        }
        WithLabel("[FilledFlat]", isTitle = true) {
          WithLabel("Filled") {
            QuackFilledTextField(
              modifier = Modifier.then(widthModifier),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
          WithLabel("placeholder") {
            QuackFilledTextField(
              modifier = Modifier.then(widthModifier),
              value = MediumText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
              placeholderText = MultilineText,
              placeholderStrategy = TextFieldPlaceholderStrategy.Always,
            )
          }
          WithLabel("icons") {
            QuackFilledTextField(
              modifier = Modifier
                .then(widthModifier)
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Left,
                )
                .filledTextFieldIcon(
                  icon = QuackIcon.Outlined.Heart,
                  tint = QuackColor.Unspecified,
                  direction = HorizontalDirection.Right,
                ),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
          WithLabel("counter") {
            QuackFilledTextField(
              modifier = Modifier
                .then(widthModifier)
                .counter(maxLength = 10),
              value = MultilineText,
              onValueChange = {},
              style = QuackTextFieldStyle.FilledFlat,
            )
          }
        }
      }
    }
  }
}
