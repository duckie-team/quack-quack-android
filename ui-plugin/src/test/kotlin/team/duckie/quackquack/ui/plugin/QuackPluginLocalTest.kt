/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import androidx.compose.ui.Modifier
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeSameInstanceAs
import team.duckie.quackquack.util.modifier.getElementByTypeOrNull

private object V

class QuackPluginLocalTest : StringSpec() {
  init {
    "Modifier.quackPluginLocal로 QuackPluginLocal이 저장됨" {
      val mod = Modifier.quackPluginLocal(V)

      mod
        .getElementByTypeOrNull<QuackPluginLocal>()
        .shouldNotBeNull()
        .value
        .shouldBeSameInstanceAs(V)
    }
  }
}
