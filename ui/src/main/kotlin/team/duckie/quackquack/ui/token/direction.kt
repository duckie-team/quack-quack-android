/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.token

import androidx.annotation.RestrictTo
import androidx.compose.runtime.Immutable

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface Direction

@Immutable
public enum class HorizontalDirection : Direction {
  Left, Right,
}

@Immutable
public enum class VerticalDirection : Direction {
  Top, Down,
}
