/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment

internal class SugarIrGenerationExtension(
    private val messageCollector: MessageCollector,
    private val sugarPath: String,
) : IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext,
    ) {
        val irVisitor = SugarIrVisitor(
            context = pluginContext,
            messageCollector = messageCollector,
            sugarPath = sugarPath,
        )
        moduleFragment.files.forEach { file ->
            file.acceptChildren(irVisitor, null)
        }
    }
}
