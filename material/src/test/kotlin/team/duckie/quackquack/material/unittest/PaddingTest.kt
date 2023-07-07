/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.unittest

import androidx.compose.ui.unit.dp
import io.kotest.matchers.shouldBe
import org.junit.Test
import team.duckie.quackquack.material.QuackPadding

class PaddingTest {
  @Test
  fun `QuackPadding with packedValue`() {
    val (horizontal, vertical) = Int.MAX_VALUE.dp to Int.MIN_VALUE.dp
    val padding = QuackPadding(horizontal, vertical)

    padding.horizontal shouldBe horizontal
    padding.vertical shouldBe vertical
  }
}
