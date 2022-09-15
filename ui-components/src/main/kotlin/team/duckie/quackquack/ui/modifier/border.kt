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
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor

/**
 * 컴포저블에 Border 가 필요할 때만 보여주고 싶을 때 사용
 *
 * @param isSelected Border 를 보여주는 상태값
 * @param width Border 의 두께
 * @param color Border 의 색깔
 *
 * isSelected 가 true 일때만 param 으로 넘겨준 border 가 생성됨
 *
 * @return Border 가 필요할때만 표시해주는 Modifier
 */
@Stable
internal fun Modifier.quackBorderOrNull(
    isSelected: Boolean,
    width: Dp = 1.dp,
    color: QuackColor = QuackColor.DuckieOrange
) = when (isSelected) {
    true -> composed {
        border(
            border = QuackBorder(
                width = width,
                color = color,
            ).asComposeBorder()
        )
    }
    else -> this
}
