/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable

private val IndicateDotSpacedBy = 6.dp

/**
 * 덕키의 Indicator 를 그립니다.
 *
 * position 간 이동에 애니메이션이 적용됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param size 전체 position 의 사이즈. **1 부터 시작합니다.**
 * @param currentPosition 현재 position index
 * @param onClick Indicator Dot 이 클릭됐을 때 호출될 람다.
 * 인자로 클릭된 position 이 넘어옵니다.
 */
@Composable
public fun QuackIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentPosition: Int,
    onClick: ((position: Int) -> Unit)? = null,
) {
    val density = LocalDensity.current
    val highlightDotOffset by animateIntOffsetAsState(
        targetValue = with(density) {
            IntOffset(
                // x 오프셋은 0 부터 시작함
                // x 오프셋에 IndicateDot 의 x 값을 더해줘야 함
                x = (IndicateDotSpacedBy + 4.dp).roundToPx() * currentPosition,
                y = 0,
            )
        },
        animationSpec = QuackAnimationSpec(),
    )

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.zIndex(1f),
            horizontalArrangement = Arrangement.spacedBy(IndicateDotSpacedBy)
        ) {
            repeat(size) { position ->
                QuackIndicatorDot(
                    color = QuackColor.Gray3,
                    position = position,
                    onClick = onClick,
                )
            }
        }
        QuackIndicatorDot(
            modifier = Modifier
                .zIndex(2f)
                .offset { highlightDotOffset },
            color = QuackColor.Gray1,
            position = currentPosition,
            onClick = onClick,
        )
    }
}

/**
 * [QuackIndicator] 에 사용되는 Dot 을 그립니다.
 */
@Composable
private fun QuackIndicatorDot(
    modifier: Modifier = Modifier,
    color: QuackColor,
    position: Int,
    onClick: ((position: Int) -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(
                color = color.composeColor,
                shape = CircleShape,
            )
            .quackClickable(
                onClick = { onClick!!.invoke(position) }.takeIf { onClick != null },
            ),
    )
}
