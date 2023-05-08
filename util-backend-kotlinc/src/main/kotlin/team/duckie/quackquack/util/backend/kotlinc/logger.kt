/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.kotlinc

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration

public interface Logger {
  public val tag: String

  public fun warn(message: Any?, location: CompilerMessageSourceLocation? = null)
  public fun error(message: Any?, location: CompilerMessageSourceLocation? = null)
  public fun throwError(message: Any?, location: CompilerMessageSourceLocation? = null): Nothing

  public operator fun invoke(value: Any?, location: CompilerMessageSourceLocation? = null) {
    warn(message = value, location = location)
  }

  public fun Any?.prependLogPrefix(withNewline: Boolean = false): String =
    "[$tag] ${if (withNewline) "\n$this" else " $this"}"
}

public fun CompilerConfiguration.getLogger(tag: String): Logger {
  val messageCollector = get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

  return object : Logger {
    override val tag = tag

    override fun warn(message: Any?, location: CompilerMessageSourceLocation?) {
      messageCollector.report(CompilerMessageSeverity.WARNING, message.toString(), location)
    }

    override fun error(message: Any?, location: CompilerMessageSourceLocation?) {
      messageCollector.report(CompilerMessageSeverity.ERROR, message.toString(), location)
    }

    override fun throwError(message: Any?, location: CompilerMessageSourceLocation?): Nothing {
      error(message, location)
      kotlin.error(message.toString())
    }
  }
}

