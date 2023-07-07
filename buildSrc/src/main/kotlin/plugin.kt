/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

inline fun PluginDependenciesSpec.quackquack(pluginName: String): PluginDependencySpec =
  id("quackquack.plugin.$pluginName")

inline fun PluginDependenciesSpec.android(pluginId: String): PluginDependencySpec =
  id("com.android.$pluginId")
