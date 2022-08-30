/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [shape.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.constant

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

/**
 * Tag 컴포넌트의 Shape 모음
 */
@Immutable
internal object TagShape {
    /**
     * 원형 테두리의 Shape
     */
    @Stable
    internal val QuackRoundTagShape = RoundedCornerShape(18.dp)

    /**
     * 약간 둥근 테두리의 Shape
     */
    @Stable
    internal val QuackRectangleTagShape = RoundedCornerShape(12.dp)
}
