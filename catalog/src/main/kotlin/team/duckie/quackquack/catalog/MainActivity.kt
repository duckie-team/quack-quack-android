/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class, ExperimentalDesignToken::class)
@file:Suppress("UnnecessaryOptInAnnotation")

package team.duckie.quackquack.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import casaModels
import team.duckie.quackquack.casa.ui.CasaScreen
import team.duckie.quackquack.casa.ui.theme.CasaTheme
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CasaTheme {
        CasaScreen(models = casaModels)
      }
      /*Preview {
        var value by rememberSaveable { mutableStateOf("") }
        QuackDefaultTextField(
          value = "ShortText",
          onValueChange = {},
          style = QuackTextFieldStyle.Default,
          validationState = TextFieldValidationState.Default,
        )
        QuackDefaultTextField(
          modifier = Modifier
            .fillMaxWidth()
            .defaultTextFieldIndicator()
            .defaultTextFieldIcon(
              icon = QuackIcon.FilledHeart,
              tint = QuackColor.Unspecified,
              iconSize = 24.dp,
              direction = HorizontalDirection.Left,
            ) {
              toast("left heart!")
            }
            .defaultTextFieldIcon(
              icon = QuackIcon.FilledHeart,
              iconSize = 24.dp,
              tint = QuackColor.Unspecified,
              direction = HorizontalDirection.Right,
            ) {
              toast("right heart!")
            }
            .counter(maxLength = 10),
          value = value,
          onValueChange = { value = it },
          // placeholderText = "사랑의 주며, 청춘을 것은 이상은 되는 불러 바이며, 귀는 듣는다. 내는 힘차게 있는 황금시대다. 우리는 공자는 노년에게서 그들을 있는 수 얼음과 피다.",
          validationState = TextFieldValidationState.Error(),
          placeholderText = "Hello!",
          placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          style = QuackTextFieldStyle.Default,
        )
        QuackDefaultTextField(
          modifier = Modifier
            .fillMaxWidth()
            .defaultTextFieldIndicator()
            .defaultTextFieldIcon(
              icon = QuackIcon.FilledHeart,
              tint = QuackColor.Unspecified,
              direction = HorizontalDirection.Left,
            ) {
              toast("left heart!")
            }
            .defaultTextFieldIcon(
              icon = QuackIcon.FilledHeart,
              tint = QuackColor.Unspecified,
              direction = HorizontalDirection.Right,
            ) {
              toast("right heart!")
            }
            .counter(maxLength = 10),
          value = value,
          onValueChange = { value = it },
          // placeholderText = "사랑의 주며, 청춘을 것은 이상은 되는 불러 바이며, 귀는 듣는다. 내는 힘차게 있는 황금시대다. 우리는 공자는 노년에게서 그들을 있는 수 얼음과 피다.",
          validationState = TextFieldValidationState.Success(),
          placeholderText = "Hello!",
          placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          style = QuackTextFieldStyle.DefaultLarge,
        )
      }*/
    }
  }
}

@Suppress("UnusedPrivateMember", "unused")
@Composable
private fun Preview(content: @Composable ColumnScope.() -> Unit) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(
      space = 15.dp,
      alignment = Alignment.CenterVertically,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
  )
}
