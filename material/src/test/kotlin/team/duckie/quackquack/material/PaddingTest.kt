/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.ui.unit.dp
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PaddingTest : StringSpec() {
    init {
        "QuackPadding 값이 packedValue로 문제 없이 관리됨" {
            val (horizontal, vertical) = Int.MAX_VALUE.dp to Int.MIN_VALUE.dp
            val padding = QuackPadding(horizontal, vertical)

            padding.horizontal shouldBe horizontal
            padding.vertical shouldBe vertical
        }
    }
}
