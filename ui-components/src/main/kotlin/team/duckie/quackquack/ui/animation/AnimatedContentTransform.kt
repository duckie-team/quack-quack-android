/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AnimatedContentTransform.kt] created by Ji Sungbin on 22. 9. 2. 오전 3:55
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize

/**
 * 컴포저블의 상태에 변화가 있을 때 자동으로 해당 상태에 맞춰
 * 애니메이션을 적용하는 컨테이너 입니다.
 *
 * @param modifier 이 컨테이너에 적용할 [Modifier]
 * @param targetState 변화를 감지할 컴포저블의 상태값
 * @param animationSpec 컴포저블 애니메이션에 사용할 [AnimationSpec]
 * @param content 상태 변화를 감지할 컴포저블 컨텐츠.
 * 인자로는 애니메이션이 적용되고 있는 [targetState] 의 값이 들어옵니다.
 * **애니메이션이 적용되기 위해선 필수로 이 값을 상태로 적용해야 합니다.**
 */
@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun <T> AnimatedContentTransform(
    modifier: Modifier = Modifier,
    targetState: T,
    animationSpec: AnimationSpec<Any> = quackAnimationSpec(),
    content: @Composable AnimatedVisibilityScope.(animatedTargetState: T) -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = targetState,
        transitionSpec = {
            fadeIn(
                animationSpec = animationSpec as FiniteAnimationSpec<Float>,
            ) with fadeOut(
                animationSpec = animationSpec as FiniteAnimationSpec<Float>,
            ) using SizeTransform(
                clip = false,
                sizeAnimationSpec = { _, _ ->
                    animationSpec as FiniteAnimationSpec<IntSize>
                },
            )
        },
        content = content,
    )
}
