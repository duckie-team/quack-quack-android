/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.utiltest

import io.kotest.matchers.shouldBe
import org.junit.Test
import team.duckie.quackquack.ui.util.buildFloat

class NumberBuilderTest {
  @Test
  fun buildFloat() {
    val actual = buildFloat {
      plus(Float.MAX_VALUE)
      minus(Float.MAX_VALUE)
      plus(1_000f)
      plus(2_000f)
    }
    val expect = 3_000f

    actual shouldBe expect
  }
}
