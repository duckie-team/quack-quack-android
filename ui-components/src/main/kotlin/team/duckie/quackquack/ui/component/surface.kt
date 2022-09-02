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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.quackSpec
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.animateQuackBorderAsState
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.animateQuackColorAsState
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.modifier.surface

/**
 * 모든 Quack 컴포넌트에서 최하위로 사용되는 컴포넌트입니다.
 * 컴포넌트의 기본 모양을 정의합니다.
 *
 * **애니메이션 가능한 모든 요소들에는 자동으로 애니메이션이 적용됩니다.**
 * 현재 애니메이션이 적용되는 요소들은 다음과 같습니다.
 *
 * [backgroundColor], [border]
 *
 * @param modifier 컴포저블에 적용할 [Modifier]. 기본값은
 * 수정 없는 본질의 Modifier 입니다.
 * @param shape 컴포저블의 [Shape]. 기본값은 [RectangleShape]
 * 입니다.
 * @param backgroundColor 컴포저블의 배경 색상. 기본값은
 * 정의되지 않은 색상인 [QuackColor.Unspecified] 입니다.
 * @param border 컴포저블의 테두리.
 * null 이 입력된다면 테두리를 설정하지 않습니다.
 * 기본값은 null 입니다.
 * @param elevation 컴포저블의 그림자 고도.
 * 기본값은 0 입니다. 즉, 그림자를 사용하지 않습니다.
 * @param rippleEnabled 컴포저블이 클릭됐을 때 리플 효과를 적용할지 여부.
 * 기본값은 true 입니다.
 * @param rippleColor 컴포저블이 클릭됐을 때 리플 효과의 색상.
 * 기본값은 정해지지 않은 색상인 [QuackColor.Unspecified] 입니다.
 * [rippleEnabled] 이 켜져 있을 때만 사용됩니다.
 * @param onClick 컴포저블이 클릭됐을 때 실행할 람다식
 * null 이 입력된다면 클릭 이벤트를 추가하지 않습니다.
 * 기본값은 null 입니다. 즉, 클릭 이벤트를 추가하지 않습니다.
 * @param contentAlignment 컴포저블의 정렬 상태. 기본값은
 * Center 입니다.
 * @param content 표시할 컴포저블. BoxScope 를 receive 로 받습니다.
 */
@Composable
// @NonRestartableComposable; 여기서 사용하는 Box 는 inline 됨
internal fun QuackSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: QuackColor = QuackColor.Unspecified,
    border: QuackBorder? = null,
    elevation: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: (() -> Unit)? = null,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    val backgroundColorAnimation by animateQuackColorAsState(
        targetValue = backgroundColor,
    )
    val borderAnimation = when (border != null) {
        true -> animateQuackBorderAsState(
            targetValue = border,
        )
        else -> null
    }

    Box(
        modifier = modifier
            .surface(
                shape = shape,
                backgroundColor = backgroundColorAnimation,
                border = borderAnimation?.value?.asComposeBorder(),
                elevation = elevation,
            )
            .quackClickable(
                onClick = onClick,
                rippleEnabled = rippleEnabled,
                rippleColor = rippleColor,
            )
            .animateContentSize(
                animationSpec = quackSpec(),
            ),
        propagateMinConstraints = true,
        contentAlignment = contentAlignment,
        content = content,
    )
}
