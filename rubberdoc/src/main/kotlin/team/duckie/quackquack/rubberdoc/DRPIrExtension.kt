/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.rubberdoc

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import team.duckie.quackquack.util.backend.kotlinc.Logger

internal class DRPIrExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(
    moduleFragment: IrModuleFragment,
    pluginContext: IrPluginContext,
  ) {
    val visitor = DRPIrVisitor(context = pluginContext, logger = logger)
    moduleFragment.accept(visitor, null)
  }
}
