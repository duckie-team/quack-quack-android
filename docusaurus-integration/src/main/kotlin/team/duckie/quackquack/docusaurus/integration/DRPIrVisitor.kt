/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.docusaurus.integration

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid

internal class DRPIrVisitor(
    @Suppress("unused") private val context: IrPluginContext,
    private val logger: Logger,
) : IrElementVisitorVoid {
    override fun visitModuleFragment(declaration: IrModuleFragment) {
        declaration.files.forEach { file ->
            file.accept(this, null)
        }
    }

    override fun visitFile(declaration: IrFile) {
        declaration.declarations.forEach { item ->
            item.accept(this, null)
        }
    }

    override fun visitSimpleFunction(declaration: IrSimpleFunction) {
        logger(declaration.dumpKotlinLike())
    }

    override fun visitTypeAlias(declaration: IrTypeAlias) {
        logger(declaration.dumpKotlinLike())
    }

    override fun visitClass(declaration: IrClass) {
        logger(declaration.dumpKotlinLike())
    }
}
