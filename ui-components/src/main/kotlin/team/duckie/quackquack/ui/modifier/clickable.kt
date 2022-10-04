/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.util.runIf

/**
 * 컴포저블에 Clickable 을 설정해 주는 Modifier 입니다.
 *
 * 이 Modifier 는 Quack 컴포넌트의 중간 단계에서도 사용될 수 있으므로
 * 기본값이 정의됬습니다.
 *
 * @param onClick 컴포넌트가 클릭됐을 때 실행할 람다식.
 * null 이 들어오면 clickable 을 설정하지 않습니다.
 * @param rippleEnabled click effect 로 리플을 설정할지 여부
 * @param rippleColor click effect 로 보일 리플의 색상.
 * null 이 들어오면 [Color.Unspecified] 를 사용합니다.
 *
 * @return clickable 속성이 적용된 [Modifier]
 */
@Stable
internal fun Modifier.quackClickable(
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor? = null,
    onClick: (() -> Unit)?,
) = runIf(onClick != null) {
    composed {
        clickable(
            onClick = onClick!!,
            indication = rememberRipple(
                color = rippleColor?.composeColor ?: Color.Unspecified,
            ).takeIf {
                rippleEnabled
            },
            interactionSource = remember { MutableInteractionSource() },
        )
    }
}
