/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)
@file:Suppress("unused")

package team.duckie.quackquack.sugar.core

import com.google.auto.service.AutoService
import org.jetbrains.annotations.VisibleForTesting
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@VisibleForTesting
const val PluginId = "team.duckie.quackquack.sugar.core"

internal val KEY_SUGAR_PATH =
  CompilerConfigurationKey<String>("Where the sugar components will be created - required")

@VisibleForTesting
val OPTION_SUGAR_PATH =
  CliOption(
    optionName = "sugarPath",
    valueDescription = "String",
    description = KEY_SUGAR_PATH.toString(),
    required = true,
    allowMultipleOccurrences = false,
  )

@AutoService(CommandLineProcessor::class)
class SugarCoreCommandLineProcessor : CommandLineProcessor {
  override val pluginId = PluginId

  override val pluginOptions = listOf(OPTION_SUGAR_PATH)

  override fun processOption(
    option: AbstractCliOption,
    value: String,
    configuration: CompilerConfiguration,
  ) {
    when (val optionName = option.optionName) {
      OPTION_SUGAR_PATH.optionName -> configuration.put(KEY_SUGAR_PATH, value)
      else -> error("Unknown plugin option: $optionName")
    }
  }
}
