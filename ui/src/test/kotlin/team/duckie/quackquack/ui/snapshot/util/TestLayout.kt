/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.theme.QuackTheme

@Composable
fun TestColumn(contentGap: Dp = 15.dp, content: @Composable () -> Unit) {
  Column(
    modifier = Modifier.wrapContentSize(),
    verticalArrangement = Arrangement.spacedBy(contentGap),
  ) {
    QuackTheme(content)
  }
}

@Composable
fun WithLabel(
  label: String,
  isTitle: Boolean = false,
  labelGap: Dp = 4.dp,
  contentGap: Dp = if (isTitle) 15.dp else 4.dp,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    modifier = Modifier.wrapContentSize(),
    verticalArrangement = Arrangement.spacedBy(labelGap),
  ) {
    BasicText(
      label,
      style = TextStyle.Default.let { style ->
        if (isTitle) style.copy(fontWeight = FontWeight.Bold) else style
      },
    )
    Column(
      modifier = Modifier.wrapContentSize(),
      verticalArrangement = Arrangement.spacedBy(contentGap),
      content = content,
    )
  }
}
