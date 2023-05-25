/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import team.duckie.quackquack.material.QuackPadding

/** 검증 실패를 나태낼 문자를 정의합니다. */
@VisibleForTesting
internal object AssertionFail {
  const val NotAllowZeroOrNegativePadding = "Negative numbers or zeros are not allowed as padding values."
}

/** [QuackPadding]으로 주어진 값이 모두 0보다 큰 양수임을 검증합니다. */
@Stable
public fun QuackPadding.assertOnlyPositiveNotZero() {
  require(
    horizontal > Dp.Hairline && vertical > Dp.Hairline,
    lazyMessage = AssertionFail::NotAllowZeroOrNegativePadding,
  )
}

/** [PaddingValues]로 주어진 값이 모두 0보다 큰 양수임을 검증합니다. */
@Stable
public fun PaddingValues.assertOnlyPositiveNotZero() {
  require(
    calculateTopPadding() > Dp.Hairline &&
      calculateBottomPadding() > Dp.Hairline &&
      calculateLeftPadding(LayoutDirection.Ltr) > Dp.Hairline &&
      calculateRightPadding(LayoutDirection.Ltr) > Dp.Hairline,
    lazyMessage = AssertionFail::NotAllowZeroOrNegativePadding,
  )
}
