/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)

package team.duckie.quackquack.sugar.processor

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal const val PluginId = "team.duckie.quackquack.sugar.processor"

internal val KEY_SUGAR_PATH = CompilerConfigurationKey<String>(
  "Where the sugar components will be created - required",
)
internal val OPTION_SUGAR_PATH = CliOption(
  optionName = "sugarPath",
  valueDescription = "String",
  description = KEY_SUGAR_PATH.toString(),
  required = true,
  allowMultipleOccurrences = false,
)

internal val KEY_POET = CompilerConfigurationKey<String>(
  "Whether to enable sugar components generation - default is true",
)
internal val OPTION_POET = CliOption(
  optionName = "poet",
  valueDescription = "<true | false>",
  description = KEY_POET.toString(),
  required = false,
  allowMultipleOccurrences = false,
)

@AutoService(CommandLineProcessor::class)
class SugarCommandLineProcessor : CommandLineProcessor {
  override val pluginId = PluginId

  override val pluginOptions = listOf(OPTION_SUGAR_PATH, OPTION_POET)

  override fun processOption(
    option: AbstractCliOption,
    value: String,
    configuration: CompilerConfiguration,
  ) {
    when (val optionName = option.optionName) {
      OPTION_SUGAR_PATH.optionName -> configuration.put(KEY_SUGAR_PATH, value)
      OPTION_POET.optionName -> configuration.put(KEY_POET, value)
      else -> error("Unknown plugin option: $optionName")
    }
  }
}
