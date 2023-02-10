/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import team.duckie.quackquack.ui.color.QuackColor

/**
 * Quack 에서 사용될 배경 dimmed 를 구현합니다.
 * **전체 화면을 [Box] 로 채운 후**, [color] 로 배경 색상을 지정하여 구현합니다.
 *
 * 전체 화면을 주어진 dimmed color 로 꽉 채우는 방식이기 때문에 이 컴포저블을 사용하는
 * 루트 컴포저블의 레이아웃 범위가 `match_parent` 이여야 합니다.
 *
 * @param zIndex dimmed 레이어의 z-index
 * @param enabled dimmed 를 활성화할지 여부
 */
@Composable
internal fun QuackBackgroundDimmed(
    zIndex: Float = 0f,
    enabled: Boolean,
) {
    if (enabled) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = QuackColor.Dimmed.composeColor,
                )
                .zIndex(
                    zIndex = zIndex,
                ),
        )
    }
}
