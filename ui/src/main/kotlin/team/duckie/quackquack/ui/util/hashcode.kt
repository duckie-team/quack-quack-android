/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

internal val reflectivelyFillMaxSizeOperationHashCode = run {
  val sizeKt = Class.forName("androidx.compose.foundation.layout.SizeKt")
  val fillWholeMaxWidthField = sizeKt.getDeclaredField("FillWholeMaxWidth")
    .apply { isAccessible = true }
  val fillWholeMaxWidth = fillWholeMaxWidthField.get(sizeKt)!!
  fillWholeMaxWidth.hashCode()
}
