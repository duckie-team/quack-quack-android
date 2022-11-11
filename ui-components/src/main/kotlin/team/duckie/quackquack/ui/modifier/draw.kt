/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState

/**
 * 컴포넌트와 함께 라인을 그리기 위해 사용됩니다.
 * 모든 필드는 리컴포지션 스킵을 위해 값을 바로 받는게 아닌 람다를 통해 받습니다.
 *
 * 라인의 두께와 색상에 변화가 있으면 자동으로 애니메이션이 적용됩니다.
 *
 * @param thicknessProvider 라인의 두께
 * @param colorProvider 라인의 색상
 * @param startOffsetProvider 라인이 drawing 될 시작 오프셋을 계산하는 람다
 * @param endOffsetProvider 라인이 drawing 될 끝 오프셋을 계산하는 람다

 * @return 주어진 오프셋에 라인을 그리기 위한 [drawWithContent] 가 적용된 [Modifier]
 */
@Stable
internal fun Modifier.drawAnimatedLine(
    thicknessProvider: () -> Dp,
    colorProvider: () -> QuackColor,
    startOffsetProvider: Density.(size: Size) -> Offset,
    endOffsetProvider: Density.(size: Size) -> Offset,
) = composed {
    val thickness = thicknessProvider()
    if (thickness == Dp.Hairline) return@composed this
    val color = colorProvider()
    val animatedColor by animateQuackColorAsState(
        targetValue = color,
    )
    val animatedThickness by animateDpAsState(
        targetValue = thickness,
        animationSpec = QuackAnimationSpec(),
    )

    drawWithContent {
        val strokeWidth = animatedThickness.toPx()
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
            strokeWidth = strokeWidth,
        )
    }
}
