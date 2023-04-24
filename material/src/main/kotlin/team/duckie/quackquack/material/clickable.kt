/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.quackquack.material

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

/**
 * 덕키에서 기본적으로 사용할 항상 ripple 표시 옵션
 *
 * Playground에서 [QuackAlwaysShowRipple]편집 후 기본값으로 되돌리고
 * 싶을 때 기본값을 참조하기 위해 public으로 공개합니다.
 */
public const val QuackDefaultAlwaysShowRipple: Boolean = false

/**
 * [Modifier.quackClickable]로 실행된 클릭 이벤트에는 모두 ripple을 표시할 것인지 여부
 *
 * 터치 영역 디버깅 용도로 수정을 허용합니다.
 *
 * Playground에서 자유로운 편집으로 쉬운 디버깅을 위해 public 으로 공개합니다.
 */
public var QuackAlwaysShowRipple: Boolean by mutableStateOf(QuackDefaultAlwaysShowRipple)

/**
 * [Modifier.clickable]에 다양한 옵션을 부여합니다.
 *
 * @param rippleColor 표시될 리플의 색상.
 * null이들어오면 [Color.Unspecified]를 사용합니다.
 * @param rippleEnabled 리플을 설정할지 여부
 * @param onClick 컴포넌트가 클릭됐을 때 실행할 람다식.
 * null이 들어오면 빈 람다를 사용합니다. (필수 옵션)
 * @param onLongClick 컴포넌트가 길게 클릭됐을 떄 실행할 람다식.
 * null이 들어오면 long-clickable을 설정하지 않습니다.
 *
 * @return [Modifier.clickable] 속성이 적용된 [Modifier]
 */
@Stable
public fun Modifier.quackClickable(
    rippleColor: QuackColor? = null,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
): Modifier = composed {
    val ripple = rememberRipple(color = rippleColor?.value ?: Color.Unspecified)
    combinedClickable(
        onClick = onClick ?: {},
        onLongClick = onLongClick,
        indication = ripple.takeIf { QuackAlwaysShowRipple || rippleEnabled },
        interactionSource = remember { MutableInteractionSource() },
    )
}
