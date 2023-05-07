/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.docusaurus.integration

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration

internal const val LogPrefix = "docusaurus-integration"

internal interface Logger {
    fun warn(value: Any?, location: CompilerMessageSourceLocation? = null)
    fun error(value: Any?, location: CompilerMessageSourceLocation? = null)
    fun throwError(value: Any?, location: CompilerMessageSourceLocation? = null): Nothing

    operator fun invoke(value: Any?, location: CompilerMessageSourceLocation? = null) {
        warn(value = value, location = location)
    }
}

internal fun CompilerConfiguration.getLogger(): Logger {
    val messageCollector = get(
        CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
        MessageCollector.NONE,
    )

    return object : Logger {
        override fun warn(value: Any?, location: CompilerMessageSourceLocation?) {
            messageCollector.report(CompilerMessageSeverity.WARNING, value.toString(), location)
            println(value) // for test logging
        }

        override fun error(value: Any?, location: CompilerMessageSourceLocation?) {
            messageCollector.report(CompilerMessageSeverity.ERROR, value.toString(), location)
            println(value) // for test logging
        }

        override fun throwError(value: Any?, location: CompilerMessageSourceLocation?): Nothing {
            error(value, location)
            kotlin.error(value.toString())
        }
    }
}

internal fun Any?.prependLogPrefix(withNewline: Boolean = false) =
    "[$LogPrefix] ${if (withNewline) "\n$this" else " $this"}"
