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

@Immutable
public interface QuackPlugin

@Immutable
public interface QuackPlugins {
  public val plugins: MutableVector<QuackPlugin>
  public operator fun QuackPlugin.unaryPlus()
}

@Immutable
public object EmptyQuackPlugins : QuackPlugins {
  override val plugins: MutableVector<QuackPlugin> = MutableVector(0)
  override fun QuackPlugin.unaryPlus() {
    error("Unexpected")
  }
}

private class QuackPluginsScope : QuackPlugins {
  override val plugins = mutableVectorOf<QuackPlugin>()
  override fun QuackPlugin.unaryPlus() {
    plugins += this
  }
}

@Stable
public inline fun <reified T : QuackPlugin> QuackPlugins.getByTypeOrNull(): T? =
  plugins.firstOrNull { it is T } as? T

@Stable
public inline fun <reified T : QuackPlugin> QuackPlugins.filterByTypeOrNull(): MutableVector<T>? =
  plugins.mapNotNull { it as? T }.takeIf { it.isNotEmpty() }

@Composable
public fun rememberQuackPlugins(builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember { QuackPluginsScope().also(builder) }

@Composable
public fun rememberQuackPlugins(key1: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(key1) { QuackPluginsScope().also(builder) }

@Composable
public fun rememberQuackPlugins(key1: Any?, key2: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(key1, key2) { QuackPluginsScope().also(builder) }

@Composable
public fun rememberQuackPlugins(key1: Any?, key2: Any?, key3: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(key1, key2, key3) { QuackPluginsScope().also(builder) }

@Composable
public fun rememberQuackPlugins(vararg keys: Any?, builder: QuackPlugins.() -> Unit): QuackPlugins =
  remember(*keys) { QuackPluginsScope().also(builder) }
