/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class)

package team.duckie.quackquack.ui.plugin.interceptor.textfield

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontFamily
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.TextFieldColorMarker
import team.duckie.quackquack.ui.TextFieldStyleMarker
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.plugin.interceptor.QuackInterceptorPlugin

/**
 * 꽥꽥에서 제공되는 TextField 컴포넌트의 이름 모음.
 * 여기에 포함된 컴포넌트에만 [QuackTextFieldFontFamilyRemovalPlugin]이 적용됩니다.
 */
private val quackTextFieldComponentNames =
  listOf("QuackDefaultTextField", "QuackFilledTextField", "QuackOutlinedTextField")

/**
 * `QuackTextField`에 적용될 디자인 토큰에서 `fontFamily`의 값을 [FontFamily.Default]로 변경합니다.
 * 이 플러그인은 [#761](https://github.com/duckie-team/quack-quack-android/issues/761)의 임시 해결책으로 제공됩니다.
 */
@Stable
public val QuackTextFieldFontFamilyRemovalPlugin: QuackInterceptorPlugin.DesignToken =
  QuackInterceptorPlugin.DesignToken { componentName, componentDesignToken, _, _ ->
    if (quackTextFieldComponentNames.contains(componentName)) {
      @Suppress("UNCHECKED_CAST")
      componentDesignToken as QuackTextFieldStyle<TextFieldStyleMarker, TextFieldColorMarker>
      object : QuackTextFieldStyle<TextFieldStyleMarker, TextFieldColorMarker> by componentDesignToken {
        override val typography =
          with(componentDesignToken.typography) {
            QuackTypography(
              color = color,
              size = size,
              weight = weight,
              letterSpacing = letterSpacing,
              lineHeight = lineHeight,
              textAlign = textAlign,
              fontFamily = FontFamily.Default,
            )
          }
      }
    } else {
      componentDesignToken
    }
  }
