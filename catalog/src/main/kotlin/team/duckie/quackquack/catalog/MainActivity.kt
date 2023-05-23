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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.TextFieldPlaceholderStrategy
import team.duckie.quackquack.ui.indicator
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.token.VerticalDirection
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      // CasaTheme {
      //   CasaScreen(models = casaModels)
      // }
      Preview {
        var value by remember { mutableStateOf("") }
        QuackDefaultTextField(
          modifier = Modifier
            .fillMaxWidth()
            .indicator(),
          value = value,
          onValueChange = { value = it },
          placeholderText = "Hello!",
          style = QuackTextFieldStyle.Default,
        )
        QuackDefaultTextField(
          modifier = Modifier
            .fillMaxWidth()
            .indicator(direction = VerticalDirection.Top),
          value = value,
          onValueChange = { value = it },
          placeholderText = "Hello!",
          placeholderStrategy = TextFieldPlaceholderStrategy.Always,
          style = QuackTextFieldStyle.Default,
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
      space = 8.dp,
      alignment = Alignment.CenterVertically,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
  )
}
