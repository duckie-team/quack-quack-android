/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(UnsafeCastFunction::class)

package team.duckie.quackquack.sugar.hosted.transformer

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
import org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction
import org.jetbrains.kotlin.utils.addToStdlib.cast
import team.duckie.quackquack.sugar.hosted.error.SugarTransformError
import team.duckie.quackquack.sugar.hosted.error.SugarVisitError
import team.duckie.quackquack.sugar.hosted.names.SugarGeneratedFileFqn
import team.duckie.quackquack.sugar.hosted.names.SugarReferFqn
import team.duckie.quackquack.sugar.hosted.node.SugarComponentNode
import team.duckie.quackquack.util.backend.kotlinc.Logger
import team.duckie.quackquack.util.backend.kotlinc.isQuackComponent
import team.duckie.quackquack.util.backend.kotlinc.locationOf

class SugarIrTransformer(
  @Suppress("unused") private val context: IrPluginContext,
  private val logger: Logger,
) : IrElementTransformer<Map<String, SugarComponentNode>> {
  override fun visitModuleFragment(
    declaration: IrModuleFragment,
    data: Map<String, SugarComponentNode>,
  ): IrModuleFragment {
    declaration.files.forEach { file -> file.accept(this, data) }
    return declaration
  }

  override fun visitFile(
    declaration: IrFile,
    data: Map<String, SugarComponentNode>,
  ): IrFile {
    if (declaration.hasAnnotation(SugarGeneratedFileFqn)) {
      declaration.declarations.forEach { item -> item.accept(this, data) }
    }
    return declaration
  }

  override fun visitSimpleFunction(
    declaration: IrSimpleFunction,
    data: Map<String, SugarComponentNode>,
  ): IrStatement {
    if (declaration.isQuackComponent) {
      val referAnnotation =
        declaration.getAnnotation(SugarReferFqn)
          ?: return super.visitSimpleFunction(declaration, data)
      val referFqn = referAnnotation.getReferFqName()

      data[referFqn]?.let { referIrData ->
        declaration.valueParameters.forEach { parameter ->
          parameter.defaultValue =
            referIrData.findMatchedDefaultValue(
              sugarComponentName = declaration.name.asString(),
              parameter = parameter,
              error = { message ->
                logger.throwError(
                  message = message,
                  location = declaration.file.locationOf(parameter),
                )
              },
            )
        }
      } ?: logger.throwError(
        message = SugarVisitError.noMatchedSugarComponentNode(declaration.name.asString()),
        location = declaration.file.locationOf(declaration),
      )
    }

    return super.visitSimpleFunction(declaration, data)
  }
}

private fun IrConstructorCall.getReferFqName(): String {
  // Assuming the first argument is always "fqn"
  val referFqnExpression = getValueArgument(0)
  return referFqnExpression.cast<IrConst<String>>().value
}

private fun SugarComponentNode.findMatchedDefaultValue(
  sugarComponentName: String,
  parameter: IrValueParameter,
  error: (message: String) -> Unit,
): IrExpressionBody? {
  val matched =
    parameters.find { referParameter ->
      referParameter.name.asString() == parameter.name.asString()
    }
  if (matched == null) {
    error(
      SugarTransformError.sugarComponentAndSugarReferHasDifferentParameters(
        "(refer) ${owner.name.asString()} -> (sugar) $sugarComponentName#${parameter.name.asString()}",
      ),
    )
  }
  return matched?.defaultValue
}
