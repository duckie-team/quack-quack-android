/*
* Designed and developed by 2022 SungbinLand, Team Duckie
*
* [spec.kt] created by Ji Sungbin on 22. 8. 14. 오후 8:13
*
* Licensed under the MIT.
* Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
*/

package team.duckie.quackquack.ui.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.snap
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
public const val QuackDefaultAnimationMillis: Int = 250

/**
 * 덕키에서 사용할 애니메이션의 지속 시간
 *
 * 애니메이션 디버깅 용도로 수정 허용. Transition API 를 사용하는
 * 방법도 있지만 컴포즈에서 Preview 가 최적하게 돌아가지 않아
 * 수동 애니메이션 디버깅을 선호함.
 *
 * Playground 에서 자유로운 지속 시간 편집으로 쉬운 디버깅을 위해 public 으로 설정함
 */
public var QuackAnimationMillis: Int by mutableStateOf(QuackDefaultAnimationMillis)

/**
 * [isSnapshotMode] 의 receiver 입니다.
 */
internal object QuackAnimationSpec

/**
 * 스냅샷 캡처 환경에서는 애니메이션이 진행중인 현황을 캡처하지 못합니다.
 * 따라서 스냅샷 캡처 환경에서는 애니메이션이 진행 시간 없이
 * 바로 완료돼야 합니다. 이 값은 현재 컴포저블이 실행중인 환경이
 * 스냅샷 캡처 환경인지를 나타냅니다. 스냅샷 캡처는 QuackQuack 내부 테스트
 * 에서만 진행되므로 internal 로 제한합니다.
 */
internal var QuackAnimationSpec.isSnapshotMode by mutableStateOf(false)

/**
 * 덕키에서 사용할 애니메이션의 기본 스팩
 *
 * @return 덕키에서 사용할 애니메이션의 기본 스팩.
 * [isSnapshotMode] 에 따라 반환값이 달라집니다. false 라면
 * 덕키에서 사용하는 애니메이션 스팩인 [TweenSpec] 이 반환되고,
 * true 라면 [SnapSpec] 이 반환됩니다.
 *
 * @see isSnapshotMode
 */
@Stable
internal fun <T> quackAnimationSpec() = when (QuackAnimationSpec.isSnapshotMode) {
    true -> snap()
    else -> tween<T>(
        durationMillis = QuackAnimationMillis,
        easing = FastOutSlowInEasing,
    )
}
