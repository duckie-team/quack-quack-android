/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.border

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.quackAnimationSpec
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
@Suppress("MemberVisibilityCanBePrivate")
@Immutable
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
 * [QuackBorder] 의 변화에 애니메이션을 적용합니다.
 * [QuackBorder.width] 와 [QuackBorder.color] 모두 애니메이션이 적용됩니다.
 *
 * animationSpec 으로 항상 [quackAnimationSpec] 을 사용합니다.
 *
 * @param targetValue 애니메이션을 적용할 [QuackBorder]
 *
 * @return 애니메이션이 적용되고 있는 [QuackBorder] 객체
 */
@Composable
internal fun animatedQuackBorderAsState(
    targetValue: QuackBorder,
): QuackBorder {
    // `@SuppressLint("SpecifyAnimationSpec")` is not working
    fun <T> quackAnimationSpec(): AnimationSpec<T> =
        team.duckie.quackquack.ui.animation.quackAnimationSpec()

    val widthAnimationState by animateDpAsState(
        targetValue = targetValue.width,
        animationSpec = quackAnimationSpec(),
    )
    val colorAnimationState by animateQuackColorAsState(
        targetValue = targetValue.color,
    )
    return QuackBorder(
        width = widthAnimationState,
        color = colorAnimationState,
    )
}
