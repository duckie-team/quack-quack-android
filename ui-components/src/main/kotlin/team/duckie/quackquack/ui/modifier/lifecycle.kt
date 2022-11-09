/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow

/**
 * 컴포저블의 생명주기 상태에 따라 주어진 콜백을 실행하는
 * [Modifier] 입니다.
 *
 * @param key [onDisappear] 상태를 재설정할 고유 키. 기본 값은 [Unit] 입니다.
 * @param onAppear 컴포저블이 보여졌을 떄 실행할 콜백
 * @param onDisappear 컴포저블이 사라졌을 때 실행할 콜백
 *
 * @return 생명주기에 따라 주어진 콜백을 실행하는 [Modifier]
 */
@Stable
internal inline fun Modifier.lifecycle(
    key: Any = Unit,
    crossinline onAppear: (offset: Offset) -> Unit,
    crossinline onDisappear: () -> Unit,
) = composed {
    onGloballyPositioned { coordinates ->
        onAppear(coordinates.positionInWindow())
    }
    DisposableEffect(
        key1 = key,
    ) {
        onDispose(
            onDisposeEffect = onDisappear,
        )
    }
    this
}
