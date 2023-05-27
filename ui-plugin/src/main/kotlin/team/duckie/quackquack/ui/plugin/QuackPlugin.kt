/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.plugin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collection.MutableVector
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.runtime.remember

/** 꽥꽥 플러그인을 나타냅니다. */
@Immutable
public interface QuackPlugin

/**
 * 꽥꽥 플러그인이 저장되는 인터페이스.
 * 인스턴스를 얻으려면 [LocalQuackPlugins]를 참조하세요.
 */
@Immutable
public interface QuackPlugins {
  /** 등록된 전체 플러그인 */
  public val plugins: MutableVector<QuackPlugin>

  /**
   * 새로운 플러그인을 등록합니다. 이 작업은 중복을 허용합니다.
   *
   * ```
   * with(QuackPluginsScope) {
   *   +QuackSamplePlugin
   * }
   * ```
   */
  public operator fun QuackPlugin.unaryPlus()
}

/**
 * 등록된 플러그인이 없고 추후 등록될 일도 없음을 나타냅니다.
 *
 * [플러그인 저장소][LocalQuackPlugins]로 [EmptyQuackPlugins]이 제공된 상태에서
 * 플러그인 등록이 요청됐다면 [IllegalStateException]이 발생합니다.
 */
@Immutable
public object EmptyQuackPlugins : QuackPlugins {
  override val plugins: MutableVector<QuackPlugin> = MutableVector(0)
  override fun QuackPlugin.unaryPlus() {
    error("Unexpected")
  }
}

/** capacity가 16인 기본 플러그인 저장소 인스턴스를 제공합니다. */
private class QuackPluginsScope : QuackPlugins {
  override val plugins = mutableVectorOf<QuackPlugin>()
  override fun QuackPlugin.unaryPlus() {
    plugins += this
  }
}

/**
 * 주어진 플러그인 저장소에서 특정 타입에 맞는 플러그인을 가져옵니다.
 * 조건에 맞는 플러그인이 여러 개라면 가장 마지막으로 등록된 플러그인을 반환합니다.
 *
 * @param T 원하는 플러그인의 타입
 */
@Stable
public inline fun <reified T : QuackPlugin> QuackPlugins.lastByTypeOrNull(): T? =
  plugins.lastOrNull { it is T } as? T

/**
 * 주어진 플러그인 저장소에서 특정 타입에 맞는 플러그인을 가져옵니다.
 * 조건에 맞는 플러그인이 여러 개라면 해당 플러그인을 모두 반환합니다.
 *
 * @param T 원하는 플러그인의 타입
 */
@Stable
public inline fun <reified T : QuackPlugin> QuackPlugins.filterByTypeOrNull(): MutableVector<T>? =
  plugins.mapNotNull { it as? T }.takeIf { it.isNotEmpty() }

/** [QuackPlugins] 인스턴스를 생성하고 이후 재사용합니다. */
@Composable
public fun rememberQuackPlugins(builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember { QuackPluginsScope().also(builder) }

/**
 * [QuackPlugins] 인스턴스를 생성하고 이후 재사용합니다.
 * 단, 주어진 [key1]가 변경되었다면 인스턴스를 다시 생성합니다.
 */
@Composable
public fun rememberQuackPlugins(key1: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(key1) { QuackPluginsScope().also(builder) }

/**
 * [QuackPlugins] 인스턴스를 생성하고 이후 재사용합니다.
 * 단, 주어진 [key1], [key2] 중 하나라도 변경되었다면 인스턴스를 다시 생성합니다.
 */
@Composable
public fun rememberQuackPlugins(key1: Any?, key2: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(key1, key2) { QuackPluginsScope().also(builder) }

/**
 * [QuackPlugins] 인스턴스를 생성하고 이후 재사용합니다.
 * 단, 주어진 [key1], [key2], [key3] 중 하나라도 변경되었다면 인스턴스를 다시 생성합니다.
 */
@Composable
public fun rememberQuackPlugins(key1: Any?, key2: Any?, key3: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(key1, key2, key3) { QuackPluginsScope().also(builder) }

/**
 * [QuackPlugins] 인스턴스를 생성하고 이후 재사용합니다.
 * 단, 주어진 [keys] 중 하나라도 변경되었다면 인스턴스를 다시 생성합니다.
 */
@Composable
public fun rememberQuackPlugins(vararg keys: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(*keys) { QuackPluginsScope().also(builder) }
