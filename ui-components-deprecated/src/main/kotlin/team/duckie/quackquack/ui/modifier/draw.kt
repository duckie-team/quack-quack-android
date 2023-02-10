/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import team.duckie.quackquack.ui.animation.QuackOptionalAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsStateInternal

/**
 * 컴포넌트와 함께 라인을 그리기 위해 사용됩니다.
 *
 * 라인의 두께와 색상에 변화가 있으면 자동으로 애니메이션이 적용됩니다.
 * **오프셋에는 애니메이션이 자동으로 적용되지 않습니다.**
 *
 * @param thickness 라인의 두께
 * @param color 라인의 색상
 * @param startOffsetProvider 라인이 drawing 될 시작 오프셋을 계산하는 람다
 * @param endOffsetProvider 라인이 drawing 될 끝 오프셋을 계산하는 람다
 * @param colorAnimationFinishedListener [color] 애니메이션이 끝났을 따 호출되는 람다
 * @param thicknessAnimationFinishedListener [thickness] 애니메이션이 끝났을 때 호출되는 람다
 * @param useAnimation 애니메이션을 적용할지 여부. QuackTab 을 그리는데 필요해서 추가로 값을 받습니다.
 *
 * @return 주어진 오프셋에 라인을 그리기 위한 [drawWithContent] 가 적용된 [Modifier]
 */
// @Stable: startOffsetProvider 와 endOffsetProvider 의 구현이 동적으로 달라질 수 있음
internal fun Modifier.drawAnimatedLine(
    thickness: Dp,
    color: QuackColor,
    startOffsetProvider: Density.(size: Size) -> Offset,
    endOffsetProvider: Density.(size: Size) -> Offset,
    colorAnimationFinishedListener: ((color: QuackColor) -> Unit)? = null,
    thicknessAnimationFinishedListener: ((thickness: Dp) -> Unit)? = null,
    useAnimation: Boolean = true,
) = composed {
    if (thickness == Dp.Hairline) {
        return@composed this
    }

    /**
     * [QuackOptionalAnimationSpec] 을 델리게이트 합니다.
     * `SpecifyAnimationSpec` 린트를 억제하기 위한 함수입니다.
     *
     * @return [QuackOptionalAnimationSpec] 을 반환합니다.
     */
    @Suppress("FunctionName")
    fun <T> QuackAnimationSpec() = QuackOptionalAnimationSpec<T>(
        useAnimation = useAnimation,
    )

    val animatedColor by animateQuackColorAsStateInternal(
        targetValue = color,
        animationSpec = QuackAnimationSpec(),
        finishedListener = colorAnimationFinishedListener,
    )
    val animatedThickness by animateDpAsState(
        targetValue = thickness,
        animationSpec = QuackAnimationSpec(),
        finishedListener = thicknessAnimationFinishedListener,
    )

    drawWithContent {
        drawContent()
        drawLine(
            color = animatedColor.composeColor,
            start = startOffsetProvider(
                /* size = */
                size,
            ),
            end = endOffsetProvider(
                /* size = */
                size,
            ),
            strokeWidth = animatedThickness.toPx(),
        )
    }
}
