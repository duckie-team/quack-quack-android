/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.util.runIf

/**
 * 덕키에서 기본적으로 사용할 항상 ripple 표시 옵션
 *
 * Playground 에서 [QuackAlwaysShowRipple] 편집 후 기본값으로 되돌리고
 * 싶을 때 기본값을 참조하기 위해 public 으로 공개합니다.
 */
public const val QuackDefaultAlwaysShowRipple: Boolean = false

/**
 * [Modifier.quackClickable] 을 통한 클릭 이벤트에는 모두 ripple 을 표시할 것인지 여부
 *
 * 터치 영역 디버깅 용도로 수정을 허용합니다.
 *
 * Playground 에서 자유로운 편집으로 쉬운 디버깅을 위해 public 으로 공개합니다.
 */
public var QuackAlwaysShowRipple: Boolean by mutableStateOf(
    value = QuackDefaultAlwaysShowRipple,
)

/**
 * 컴포저블에 Clickable 을 설정해 주는 [Modifier] 입니다.
 *
 * 이 [Modifier] 는 꽥꽥 컴포넌트의 중간 단계에서도 사용될 수 있으므로
 * 기본값이 정의됬습니다.
 *
 * @param rippleEnabled 리플을 설정할지 여부
 * @param rippleColor 표시될 리플의 색상.
 * null 이 들어오면 [Color.Unspecified] 를 사용합니다.
 * @param onClick 컴포넌트가 클릭됐을 때 실행할 람다식.
 * null 이 들어오면 clickable 을 설정하지 않습니다.
 * @param onLongClick 컴포넌트가 길게 클릭됐을 떄 실행할 람다식.
 * null 이 들어오면 long-clickable 을 설정하지 않습니다.
 *
 * @return clickable 속성이 적용된 [Modifier]
 */
// @Stable: onClick 구현이 동적으로 달라질 수 있음
public fun Modifier.quackClickable(
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor? = null,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)?,
): Modifier = runIf(
    condition = onClick != null,
) {
    composed {
        combinedClickable(
            onClick = onClick!!,
            onLongClick = onLongClick,
            indication = rememberRipple(
                color = rippleColor?.composeColor ?: Color.Unspecified,
            ).takeIf {
                QuackAlwaysShowRipple || rippleEnabled
            },
            interactionSource = remember { MutableInteractionSource() },
        )
    }
}
