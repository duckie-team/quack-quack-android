/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * 꽥꽥에서 사용하는 컴포저블 테마를 제공합니다.
 * 이 테마에서는 다음과 같을 작업을 진행합니다.
 *
 * 1. OverscrollEffect 제거
 *
 * @param content 꽥꽥 디자인에 맞게 표시할 컴포저블 컨텐츠
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun QuackTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        content = content,
    )
}
