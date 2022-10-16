/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * [LocalOverscrollConfiguration] 을 null 로 제공합니다.
 * Overscroll effect 를 제거하기 위해 사용됩니다.
 *
 * @param content Overscroll 이 없는 환경에서 표시할 컴포저블 컨텐츠
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NoOverscrollEffect(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        content = content,
    )
}
