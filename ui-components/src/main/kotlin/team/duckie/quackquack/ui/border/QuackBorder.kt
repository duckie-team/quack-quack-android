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
import androidx.compose.ui.composed
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
public class QuackBorder(
    public val width: Dp = 1.dp,
    public val color: QuackColor,
) {
    /**
     * [color] 를 [Brush] 로 변환합니다.
     *
     * @return [color] 를 [Brush] 로 변환한 값
     */
    public val brush: SolidColor = SolidColor(
        value = color.composeColor,
    )

    /**
     * [QuackBorder] 를 [BorderStroke] 로 변환합니다.
     *
     * @return 변환된 [BorderStroke]
     */
    @Stable
    public fun asComposeBorder(): BorderStroke {
        return BorderStroke(
            width = width,
            brush = brush,
        )
    }
}

/**
 * 선택적으로 [QuackBorder] 를 [Modifier] 에 적용합니다.
 *
 * @param border 적용할 [QuackBorder] 객체
 * @param shape border 를 적용할 [Shape] 값
 *
 * @return [border] 가 적용된 [Modifier].
 * [border] 값이 null 이 아닐때만 [border] 가 적용됩니다.
 */
@Stable
public fun Modifier.applyAnimatedQuackBorder(
    border: QuackBorder?,
    shape: Shape = RectangleShape,
): Modifier = runIf(
    condition = border != null,
) {
    composed {
        val borderAnimation = animatedQuackBorderAsState(
            targetValue = border!!,
        )

        border(
            border = borderAnimation.asComposeBorder(),
            shape = shape,
        )
    }
}

/**
 * [QuackBorder] 의 변화에 애니메이션을 적용합니다.
 * [QuackBorder.width] 와 [QuackBorder.color] 모두 애니메이션이 적용됩니다.
 *
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param targetValue 애니메이션을 적용할 [QuackBorder]
 * @param widthAnimationfinishedListener [QuackBorder.width] 에 대한 애니메이션이 끝났을때 호출될 콜백
 * @param colorAnimationFinishedListener [QuackBorder.color] 에 대한 애니메이션이 끝났을때 호출될 콜백
 *
 * @return 애니메이션이 적용되고 있는 [QuackBorder] 객체
 */
@Composable
public fun animatedQuackBorderAsState(
    targetValue: QuackBorder,
    widthAnimationfinishedListener: ((dp: Dp) -> Unit)? = null,
    colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
): QuackBorder {
    val widthAnimationState by animateDpAsState(
        targetValue = targetValue.width,
        animationSpec = QuackAnimationSpec(),
        finishedListener = widthAnimationfinishedListener,
    )
    val colorAnimationState by animateQuackColorAsState(
        targetValue = targetValue.color,
        finishedListener = colorAnimationFinishedListener,
    )
    return QuackBorder(
        width = widthAnimationState,
        color = colorAnimationState,
    )
}
