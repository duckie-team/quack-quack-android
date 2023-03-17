/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import Logger
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment

internal class SugarIrExtension(
    private val logger: Logger,
    private val sugarPath: String,
    private val poet: Boolean,
) : IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext,
    ) {
        val sugarIrDatas = mutableListOf<SugarIrData>()
        val visitor = SugarIrVisitor(
            context = pluginContext,
            logger = logger,
            addSugarIrData = sugarIrDatas::add,
        )
        val transformer = SugarIrTransformer(
            context = pluginContext,
            logger = logger,
        )
        moduleFragment.accept(visitor, null)
        // generateSugarComponents(sugarIrDatas)
        // moduleFragment.transform(transformer, sugarIrDatas)
    }
}
