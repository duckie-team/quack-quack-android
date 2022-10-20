/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.border
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.util.runIf

/**
 * 선택적으로 [QuackBorder] 를 [Modifier] 에 적용합니다.
 *
 * @param enabled border 를 적용할지 여부.
 * 이 값이 true 일때만 border 가 적용됩니다.
 * @param border 적용할 [QuackBorder] 객체
 * @param shape border 를 적용할 [Shape] 값
 *
 * @return [enabled] 여부에 따라서 [QuackBorder] 를 적용해주는 [Modifier]
 * [enabled] 이 true 이고, [border] 값이 null 이 아닐때만 border 가 적용됩니다.
 */
@Stable
internal fun Modifier.applyQuackBorder(
    enabled: Boolean = true,
    border: QuackBorder?,
    shape: Shape = RectangleShape,
) = runIf(enabled && border != null) {
    border(
        border = border!!.asComposeBorder(),
        shape = shape,
    )
}
