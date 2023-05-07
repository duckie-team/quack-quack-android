/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(UnsafeCastFunction::class)

package team.duckie.quackquack.sugar.processor.ir

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
import team.duckie.quackquack.util.backend.Logger
import team.duckie.quackquack.util.backend.isQuackComponent
import team.duckie.quackquack.util.backend.locationOf

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
    if (declaration.hasAnnotation(SugarGeneratedFileFqn)) {
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

      // run으로 throwError 하는 게 더 가독성이 좋음
      val referAnnotation = declaration.getAnnotation(SugarReferFqn) ?: run {
        logger.throwError(
          value = PoetError.sugarComponentButNoSugarRefer(declaration),
          location = declaration.file.locationOf(declaration),
        )
      }
      val referFqn = referAnnotation.getReferFqName()

      data[referFqn]?.let { referIrData ->
        declaration.valueParameters.forEach { parameter ->
          parameter.defaultValue = referIrData.findMatchedDefaultValue(
            parameter = parameter,
            error = { message ->
              logger.throwError(
                value = message,
                location = declaration.file.locationOf(parameter),
              )
            },
          )
        }
      } ?: logger.throwError(
        value = SugarVisitError.noMatchedSugarIrData(declaration),
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

private fun SugarIrData.findMatchedDefaultValue(
  parameter: IrValueParameter,
  error: (message: String) -> Unit,
): IrExpressionBody? {
  val matched = parameters.find { referParameter ->
    referParameter.name.asString() == parameter.name.asString()
  }
  if (matched == null) {
    error(
      SugarTransformError.sugarComponentAndSugarReferHasDifferentParameters(
        sugarIrData = this,
        parameter = parameter,
      ),
    )
  }
  return matched?.defaultValue
}
