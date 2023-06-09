/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

internal open class QuackComposedModifier(val factory: @Composable Modifier.() -> Modifier) : Modifier.Element

@Stable
private class KeyedQuackComposedModifier1(
  val fqName: String,
  val key1: Any?,
  factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(factory) {
  override fun equals(other: Any?): Boolean {
    return other is KeyedQuackComposedModifier1 &&
      fqName == other.fqName &&
      key1 == other.key1
  }

  override fun hashCode(): Int {
    return 31 * fqName.hashCode() + key1.hashCode()
  }
}

@Stable
private class KeyedQuackComposedModifier2(
  val fqName: String,
  val key1: Any?,
  val key2: Any?,
  factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(factory) {
  override fun equals(other: Any?): Boolean {
    return other is KeyedQuackComposedModifier2 &&
      fqName == other.fqName &&
      key1 == other.key1 &&
      key2 == other.key2
  }

  override fun hashCode(): Int {
    var result = fqName.hashCode()
    result = 31 * result + key1.hashCode()
    result = 31 * result + key2.hashCode()
    return result
  }
}

@Stable
private class KeyedQuackComposedModifier3(
  val fqName: String,
  val key1: Any?,
  val key2: Any?,
  val key3: Any?,
  factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(factory) {
  override fun equals(other: Any?): Boolean {
    return other is KeyedQuackComposedModifier3 &&
      fqName == other.fqName &&
      key1 == other.key1 &&
      key2 == other.key2 &&
      key3 == other.key3
  }

  override fun hashCode(): Int {
    var result = fqName.hashCode()
    result = 31 * result + key1.hashCode()
    result = 31 * result + key2.hashCode()
    result = 31 * result + key3.hashCode()
    return result
  }
}

@Stable
private class KeyedQuackComposedModifierN(
  val fqName: String,
  val keys: Array<out Any?>,
  factory: @Composable Modifier.() -> Modifier,
) : QuackComposedModifier(factory) {
  override fun equals(other: Any?): Boolean {
    return other is KeyedQuackComposedModifierN &&
      fqName == other.fqName &&
      keys.contentEquals(other.keys)
  }

  override fun hashCode(): Int {
    return 31 * fqName.hashCode() + keys.contentHashCode()
  }
}

/**
 * [Modifier.composed]의 꽥꽥 버전을 구현합니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 *
 * `quackComposed`는 무조건 [QuackDataModifierModel]를 반환해야 합니다. 일반
 * [Modifier.Element]를 반환하는 경우에는 표준 [composed]를 사용하세요.
 *
 * @see Composer.quackMaterializeOf
 */
@Stable
public fun Modifier.quackComposed(factory: @Composable Modifier.() -> Modifier): Modifier {
  return then(QuackComposedModifier(factory))
}

/**
 * [Modifier.composed]의 꽥꽥 버전을 구현합니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 *
 * `quackComposed`는 무조건 [QuackDataModifierModel]를 반환해야 합니다. 일반
 * [Modifier.Element]를 반환하는 경우에는 표준 [composed]를 사용하세요.
 *
 * `fullyQualifiedName`과 `key`를 통한 안정성 정보가 아직 검증되지 않았습니다. 이는
 * [Modifier.composed]의 aosp-test 로컬 테스트 결과도 동일합니다. 따라서 인자로 제공되는
 * 안정성에 의존한 로직을 구현하면 안 됩니다.
 *
 * 자세한 정보는 `quack-KeyedComposedModifier는 안정성을 준수하며 re-composition에 한 번만
 * 재실행돼야 함` 테스트를 참고하세요.
 *
 * @see Composer.quackMaterializeOf
 */
@Stable
public fun Modifier.quackComposed(
  fullyQualifiedName: String,
  key1: Any?,
  factory: @Composable Modifier.() -> Modifier,
): Modifier {
  return then(
    KeyedQuackComposedModifier1(
      fqName = fullyQualifiedName,
      key1 = key1,
      factory = factory,
    ),
  )
}

/**
 * [Modifier.composed]의 꽥꽥 버전을 구현합니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 *
 * `quackComposed`는 무조건 [QuackDataModifierModel]를 반환해야 합니다. 일반
 * [Modifier.Element]를 반환하는 경우에는 표준 [composed]를 사용하세요.
 *
 * `fullyQualifiedName`과 `key`를 통한 안정성 정보가 아직 검증되지 않았습니다. 이는
 * [Modifier.composed]의 aosp-test 로컬 테스트 결과도 동일합니다. 따라서 인자로 제공되는
 * 안정성에 의존한 로직을 구현하면 안 됩니다.
 *
 * 자세한 정보는 `quack-KeyedComposedModifier는 안정성을 준수하며 re-composition에 한 번만
 * 재실행돼야 함` 테스트를 참고하세요.
 *
 * @see Composer.quackMaterializeOf
 */
@Stable
public fun Modifier.quackComposed(
  fullyQualifiedName: String,
  key1: Any?,
  key2: Any?,
  factory: @Composable Modifier.() -> Modifier,
): Modifier {
  return then(
    KeyedQuackComposedModifier2(
      fqName = fullyQualifiedName,
      key1 = key1,
      key2 = key2,
      factory = factory,
    ),
  )
}

/**
 * [Modifier.composed]의 꽥꽥 버전을 구현합니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 *
 * `quackComposed`는 무조건 [QuackDataModifierModel]를 반환해야 합니다. 일반
 * [Modifier.Element]를 반환하는 경우에는 표준 [composed]를 사용하세요.
 *
 * `fullyQualifiedName`과 `key`를 통한 안정성 정보가 아직 검증되지 않았습니다. 이는
 * [Modifier.composed]의 aosp-test 로컬 테스트 결과도 동일합니다. 따라서 인자로 제공되는
 * 안정성에 의존한 로직을 구현하면 안 됩니다.
 *
 * 자세한 정보는 `quack-KeyedComposedModifier는 안정성을 준수하며 re-composition에 한 번만
 * 재실행돼야 함` 테스트를 참고하세요.
 *
 * @see Composer.quackMaterializeOf
 */
@Stable
public fun Modifier.quackComposed(
  fullyQualifiedName: String,
  key1: Any?,
  key2: Any?,
  key3: Any?,
  factory: @Composable Modifier.() -> Modifier,
): Modifier {
  return then(
    KeyedQuackComposedModifier3(
      fqName = fullyQualifiedName,
      key1 = key1,
      key2 = key2,
      key3 = key3,
      factory = factory,
    ),
  )
}

/**
 * [Modifier.composed]의 꽥꽥 버전을 구현합니다.
 *
 * `composed`의 결과가 [QuackDataModifierModel]일 경우 `acc`를 유지하며 folding하기 위해
 * `ComposedModifier`의 별도 대응이 필요합니다.
 *
 * `quackComposed`는 무조건 [QuackDataModifierModel]를 반환해야 합니다. 일반
 * [Modifier.Element]를 반환하는 경우에는 표준 [composed]를 사용하세요.
 *
 * `fullyQualifiedName`과 `key`를 통한 안정성 정보가 아직 검증되지 않았습니다. 이는
 * [Modifier.composed]의 aosp-test 로컬 테스트 결과도 동일합니다. 따라서 인자로 제공되는
 * 안정성에 의존한 로직을 구현하면 안 됩니다.
 *
 * 자세한 정보는 `quack-KeyedComposedModifier는 안정성을 준수하며 re-composition에 한 번만
 * 재실행돼야 함` 테스트를 참고하세요.
 *
 * @see Composer.quackMaterializeOf
 */
@Stable
public fun Modifier.quackComposed(
  fullyQualifiedName: String,
  vararg keys: Any?,
  factory: @Composable Modifier.() -> Modifier,
): Modifier {
  return then(
    KeyedQuackComposedModifierN(
      fqName = fullyQualifiedName,
      keys = keys,
      factory = factory,
    ),
  )
}
