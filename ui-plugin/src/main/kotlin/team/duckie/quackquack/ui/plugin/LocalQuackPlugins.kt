/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * 꽥꽥 플러그인 저장소를 제공합니다.
 * 만약 등록된 플러그인이 없다면 [EmptyQuackPlugins]를 반환합니다.
 * 플러그인을 등록하려면 [rememberQuackPlugins]를 사용하세요.
 */
public val LocalQuackPlugins: ProvidableCompositionLocal<QuackPlugins> = staticCompositionLocalOf {
  EmptyQuackPlugins
}
