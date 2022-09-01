/*
* Designed and developed by 2022 SungbinLand, Team Duckie
*
* [spec.kt] created by Ji Sungbin on 22. 8. 14. 오후 8:13
*
* Licensed under the MIT.
* Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
*/

package team.duckie.quackquack.ui.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * 덕키에서 사용할 애니메이션의 기본 지속 시간
 *
 * Playground 에서 [QuackAnimationMillis] 편집 후 기본값으로 되돌리고
 * 싶을 때 기본값을 참조하기 위해 public 으로 설정함
 */
const val QuackDefaultAnimationMillis = 250

/**
 * 덕키에서 사용할 애니메이션의 지속 시간
 *
 * 애니메이션 디버깅 용도로 수정 허용. Transition API 를 사용하는
 * 방법도 있지만 컴포즈에서 Preview 가 최적하게 돌아가지 않아
 * 수동 애니메이션 디버깅을 선호함.
 *
 * Playground 에서 자유로운 지속 시간 편집으로 쉬운 디버깅을 위해 public 으로 설정함
 */
var QuackAnimationMillis by mutableStateOf(QuackDefaultAnimationMillis)

/**
 * 덕키에서 사용할 애니메이션 중 [Tween][TweenSpec] 의 기본 스팩
 *
 * @return 덕키에서 사용할 [TweenSpec] 의 기본 스팩
 */
@Stable
internal fun <T> quackTween() = tween<T>(
    durationMillis = QuackAnimationMillis,
    easing = FastOutSlowInEasing,
)
