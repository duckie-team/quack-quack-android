/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackBorder.kt] created by Ji Sungbin on 22. 9. 1. 오후 10:27
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.border

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.animateQuackAsState
import team.duckie.quackquack.ui.animation.quackSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState

/**
 * [BorderStroke] 에서 [BorderStroke.brush] 값을 [QuackColor] 로 받기 위한
 * [BorderStroke] 의 wrapper 클래스 입니다.
 *
 * @param width border 의 굵기
 * @param color border 의 색상
 */
// 기본 값으로 정해진게 없기에 internal constructor
@Immutable
internal class QuackBorder(
    val width: Dp = 1.dp,
    val color: QuackColor,
) {
    /**
     * [QuackBorder] 를 [BorderStroke] 로 변환합니다.
     *
     * @return 변환된 [BorderStroke]
     */
    @Stable
    fun asComposeBorder() = BorderStroke(
        width = width,
        brush = SolidColor(
            value = color.value,
        ),
    )
}

/**
 * [QuackBorder] 의 변화에 애니메이션을 적용합니다.
 * [QuackBorder.width] 와 [QuackBorder.color] 모두
 * 애니메이션이 적용됩니다.
 *
 * @param targetValue 애니메이션을 적용할 [QuackBorder]
 * @param animationSpec 애니메이션에 사용할 [AnimationSpec]
 */
@Suppress("UNCHECKED_CAST")
@Composable
internal fun animateQuackBorderAsState(
    targetValue: QuackBorder,
    animationSpec: AnimationSpec<Any> = quackSpec(),
): State<QuackBorder> {
    val widthAnimationState = animateDpAsState(
        targetValue = targetValue.width,
        animationSpec = animationSpec as AnimationSpec<Dp>,
    )
    val colorAnimationState = animateQuackColorAsState(
        targetValue = targetValue.color,
        animationSpec = animationSpec as AnimationSpec<QuackColor>,
    )
    return animateQuackAsState(
        initialValue = targetValue,
        animationStates = listOf(
            widthAnimationState,
            colorAnimationState,
        ),
        targetBuilder = { animationFlows ->
            val (width, color) = animationFlows
            QuackBorder(
                width = width as Dp,
                color = color as QuackColor,
            )
        },
    )
}
