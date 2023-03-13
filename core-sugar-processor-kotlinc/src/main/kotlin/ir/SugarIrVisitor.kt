/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocationWithRange
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.extensions.AnnotationBasedExtension
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.descriptors.toIrBasedDescriptor
import org.jetbrains.kotlin.ir.types.isUnit
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.psi.KtModifierListOwner

internal const val LogPrefix = "[SUGAR (IR)]"
internal const val QuackComponentPrefix = "Quack"

internal const val ComposableFqn = "androidx.compose.runtime.Composable"
internal const val SugarTokenFqn = "team.duckie.quackquack.sugar.annotation.SugarToken"
internal const val SugarImportFqn = "team.duckie.quackquack.sugar.annotation.Import"

internal class SugarIrVisitor(
    private val context: IrPluginContext,
    private val messageCollector: MessageCollector,
    private val sugarPath: String,
) : AnnotationBasedExtension, IrElementVisitorVoid {
    override fun getAnnotationFqNames(modifierListOwner: KtModifierListOwner?): List<String> {
        return listOf(ComposableFqn)
    }

    override fun visitSimpleFunction(declaration: IrSimpleFunction) {
        if (declaration.isPublicQuackComponent) {
            log(declaration.name.asString())
            declaration.valueParameters.forEach { parameter ->
                with(parameter) {
                    log(
                        """
                        name: ${name.asString()}
                        type: ${type.asString()}
                        defaultValue: $defaultValue
                        """.trimIndent(),
                    )
                }
            }
            log("-".repeat(10))
        }
    }

    private val IrFunction.isPublicQuackComponent: Boolean
        get() {
            // 0. 컴포저블 함수이고,
            // 1. 공개 함수이며,
            // 2. 함수명이 Quack으로 시작하고,
            // 3. 확장 함수가 아니고,
            // 4. 반환 타입이 없어야 함

            return toIrBasedDescriptor().hasSpecialAnnotation(null) &&
                    visibility == DescriptorVisibilities.PUBLIC &&
                    name.asString().startsWith(QuackComponentPrefix) &&
                    extensionReceiverParameter == null &&
                    returnType.isUnit()
        }

    // private val IrFunction.isSugarComponent: Boolean
    //     get() {
    //         // @SugarToken 어노테이션이 붙은 타입을 사용중인 인자가 있어야 함
    //     }

    private fun log(message: Any) {
        messageCollector.report(CompilerMessageSeverity.WARNING, "$LogPrefix $message")
    }

    private fun IrDeclaration.reportError(message: String) {
        val location = file.locationOf(this)
        messageCollector.report(CompilerMessageSeverity.ERROR, "$LogPrefix $message", location)
    }

    /**
     * Finds the line and column of [irElement] within this file.
     */
    private fun IrFile.locationOf(irElement: IrElement?): CompilerMessageSourceLocation {
        val sourceRangeInfo = fileEntry.getSourceRangeInfo(
            beginOffset = irElement?.startOffset ?: SYNTHETIC_OFFSET,
            endOffset = irElement?.endOffset ?: SYNTHETIC_OFFSET,
        )
        return CompilerMessageLocationWithRange.create(
            path = sourceRangeInfo.filePath,
            lineStart = sourceRangeInfo.startLineNumber + 1,
            columnStart = sourceRangeInfo.startColumnNumber + 1,
            lineEnd = sourceRangeInfo.endLineNumber + 1,
            columnEnd = sourceRangeInfo.endColumnNumber + 1,
            lineContent = null,
        )!!
    }
}
