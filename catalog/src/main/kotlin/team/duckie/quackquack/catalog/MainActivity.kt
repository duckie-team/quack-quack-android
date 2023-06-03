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
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.counter
import team.duckie.quackquack.ui.defaultTextFieldIcon
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      /*CasaTheme {
        CasaScreen(models = casaModels)
      }*/
      /*QuackTheme(
        plugins = rememberQuackPlugins {
          +QuackImageGifPlugin
        },
      ) {
        Box(modifier = Modifier.fillMaxSize()) {
          QuackImage(src = "https://media.tenor.com/K-Noz5k7X04AAAAi/colors-rainbow.gif")
        }
      }*/
      Preview {
        QuackDefaultTextField(
          modifier = Modifier
            .counter(maxLength = 10)
            .defaultTextFieldIcon(QuackIcon.Outlined.Heart),
          value = "가나다라마바사아자차카타파하가나다라",
          onValueChange = {},
          singleLine = true,
          style = QuackTextFieldStyle.DefaultLarge,
        )
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
      space = 15.dp,
      alignment = Alignment.CenterVertically,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
  )
}
