/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("ModifierFactoryExtensionFunction", "ModifierFactoryReturnType")

package team.duckie.quackquack.util.modifier

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.fastForEach

@Stable
public fun Modifier.forEach(action: (modifier: Modifier) -> Unit) {
  splitToList().fastForEach(action)
}

@Stable
public operator fun Modifier.iterator(): ModifierIterator =
  object : ModifierIterator {
    override val modifiers = splitToList()
    override var currentIndex = -1
    override val maxIndex = modifiers.lastIndex
  }

@Immutable
public interface ModifierIterator : Iterator<Modifier> {
  public val modifiers: List<Modifier.Element>
  public var currentIndex: Int
  public val maxIndex: Int

  override fun hasNext(): Boolean = currentIndex + 1 <= maxIndex

  override fun next(): Modifier.Element =
    if (hasNext()) modifiers[++currentIndex] else throw NoSuchElementException()
}

@Stable
public fun Modifier.splitToList(): List<Modifier.Element> =
  foldIn(mutableListOf()) { acc, element ->
    acc.apply { add(element) }
  }

@Stable
public fun Modifier.first(): Modifier.Element = splitToList().first()

@Stable
public fun Modifier.last(): Modifier.Element = splitToList().last()

@Stable
public inline fun <reified T : Modifier.Element> Modifier.getElementByTypeOrNull(): T? =
  splitToList().fastFirstOrNull { it is T } as? T

@Stable
public inline fun <reified T : Modifier.Element> Modifier.filterElementByTypeOrNull(): List<T>? {
  val modifiers = splitToList()
  val target = ArrayList<T>(modifiers.size)
  modifiers.fastForEach { modifier ->
    if (modifier is T) target += modifier
  }
  return target.takeIf { it.isNotEmpty() }
}
