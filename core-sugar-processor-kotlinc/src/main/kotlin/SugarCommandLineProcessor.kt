/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal val KEY_SUGAR_PATH = CompilerConfigurationKey<String>("Where the sugar components will be created")
internal val OPTION_SUGAR_PATH = CliOption(
    optionName = "sugarPath",
    valueDescription = "String",
    description = KEY_SUGAR_PATH.toString(),
    required = /*true*/ false,
    allowMultipleOccurrences = false,
)

@AutoService(CommandLineProcessor::class)
class SugarCommandLineProcessor : CommandLineProcessor {
    override val pluginId = PluginId

    override val pluginOptions = listOf(OPTION_SUGAR_PATH)

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration,
    ) {
        if (option.optionName == OPTION_SUGAR_PATH.optionName) {
            configuration.put(KEY_SUGAR_PATH, value)
        } else {
            error("Unknown plugin option: ${option.optionName}")
        }
    }
}
