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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.ui.util.fontScaleAwareIconSize
import team.duckie.quackquack.util.applyIf

/**
 * [QuackIcon]을 그립니다. 아이콘 사이즈는 [Modifier.size]로 정하는 게 아닌
 * [size] 인자로 제공해야 합니다. [size] 기본값은 24dp로 제공됩니다.
 *
 * @param icon 그릴 [아이콘][QuackIcon]
 * @param size [icon]의 사이즈. 이 인자로 전달된 사이즈는 font scale에 맞게
 * 자동 조정될 수 있습니다. 자세한 내용은 꽥꽥 플러그인을 참고하세요.
 * @param tint [icon]에 적용할 틴트
 * @param contentScale [icon]에 적용할 [contentScale][ContentScale]
 * @param contentDescription [icon]을 설명하는 문구. 접근성 서비스에 사용됩니다.
 */
@NoSugar
@NonRestartableComposable
@Composable
public fun QuackIcon(
  icon: ImageVector,
  modifier: Modifier = Modifier,
  size: Dp = 24.dp,
  tint: QuackColor = QuackColor.Unspecified,
  contentScale: ContentScale = ContentScale.Fit,
  contentDescription: String? = null,
) {
  val currentColorFilter = remember(tint) { tint.toColorFilterOrNull() }

  Box(
    modifier
      .fontScaleAwareIconSize(baseline = size)
      .paint(
        painter = rememberVectorPainter(icon),
        contentScale = contentScale,
        colorFilter = currentColorFilter,
      )
      .applyIf(contentDescription != null) {
        semantics {
          this.contentDescription = contentDescription!!
        }
      },
  )
}
