/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin.interceptor

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.plugin.EmptyQuackPlugins
import team.duckie.quackquack.ui.plugin.LocalQuackPlugins
import team.duckie.quackquack.ui.plugin.QuackPluginLocal
import team.duckie.quackquack.ui.plugin.lastByTypeOrNull
import team.duckie.quackquack.util.modifier.getElementByTypeOrNull

/** 현재 실행 중인 call-site의 메서드 이름을 반환합니다. */
@PublishedApi
internal val currentMethodName: String
  // https://stackoverflow.com/a/32329165/14299073
  inline get() = Thread.currentThread().stackTrace[1].methodName

/** intercept된 스타일이 기존에 제공된 스타일과 다른 타입일 때 throw할 에러 메시지 */
@PublishedApi
@VisibleForTesting
internal const val InterceptedStyleTypeExceptionMessage: String =
  "The intercepted style is of a different type than the original style."

/**
 * 주어진 [디자인 토큰][style]에 [QuackInterceptorPlugin.DesignToken] 플러그인을 적용한 후,
 * 새로운 [디자인 토큰][Style]을 반환합니다.
 *
 * 만약 [기존 디자인 토큰][style]과 새로운 디자인 토큰의 타입이 다르다면 [IllegalStateException]이
 * 발생합니다. 새로운 디자인 토큰의 타입은 [Style]과 일치할 것이라 가정합니다.
 *
 * @param Style intercept의 결과로 생성될 디자인 토큰의 타입
 * @param style 기존에 적용된 디자인 토큰
 * @param modifier 현재 컴포넌트에 적용된 [Modifier]
 */
@Composable
public inline fun <reified Style> rememberInterceptedStyleSafely(style: Any, modifier: Modifier): Style {
  val localQuackPlugins = LocalQuackPlugins.current

  return remember(localQuackPlugins, style, modifier) {
    localQuackPlugins.takeIf { it != EmptyQuackPlugins }?.let { plugins ->
      val interceptorPlugin = plugins.lastByTypeOrNull<QuackInterceptorPlugin.DesignToken>()
        ?: return@let null
      val quackPluginLocal = modifier.getElementByTypeOrNull<QuackPluginLocal>()

      interceptorPlugin
        .intercept(
          componentName = currentMethodName,
          componentDesignToken = style,
          componentModifier = modifier,
          quackPluginLocal = quackPluginLocal,
        )
    } ?: style
  }.also { interceptedStyle ->
    check(interceptedStyle is Style, lazyMessage = ::InterceptedStyleTypeExceptionMessage)
  } as Style
}
