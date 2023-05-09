/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import team.duckie.quackquack.util.DelicateQuackApi

/**
 * 꽥꽥에서 기본적으로 사용할 애니메이션의 기본 지속 시간
 *
 * Playground에서 [QuackAnimationMillis] 편집 후 기본값으로 되돌리고 싶을 때
 * 기본값으로 사용할 수 있습니다.
 */
public const val QuackDefaultAnimationMillis: Int = 150

/**
 * 꽥꽥에서 사용할 애니메이션의 지속 시간
 *
 * 애니메이션 디버깅 용도로 수정을 허용합니다. Transition API를 사용해 애니메이션을
 * 디버깅하는 방법도 있지만 컴포즈에서 Preview가 최적하게 돌아가지 않아 수동 애니메이션
 * 디버깅도 고려합니다.
 *
 * Playground에서 자유로운 지속 시간 편집으로 쉬운 디버깅이 가능합니다.
 */
public var QuackAnimationMillis: Int = QuackDefaultAnimationMillis

/** 꽥꽥에서 사용할 [AnimationSpec]의 정보 */
public object QuackAnimationSpec {
  /**
   * 일부 환경에서는 애니메이션이 없이 진행돼야 할 때도 있습니다. 이 값을 true로
   * 설정하면 모든 애니메이션을 무시합니다.
   *
   * **이 값의 변경은 모든 애니메이션에 영향을 미치므로 신중하게 사용해야 합니다.**
   */
  @DelicateQuackApi
  public var snapMode: Boolean = false

  /**
   * 꽥꽥에서 사용할 [애니메이션의 기본 스펙][AnimationSpec]
   *
   * @return 덕키에서 사용할 [AnimationSpec]. [snapMode] 에 따라 반환값이 달라집니다.
   * false라면 덕키에서 사용하는 애니메이션 스펙인 [TweenSpec]이 반환되고, true라면
   * [SnapSpec]이 반환됩니다.
   *
   * @see snapMode
   */
  @OptIn(DelicateQuackApi::class)
  public operator fun <T> invoke(): DurationBasedAnimationSpec<T> = when (snapMode) {
    true -> snap()
    else -> tween(
      durationMillis = QuackAnimationMillis,
      easing = LinearEasing,
    )
  }
}

/**
 * [QuackAnimationSpec.snapMode] 대신에 한 번만 선택적으로 애니메이션 여부를 결정하기
 * 위해 사용할 수 있습니다.
 *
 * @param useAnimation 애니메이션을 사용할지 여부
 *
 * @return [useAnimation] 여부에 따른 [DurationBasedAnimationSpec]
 */
@Stable
public fun <T> quackOptionalAnimationSpec(useAnimation: Boolean): DurationBasedAnimationSpec<T> {
  return when (useAnimation) {
    true -> QuackAnimationSpec()
    else -> snap()
  }
}
