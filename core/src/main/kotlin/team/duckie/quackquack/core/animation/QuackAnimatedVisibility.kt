/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.core.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.core.material.QuackAnimationSpec

/**
 * 컴포저블의 visiblilty 변화에 애니메이션을 적용하는 컨테이너 입니다.
 *
 * @param visible visibility 여부
 * @param label 내부 구현인 Transition API에 사용될 label
 * @param otherEnterAnimation 추가로 더할 enter 애니메이션
 * @param otherExitAnimation 추가로 더할 exit 애니메이션
 * @param content visiblilty 애니메이션이 적용될 컴포저블 컨텐츠
 */
@Composable
public fun QuackAnimatedVisibility(
    modifier: Modifier = Modifier,
    visible: Boolean,
    label: String = "AnimatedVisibility",
    otherEnterAnimation: EnterTransition? = null,
    otherExitAnimation: ExitTransition? = null,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(QuackAnimationSpec()).optionalAdd(otherEnterAnimation),
        exit = fadeOut(QuackAnimationSpec()).optionalAdd(otherExitAnimation),
        label = label,
        content = content,
    )
}

private fun EnterTransition.optionalAdd(additional: EnterTransition?): EnterTransition {
    return if (additional != null) plus(additional) else this
}

private fun ExitTransition.optionalAdd(additional: ExitTransition?): ExitTransition {
    return if (additional != null) plus(additional) else this
}
