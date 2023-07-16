/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.hosted

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import team.duckie.quackquack.sugar.hosted.codegen.generateSugarComponentFiles
import team.duckie.quackquack.sugar.hosted.node.SugarComponentNode
import team.duckie.quackquack.sugar.hosted.visitor.SugarNodeVisitor
import team.duckie.quackquack.util.backend.kotlinc.Logger

internal class SugarHostedExtension(
  private val logger: Logger,
  private val sugarPath: String,
) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    val nodes = mutableListOf<SugarComponentNode>()
    val visitor = SugarNodeVisitor(
      context = pluginContext,
      logger = logger,
      addSugarComponentNode = nodes::add,
    )

    moduleFragment.accept(visitor, null)
    generateSugarComponentFiles(
      sugarComponentNodes = nodes,
      sugarPath = sugarPath,
    )
  }
}
