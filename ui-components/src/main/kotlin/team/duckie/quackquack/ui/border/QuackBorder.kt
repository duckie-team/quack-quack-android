/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.border

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.util.runIf

/**
 * [BorderStroke] 에서 [BorderStroke.brush] 값을 [QuackColor] 로 받기 위한
 * [BorderStroke] 의 wrapper 클래스 입니다.
 *
 * @param width border 의 굵기
 * @param color border 의 색상
 */
internal class QuackBorder(
    val width: Dp = 1.dp,
    val color: QuackColor,
) {
    /**
     * [color] 를 [Brush] 로 변환합니다.
     *
     * @return [color] 를 [Brush] 로 변환한 값
     */
    val brush = SolidColor(
        value = color.composeColor,
    )

    /**
     * [QuackBorder] 를 [BorderStroke] 로 변환합니다.
     *
     * @return 변환된 [BorderStroke]
     */
    @Stable
    fun asComposeBorder() = BorderStroke(
        width = width,
        brush = brush,
    )
}

/**
 * 선택적으로 [QuackBorder] 를 [Modifier] 에 적용합니다.
 *
 * @param enabled border 를 적용할지 여부.
 * 이 값이 true 일때만 border 가 적용됩니다.
 * @param border 적용할 [QuackBorder] 객체
 * @param shape border 를 적용할 [Shape] 값
 *
 * @return [enabled] 여부에 따라서 [QuackBorder] 를 적용해주는 [Modifier].
 * [enabled] 이 true 이고, [border] 값이 null 이 아닐때만 border 가 적용됩니다.
 */
@Stable
internal fun Modifier.applyQuackBorder(
    enabled: Boolean = true,
    border: QuackBorder?,
    shape: Shape = RectangleShape,
) = runIf(
    condition = enabled && border != null,
) {
    border(
        border = border!!.asComposeBorder(),
        shape = shape,
    )
}

/**
 * [QuackBorder] 의 변화에 애니메이션을 적용합니다.
 * [QuackBorder.width] 와 [QuackBorder.color] 모두 애니메이션이 적용됩니다.
 *
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param targetValue 애니메이션을 적용할 [QuackBorder]
 *
 * @return 애니메이션이 적용되고 있는 [QuackBorder] 객체
 */
@Composable
internal fun animatedQuackBorderAsState(
    targetValue: QuackBorder,
): QuackBorder {
    val widthAnimationState by animateDpAsState(
        targetValue = targetValue.width,
        animationSpec = QuackAnimationSpec(),
    )
    val colorAnimationState by animateQuackColorAsState(
        targetValue = targetValue.color,
    )
    return QuackBorder(
        width = widthAnimationState,
        color = colorAnimationState,
    )
}
