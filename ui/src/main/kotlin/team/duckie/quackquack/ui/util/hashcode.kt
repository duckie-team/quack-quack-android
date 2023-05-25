/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier

/**
 * [Modifier.fillMaxWidth]를 기본 인자로 사용했을 때 위임되는 top-level 변수인
 * `FillWholeMaxWidth`의 hashcode를 리플랙션을 이용하여 조회합니다.
 */
internal val reflectivelyFillMaxSizeOperationHashCode = run {
  val sizeKt = Class.forName("androidx.compose.foundation.layout.SizeKt")
  val fillWholeMaxWidthField = sizeKt.getDeclaredField("FillWholeMaxWidth")
    .apply { isAccessible = true }
  val fillWholeMaxWidth = fillWholeMaxWidthField.get(sizeKt)!!
  fillWholeMaxWidth.hashCode()
}
