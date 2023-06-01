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
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.span
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
        QuackText(
          modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .span(
              texts = listOf("잭", "타이타닉"),
              style = SpanStyle(
                color = QuackColor.DuckieOrange.value,
                fontWeight = FontWeight.SemiBold,
              ),
            ),
          text = "잭.. 이 사람\n" +
            "타이타닉도 안봤나봐요...",
          typography = QuackTypography.Quote,
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
