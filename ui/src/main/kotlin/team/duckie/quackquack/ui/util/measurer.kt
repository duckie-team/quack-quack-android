/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.LayoutDirection

/**
 * [fallbackLayoutDirection][TextMeasurer.fallbackLayoutDirection]을 [LayoutDirection.Ltr]으로 고정하며
 * [TextMeasurer] 인스턴스를 생성 및 반환합니다. [LayoutDirection.Rtl]을 고려하지 않아도 되는 상황에서
 * 불필요한 컴포저블 참조를 줄여줍니다.
 *
 * @param cacheSize [TextMeasurer] 생성자에 [cacheSize][TextMeasurer.cacheSize]로 전달할 값
 */
@Composable
internal fun rememberLtrTextMeasurer(cacheSize: Int = /*DefaultCacheSize*/ 8): TextMeasurer {
  val fontFamilyResolver = LocalFontFamilyResolver.current
  val density = LocalDensity.current

  return remember(fontFamilyResolver, density, cacheSize) {
    TextMeasurer(
      fallbackFontFamilyResolver = fontFamilyResolver,
      fallbackDensity = density,
      fallbackLayoutDirection = LayoutDirection.Ltr,
      cacheSize = cacheSize,
    )
  }
}
