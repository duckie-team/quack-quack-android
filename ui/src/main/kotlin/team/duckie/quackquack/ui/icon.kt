/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.ui.util.fontScaleAwareIconSize

/**
 * [QuackIcon]을 그립니다. 아이콘 사이즈는 [Modifier.size]로 정하는 게 아닌
 * [size] 인자로 제공해야 합니다. [size] 기본값은 24dp로 제공됩니다.
 *
 * @param icon 그릴 [아이콘][QuackIcon]
 * @param size 아이콘 사이즈
 * @param tint 아이콘에 입힐 틴트
 * @param contentScale 아이콘에 적용할 [contentScale][ContentScale]
 */
// TODO: @SugarParameterizedTest
@NonRestartableComposable
@Composable
public fun QuackIcon(
  @SugarToken icon: QuackIcon,
  modifier: Modifier = Modifier,
  size: Dp = 24.dp,
  tint: QuackColor = QuackColor.Unspecified,
  contentScale: ContentScale = ContentScale.Fit,
) {
  val currentColorFilter = remember(tint) { tint.toColorFilterOrNull() }

  Box(
    modifier
      .testTag("icon")
      .fontScaleAwareIconSize(baseline = size)
      .paint(
        painter = icon.asPainter(),
        contentScale = contentScale,
        colorFilter = currentColorFilter,
      )
  )
}
