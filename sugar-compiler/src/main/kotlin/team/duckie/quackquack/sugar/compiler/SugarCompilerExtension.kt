/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.compiler

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import team.duckie.quackquack.sugar.hosted.node.SugarComponentNode
import team.duckie.quackquack.sugar.hosted.transformer.SugarIrTransformer
import team.duckie.quackquack.sugar.hosted.visitor.SugarCoreVisitor
import team.duckie.quackquack.util.backend.kotlinc.Logger

internal class SugarCompilerExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(
    moduleFragment: IrModuleFragment,
    pluginContext: IrPluginContext,
  ) {
    val nodes = mutableListOf<SugarComponentNode>()
    val visitor = SugarCoreVisitor(
      context = pluginContext,
      logger = logger,
      addSugarComponentNode = nodes::add,
    )
    val transformer = SugarIrTransformer(
      context = pluginContext,
      logger = logger,
    )

    moduleFragment.accept(visitor, null)
    moduleFragment.transform(transformer, nodes.asMap())
  }
}

private fun List<SugarComponentNode>.asMap() =
  buildMap(capacity = size) {
    this@asMap.forEach { SugarComponentNode ->
      set(SugarComponentNode.referFqn.asString(), SugarComponentNode)
    }
  }
