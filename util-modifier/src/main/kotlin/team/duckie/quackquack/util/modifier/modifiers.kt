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

/** 주어진 [Modifier]의 [요소][Modifier.Element]를 순회하며 [action] 작업을 수행합니다. */
@Stable
public fun Modifier.forEach(action: (modifier: Modifier) -> Unit) {
  splitToList().fastForEach(action)
}

/** 주어진 [Modifier]의 [요소][Modifier.Element]를 순회하는 [Iterator]를 반환합니다. */
@Stable
public operator fun Modifier.iterator(): ModifierIterator =
  object : ModifierIterator {
    override val modifiers = splitToList()
    override var currentIndex = -1
    override val maxIndex = modifiers.lastIndex
  }

/** [Modifier.Element]를 순회하는 [Iterator] */
@Immutable
public interface ModifierIterator : Iterator<Modifier> {
  public val modifiers: List<Modifier.Element>
  public var currentIndex: Int
  public val maxIndex: Int

  override fun hasNext(): Boolean = currentIndex + 1 <= maxIndex

  override fun next(): Modifier.Element =
    if (hasNext()) modifiers[++currentIndex] else throw NoSuchElementException()
}

/** 주어진 [Modifier]를 각각 [요소][Modifier.Element]로 분리하여 반환합니다. */
@Stable
public fun Modifier.splitToList(): List<Modifier.Element> =
  foldIn(mutableListOf()) { acc, element ->
    acc.apply { add(element) }
  }

/** 주어진 [Modifier]의 첫 번째 [요소][Modifier.Element]를 반환합니다. */
@Stable
public fun Modifier.first(): Modifier.Element = splitToList().first()

/** 주어진 [Modifier]의 마지막 [요소][Modifier.Element]를 반환합니다. */
@Stable
public fun Modifier.last(): Modifier.Element = splitToList().last()

/**
 * 주어진 [Modifier]에서 특정 타입에 맞는 첫 번째 [요소][Modifier.Element]를 반환합니다.
 * 만약 찾고자 하는 [요소][Modifier.Element]가 없다면 null을 반환합니다.
 *
 * @param T 찾고자 하는 [요소][Modifier.Element]의 타입
 */
@Stable
public inline fun <reified T : Modifier.Element> Modifier.getElementByTypeOrNull(): T? =
  splitToList().fastFirstOrNull { it is T } as? T

/**
 * 주어진 [Modifier]에서 특정 타입에 맞는 [요소][Modifier.Element]를 모두 반환합니다.
 * 만약 찾고자 하는 [요소][Modifier.Element]가 없다면 null을 반환합니다.
 *
 * @param T 찾고자 하는 [요소][Modifier.Element]의 타입
 */
@Stable
public inline fun <reified T : Modifier.Element> Modifier.filterElementByTypeOrNull(): List<T>? {
  val modifiers = splitToList()
  val target = ArrayList<T>(modifiers.size)
  modifiers.fastForEach { modifier ->
    if (modifier is T) target += modifier
  }
  return target.takeIf { it.isNotEmpty() }
}
