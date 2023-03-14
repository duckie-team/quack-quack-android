/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import Logger
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer

internal class SugarIrTransformer(
    private val context: IrPluginContext,
    private val logger: Logger,
) : IrElementTransformer<List<SugarIrData>> {
    override fun visitSimpleFunction(
        declaration: IrSimpleFunction,
        data: List<SugarIrData>,
    ): IrStatement {
        TODO()
        return super.visitSimpleFunction(declaration, data)
    }
}
