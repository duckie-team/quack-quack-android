/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [surface.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:02
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.modifier.surface

/**
 * 모든 Quack 컴포넌트에서 최하위로 사용되는 컴포넌트입니다.
 * 컴포넌트의 기본 모양을 정의합니다.
 *
 * @param modifier 컴포저블에 적용할 [Modifier]
 * @param shape 컴포저블의 [Shape]
 * @param backgroundColor 컴포저블의 배경 색상
 * @param border 컴포저블의 테두리.
 * null 이 입력된다면 테두리를 설정하지 않습니다.
 * @param elevation 컴포저블의 그림자 고도
 * @param rippleEnabled 컴포저블이 클릭됐을 때 리플 효과를 적용할지 여부
 * @param rippleColor 컴포저블이 클릭됐을 때 리플 효과의 색상.
 * [rippleEnabled] 이 켜져 있을 때만 사용됩니다.
 * @param onClick 컴포저블이 클릭됐을 때 실행할 람다식
 * @param contentAlignment 컴포저블의 정렬 상태
 * @param content 표시할 컴포저블. BoxScope 를 receive 로 받습니다.
 */
@Composable
internal fun QuackSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: QuackColor = QuackColor.Unspecified,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: (() -> Unit)? = null,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .surface(
                shape = shape,
                backgroundColor = backgroundColor,
                border = border,
                elevation = elevation,
            )
            .quackClickable(
                onClick = onClick,
                rippleEnabled = rippleEnabled,
                rippleColor = rippleColor,
            )
            .animateContentSize(
                animationSpec = quackTween(),
            ),
        propagateMinConstraints = true,
        contentAlignment = contentAlignment,
        content = content,
    )
}
