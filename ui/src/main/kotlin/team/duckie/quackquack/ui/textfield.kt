/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.annotation.IntRange
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.token.IconRole
import team.duckie.quackquack.ui.token.VerticalDirection

@Immutable
public enum class TextFieldState {
  Error, Success, Default,
}

@Immutable
public enum class PlaceholderStrategy {
  Always, Hidable,
}

@Stable
private data class TextFieldIconData(
  val icon: QuackIcon,
  val tint: QuackColor,
  val tintGetter: ((text: String) -> QuackColor)?,
  val role: IconRole,
  val direction: HorizontalDirection,
  val onClick: ((text: String) -> Unit)?,
) : QuackDataModifierModel

@Stable
private data class TextFieldBorderData(
  val thickness: Dp,
  val color: QuackColor,
  val direction: VerticalDirection,
) : QuackDataModifierModel

@Stable
private data class TextFieldCountableData(
  val enable: Boolean,
  val baseColor: QuackColor,
  val highlightColor: QuackColor,
  val typography: QuackTypography,
  @IntRange(from = 1) val maxCount: Int,
) : QuackDataModifierModel
