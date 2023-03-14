/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import Logger
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid

internal class SugarIrVisitor(
    private val logger: Logger,
    private val addSugarIrData: (data: SugarIrData) -> Unit,
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
        // TODO()
    }
}
