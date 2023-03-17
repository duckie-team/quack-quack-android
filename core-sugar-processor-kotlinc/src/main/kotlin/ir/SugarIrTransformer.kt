/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import GeneratedFileFqn
import Logger
import NoSugarFqn
import SugarReferFqn
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.getAnnotation
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer
import org.jetbrains.kotlin.utils.addToStdlib.cast

internal class SugarIrTransformer(
    @Suppress("unused") private val context: IrPluginContext,
    private val logger: Logger,
) : IrElementTransformer<Map<String, SugarIrData>> {
    override fun visitModuleFragment(
        declaration: IrModuleFragment,
        data: Map<String, SugarIrData>,
    ): IrModuleFragment {
        declaration.files.forEach { file ->
            file.accept(this, data)
        }
        return declaration
    }

    override fun visitFile(
        declaration: IrFile,
        data: Map<String, SugarIrData>,
    ): IrFile {
        if (declaration.hasAnnotation(GeneratedFileFqn)) {
            declaration.declarations.forEach { item ->
                item.accept(this, data)
            }
        }
        return declaration
    }

    override fun visitSimpleFunction(
        declaration: IrSimpleFunction,
        data: Map<String, SugarIrData>,
    ): IrStatement {
        if (declaration.isQuackComponent) {
            if (declaration.hasAnnotation(NoSugarFqn)) {
                return super.visitSimpleFunction(declaration, data)
            }

            val referAnnotation = declaration.getAnnotation(SugarReferFqn) ?: run {
                val error = "The SugarRefer for the Sugar component is missing. (${declaration.name.asString()})"
                logger.error(
                    value = error,
                    location = declaration.file.locationOf(declaration),
                )
                error(error)
            }

            val referFqn = referAnnotation.getReferFqName()
            data[referFqn]?.let { referIrData ->
                declaration.valueParameters.forEach { parameter ->
                    parameter.defaultValue = referIrData.findMatchedDefaultValue(parameter) { error ->
                        logger.error(
                            value = error,
                            location = declaration.file.locationOf(parameter),
                        )
                        error(error)
                    }
                }
            } ?: run {
                val error = "No SugarIrData was found for the given SugarRefer. (${declaration.name.asString()}) " +
                        "Please report it in a GitHub Issue. (https://link.duckie.team/quackquack-bug)"
                logger.error(
                    value = error,
                    location = declaration.file.locationOf(declaration),
                )
                error(error)
            }
        }

        return super.visitSimpleFunction(declaration, data)
    }
}

private fun IrConstructorCall.getReferFqName(): String {
    // Assuming the first argument is always "fqn"
    val referFqnExpression = getValueArgument(0)
    return referFqnExpression.cast<IrConst<String>>().value
}

private fun SugarIrData.findMatchedDefaultValue(
    parameter: IrValueParameter,
    errorReporter: (error: String) -> Unit,
): IrExpressionBody? {
    val matched = parameters.find { referParameter ->
        referParameter.name.asString() == parameter.name.asString()
    }
    if (matched == null) {
        errorReporter(
            "The Sugar component has a parameter that doesn't exist in the SugarRefer. " +
                    "(${referFqn.asString()}#${parameter.name.asString()})",
        )
    }
    return matched?.defaultValue
}
