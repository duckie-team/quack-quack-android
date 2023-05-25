/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.utiltest

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Test
import team.duckie.quackquack.ui.util.reflectivelyFillMaxSizeOperationHashCode

class HashCodeTest {
  @Test
  fun notContainsFillMaxWidthModifier() {
    val fillMaxWidthHashCode = reflectivelyFillMaxSizeOperationHashCode
    val modifiers = Modifier
      .fillMaxSize()
      .fillMaxHeight()
    var containsFillMaxWidthModifier = false

    for (modifier in modifiers) {
      if (modifier.hashCode() == fillMaxWidthHashCode) {
        containsFillMaxWidthModifier = true
      }
    }

    containsFillMaxWidthModifier.shouldBeFalse()
  }

  @Test
  fun containsFillMaxWidthModifier() {
    val fillMaxWidthHashCode = reflectivelyFillMaxSizeOperationHashCode
    val modifiers = Modifier
      .fillMaxSize()
      .fillMaxWidth()
      .fillMaxHeight()
    var containsFillMaxWidthModifier = false

    for (modifier in modifiers) {
      if (modifier.hashCode() == fillMaxWidthHashCode) {
        containsFillMaxWidthModifier = true
      }
    }

    containsFillMaxWidthModifier.shouldBeTrue()
  }
}

private operator fun Modifier.iterator() =
  object : ModifierIterator {
    override val modifiers = splitToList()
    override var currentIndex = 0
    override val maxIndex = modifiers.lastIndex
  }

private interface ModifierIterator : Iterator<Modifier> {
  val modifiers: List<Modifier>
  var currentIndex: Int
  val maxIndex: Int

  override fun hasNext() = currentIndex + 1 <= maxIndex

  @Suppress("ModifierFactoryExtensionFunction")
  override fun next() =
    if (hasNext()) modifiers[currentIndex++] else throw NoSuchElementException()
}

private fun Modifier.splitToList(): List<Modifier> =
  foldIn(mutableListOf()) { acc, element ->
    acc.apply { add(element) }
  }
