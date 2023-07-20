/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalContracts::class)

package team.duckie.quackquack.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import team.duckie.quackquack.util.DpSize

/** 현재 font scale을 람다로 제공합니다. */
@SuppressLint("ComposableNaming")
@Composable
@ReadOnlyComposable
public inline fun <T> currentFontScale(content: (fontScale: Float) -> T): T {
  contract { callsInPlace(content, InvocationKind.EXACTLY_ONCE) }
  val configuration = LocalConfiguration.current
  return content(configuration.fontScale)
}

/**
 * [Modifier]를 이용하여 컴포저블의 사이즈를 font scale aware 하게 설정합니다.
 *
 * 모든 컴포저블에 사용할 수 있지만 아이콘 컴포저블에 사용함을 권장합니다.
 *
 * @param baseline 기준값으로 사용할 컴포넌트 사이즈. 아이콘 컴포저블에 사용함을
 * 권장으로 개발됐기에 기본값으로 `24.dp`를 사용합니다.
 */
@Stable
public fun Modifier.fontScaleAwareIconSize(baseline: Dp = 24.dp): Modifier {
  return composed {
    currentFontScale { fontScale ->
      // value class라 인스턴스 생성 비용 적음 (remember 보다 저렴함)
      val defaultSize = DpSize(all = baseline)
      size(defaultSize * fontScale)
    }
  }
}
