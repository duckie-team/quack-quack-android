/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.interceptor

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.plugin.QuackPlugin
import team.duckie.quackquack.ui.plugin.QuackPluginLocal
import team.duckie.quackquack.ui.plugin.quackPluginLocal

/** 꽥꽥 컴포넌트가 그려지기 전에 특정 조건을 가로채서 변경할 수 있는 플러그인입니다. */
@Immutable
public sealed interface QuackInterceptorPlugin : QuackPlugin {
  /** 컴포넌트의 디자인 토큰을 가로채서 변경합니다. */
  @Immutable
  public fun interface DesignToken : QuackInterceptorPlugin {
    /**
     * 컴포넌트의 디자인 토큰을 가로채서 변경한 새로운 디자인 토큰을 반환합니다.
     *
     * @param componentName 플러그인이 적용될 컴포넌트의 이름
     * @param componentDesignToken 플러그인이 적용되고 있는 컴포넌트에 적용된 디자인 토큰.
     * 이 값은 이 플러그인이 적용되기 전에 미리 갖고 있던 디자인 토큰을 나타냅니다.
     * @param componentModifier 플러그인이 적용되고 있는 컴포넌트가 사용하고 있는 [Modifier]
     * @param quackPluginLocal [Modifier.quackPluginLocal]로 제공된 값
     */
    @Stable
    public fun intercept(
      componentName: String,
      componentDesignToken: Any,
      componentModifier: Modifier,
      quackPluginLocal: QuackPluginLocal?,
    ): Any
  }
}
