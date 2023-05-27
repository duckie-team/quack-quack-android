/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.compose.runtime.test

import androidx.compose.runtime.currentCompositeKeyHash
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ComposeRuntimeMockTest : StringSpec() {
  init {
    coroutineTestScope = true

    // TODO: 테스트 개선 (리컴포지션 방식)
    "composed (withRunningRecomposer)".config(enabled = false) {
      var compositeKeyHash = 0

      composed { compositeKeyHash = currentCompositeKeyHash }

      compositeKeyHash shouldBe -1944583614
    }
  }
}
