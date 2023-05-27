/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import androidx.compose.runtime.collection.MutableVector
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.types.shouldBeSameInstanceAs
import team.duckie.quackquack.util.compose.runtime.test.composed

private val P1 = object : QuackPlugin {}
private val P2 = object : QuackPlugin {}
private val P3 = object : QuackPlugin {}

class QuackPluginsTest : StringSpec() {
  init {
    "rememberQuackPlugins로 플러그인이 잘 저장돼야 함" {
      val plugins = listOf(P1, P2, P3)
      val quackPlugins: QuackPlugins

      composed {
        quackPlugins = rememberQuackPlugins {
          +P1
          +P2
          +P3
        }
      }

      quackPlugins.plugins.asMutableList() shouldContainExactly plugins
    }

    "getByTypeOrNull으로 타입에 맞는 플러그인을 하나 조회할 수 있음" {
      class LocalP1 : QuackPlugin
      class LocalP2 : QuackPlugin
      class LocalP3 : QuackPlugin
      class LocalP4 : QuackPlugin

      val loalP3Instance = LocalP3()

      val plugins = listOf(LocalP1(), LocalP2(), loalP3Instance)
      val quackPlugins: QuackPlugins

      composed {
        quackPlugins = rememberQuackPlugins {
          plugins.forEach { plugin ->
            +plugin
          }
        }
      }

      var pluginOrNull: QuackPlugin? = quackPlugins.getByTypeOrNull<LocalP3>()
      pluginOrNull shouldBeSameInstanceAs loalP3Instance

      pluginOrNull = quackPlugins.getByTypeOrNull<LocalP4>()
      pluginOrNull.shouldBeNull()
    }

    @Suppress("UNCHECKED_CAST")
    "filterByTypeOrNull으로 타입에 맞는 플러그인을 모두 조회할 수 있음" {
      open class LocalP : QuackPlugin
      open class LocalP2 : QuackPlugin
      class LocalPP : LocalP()
      class LocalPP2 : LocalP()

      val localPPInstance = LocalPP()
      val loalPP2Instance = LocalPP2()

      val plugins = listOf(P1, P2, P3, localPPInstance, loalPP2Instance)
      val quackPlugins: QuackPlugins

      composed {
        quackPlugins = rememberQuackPlugins {
          plugins.forEach { plugin ->
            +plugin
          }
        }
      }

      var pluginsOrNull = quackPlugins.filterByTypeOrNull<LocalP>() as MutableVector<QuackPlugin>?
      pluginsOrNull.shouldNotBeNull().asMutableList().shouldContainExactly(localPPInstance, loalPP2Instance)

      pluginsOrNull = quackPlugins.filterByTypeOrNull<LocalP2>() as MutableVector<QuackPlugin>?
      pluginsOrNull.shouldBeNull()
    }

    "EmptyQuackPlugins에서 연산은 허용되지 않음" {
      shouldThrowWithMessage<IllegalStateException>("Unexpected") {
        with(EmptyQuackPlugins) { +P1 }
      }
    }
  }
}

