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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.TextFieldPlaceholderStrategy
import team.duckie.quackquack.ui.defaultTextFieldIcon
import team.duckie.quackquack.ui.defaultTextFieldIndicator
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.token.IconRole
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      // CasaTheme {
      //   CasaScreen(models = casaModels)
      // }
      Preview {
        var value by rememberSaveable { mutableStateOf("") }
        QuackDefaultTextField(
          modifier = Modifier
            .fillMaxWidth()
            .defaultTextFieldIndicator()
            .defaultTextFieldIcon(
              icon = QuackIcon.FilledHeart,
              role = IconRole.Button,
              tint = QuackColor.Unspecified,
              direction = HorizontalDirection.Left,
            ) {
              toast("left heart!")
            }
            .defaultTextFieldIcon(
              icon = QuackIcon.FilledHeart,
              role = IconRole.Button,
              tint = QuackColor.Unspecified,
            ) {
              toast("right heart!")
            },
          value = value,
          onValueChange = { value = it },
          // placeholderText = "사랑의 주며, 청춘을 것은 이상은 되는 불러 바이며, 귀는 듣는다. 내는 힘차게 있는 황금시대다. 우리는 공자는 노년에게서 그들을 있는 수 얼음과 피다.",
          // validationState = TextFieldValidationState.Success(),
          placeholderText = "Hello!",
          placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          style = QuackTextFieldStyle.Default,
        )
        /*QuackDefaultTextField(
          modifier = Modifier
            .fillMaxWidth()
            .defaultTextFieldIndicator()
            .defaultTextFieldIcon(QuackIcon.FilledHeart)
            .defaultTextFieldIcon(QuackIcon.FilledHeart, role = IconRole.Button) {
              toast("heart!")
            },
          value = value,
          onValueChange = { value = it },
          placeholderText = "Hello!",
          placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          validationState = TextFieldValidationState.Error("방황하였으며, 실현에 관현악이며, 그들의 위하여 말이다. 대고, 못하다 청춘은 않는 많이 있다."),
          validationLabelVisibilityStrategy = TextFieldValidationLabelVisibilityStrategy.Invisible("방황하였으며, 실현에 관현악이며, 그들의 위하여 말이다. 대고, 못하다 청춘은 않는 많이 있다."),
          style = QuackTextFieldStyle.DefaultLarge,
        )*/
      }
    }
  }
}

@Suppress("UnusedPrivateMember", "unused")
@Composable
private fun Preview(content: @Composable ColumnScope.() -> Unit) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(
      space = 8.dp,
      alignment = Alignment.CenterVertically,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
  )
}
