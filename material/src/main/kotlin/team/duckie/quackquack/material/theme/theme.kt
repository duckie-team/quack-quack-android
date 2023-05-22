/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.quackquack.material.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import team.duckie.quackquack.material.QuackColor

/**
 * 꽥꽥 컴포넌트에서 사용할 커서 색상을 제공합니다.
 *
 * @see QuackTextFieldTheme
 */
public val LocalQuackTextFieldTheme: ProvidableCompositionLocal<QuackTextFieldTheme> =
  staticCompositionLocalOf { DefaultTextFieldTheme }

/**
 * 꽥꽥에서 사용하는 컴포저블 테마를 제공합니다. 이 테마에서는 다음과 같을 작업을 진행합니다.
 *
 * 1. OverscrollEffect 제거
 * 2. 꽥꽥 컴포넌트에서 사용할 커서(cursor) 테마 제공
 *
 * @param content 꽥꽥 디자인에 맞게 표시할 컴포저블 컨텐츠
 */
@Composable
public fun QuackTheme(content: @Composable () -> Unit) {
  CompositionLocalProvider(
    LocalOverscrollConfiguration provides null,
    LocalTextSelectionColors provides TextSelectionColors(
      handleColor = QuackColor.DuckieOrange.value,
      backgroundColor = QuackColor.DuckieOrange.change(alpha = 0.2f).value,
    ),
    LocalQuackTextFieldTheme provides DefaultTextFieldTheme,
    content = content,
  )
}

/** 꽥꽥의 TextField 관련 컴포넌트에 사용할 테마를 지정합니다. */
@Immutable
public interface QuackTextFieldTheme {
  /**
   * 꽥꽥 컴포넌트에서 사용할 커서 색상
   *
   * 기본값으로 [QuackColor.Black]를 사용합니다.
   */
  public val cursorColor: QuackColor
}

/** [QuackTextFieldTheme]의 덕키 기본값 */
@Immutable
public object DefaultTextFieldTheme : QuackTextFieldTheme {
  override val cursorColor: QuackColor = QuackColor.DuckieOrange
}
