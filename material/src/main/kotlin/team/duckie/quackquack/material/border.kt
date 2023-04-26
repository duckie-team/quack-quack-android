/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.quackquack.material

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.util.applyIf

/**
 * [BorderStroke]에서 [BorderStroke.brush] 값을 [QuackColor]로 받기 위한
 * [BorderStroke]의 래퍼입니다.
 *
 * @param thickness border의 굵기
 * @param color border의 색상
 */
@Immutable
public class QuackBorder(
    public val thickness: Dp = 1.dp,
    public val color: QuackColor,
) {
    /**
     * [color] 를 [Brush]로 변환합니다.
     */
    @Stable
    public val brush: SolidColor = SolidColor(value = color.value)

    /**
     * [QuackBorder] 를 [BorderStroke]로 변환합니다.
     */
    @Stable
    public fun asComposeBorder(): BorderStroke {
        return BorderStroke(width = thickness, brush = brush)
    }
}

/**
 * 선택적으로 [QuackBorder]를 [Modifier]에 적용합니다.
 *
 * @param border 적용할 [QuackBorder]
 * @param shape border에 적용할 [Shape]
 *
 * @return [border]가 적용된 [Modifier].
 * [border] 값이 null이 아닐때만 [border]가 적용됩니다.
 */
@Stable
public fun Modifier.quackBorder(
    border: QuackBorder?,
    shape: Shape = RectangleShape,
): Modifier = applyIf(border != null) {
    inspectable(
        inspectorInfo = debugInspectorInfo {
            name = "quackBorder"
            properties["border"] = border
            properties["shape"] = shape
        },
    ){
        border(
            border = border!!.asComposeBorder(),
            shape = shape,
        )
    }
}
