/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import team.duckie.quackquack.util.compose.runtime.test.composed

class LocalQuackPluginsTest : StringSpec() {
  init {
    "LocalQuackPlugins로 값을 제공하지 않았을 때는 EmptyQuackPlugins이 제공됨" {
      val localQuackPlugins: QuackPlugins

      composed { localQuackPlugins = LocalQuackPlugins.current }

      localQuackPlugins shouldBeSameInstanceAs EmptyQuackPlugins
    }
  }
}
