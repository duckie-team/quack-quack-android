/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("unused")

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocationWithRange
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET

internal const val LogPrefix = "[SUGAR (IR)]"

internal interface Logger {
    fun warn(value: Any?, location: CompilerMessageSourceLocation? = null)
    fun error(value: Any?, location: CompilerMessageSourceLocation? = null)

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
        }

        override fun error(value: Any?, location: CompilerMessageSourceLocation?) {
            messageCollector.report(CompilerMessageSeverity.ERROR, value.toString(), location)
        }
    }
}

internal fun Any?.prependLogPrefix(withNewline: Boolean = false) =
    "$LogPrefix ${if (withNewline) "\n$this" else " $this"}"

/**
 * Finds the line and column of [irElement] within this file.
 */
internal fun IrFile.locationOf(irElement: IrElement?): CompilerMessageSourceLocation {
    val sourceRangeInfo = fileEntry.getSourceRangeInfo(
        beginOffset = irElement?.startOffset ?: SYNTHETIC_OFFSET,
        endOffset = irElement?.endOffset ?: SYNTHETIC_OFFSET,
    )
    return CompilerMessageLocationWithRange.create(
        path = sourceRangeInfo.filePath,
        lineStart = sourceRangeInfo.startLineNumber + 1,
        columnStart = sourceRangeInfo.startColumnNumber + 1,
        lineEnd = sourceRangeInfo.endLineNumber + 1,
        columnEnd = sourceRangeInfo.endColumnNumber + 1,
        lineContent = null,
    )!!
}
