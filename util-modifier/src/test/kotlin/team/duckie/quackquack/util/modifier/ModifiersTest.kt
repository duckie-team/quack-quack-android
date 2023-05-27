/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.modifier

import androidx.compose.ui.Modifier
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private object Mod : Modifier.Element
private object Mod2 : Modifier.Element

class ModifiersTest : StringSpec() {
  init {
    "splitToList" {
      val elements = listOf(Mod, Mod2)
      var modifier: Modifier = Modifier

      elements.forEach { element ->
        modifier = modifier.then(element)
      }

      modifier.splitToList() shouldContainExactly elements
    }

    "ModifierIterator" {
      val modifiers = Modifier.then(Mod).then(Mod2)
      val elements = mutableListOf<Modifier.Element>()

      for (element in modifiers) {
        elements += element
      }

      elements shouldContainExactly modifiers.splitToList()
    }

    "first" {
      val modifiers = Modifier.then(Mod).then(Mod2)
      val mod = modifiers.first()

      mod shouldBe Mod
    }

    "last" {
      val modifiers = Modifier.then(Mod).then(Mod2)
      val mod = modifiers.last()

      mod shouldBe Mod2
    }

    "getElementByTypeOrNull" {
      val modifiers = Modifier.then(Mod).then(Mod2)
      val mod = modifiers.getElementByTypeOrNull<Mod2>()

      mod shouldBe Mod2

      class Mod3 : Modifier.Element

      val nullMod = modifiers.getElementByTypeOrNull<Mod3>()

      nullMod.shouldBeNull()
    }

    "filterElementByTypeOrNull" {
      val modifiers = Modifier.then(Mod).then(Mod2).then(Mod2).then(Mod)
      val mods = modifiers.filterElementByTypeOrNull<Mod2>()

      mods.shouldContainExactly(Mod2, Mod2)

      class Mod3 : Modifier.Element

      val nullMod = modifiers.filterElementByTypeOrNull<Mod3>()

      nullMod.shouldBeNull()
    }
  }
}
