/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.token

import androidx.annotation.RestrictTo
import androidx.compose.runtime.Immutable

/** 방향을 정의하는 클래스임을 나타냅니다. */
@Immutable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface Direction

/**
 * horizontal 환경에서 사용 가능한 방향을 정의합니다.
 *
 * @property Left
 * @property Right
 */
public enum class HorizontalDirection : Direction {
  Left, Right,
}

/**
 * vertical 환경에서 사용 가능한 방향을 정의합니다.
 *
 * @property Top
 * @property Bottom
 */
public enum class VerticalDirection : Direction {
  Top, Bottom,
}
