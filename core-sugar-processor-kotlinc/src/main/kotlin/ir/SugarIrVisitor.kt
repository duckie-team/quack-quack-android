/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import ComposableFqn
import GeneratedFileFqn
import ImportsFqn
import Logger
import NoSugarFqn
import QuackComponentPrefix
import SugarDefaultName
import SugarNameFqn
import SugarTokenFqn
import SugarTokenName
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.jvm.ir.psiElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrClassReference
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.IrVararg
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.types.getClass
import org.jetbrains.kotlin.ir.util.companionObject
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.ir.util.getAnnotation
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.util.properties
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.utils.addToStdlib.cast
import prependLogPrefix

internal class SugarIrVisitor(
    @Suppress("unused") private val context: IrPluginContext,
    private val logger: Logger,
    private val addSugarIrData: (data: SugarIrData) -> Unit,
) : IrElementVisitorVoid {
    override fun visitModuleFragment(declaration: IrModuleFragment) {
        declaration.files.forEach { file ->
            file.accept(this, null)
        }
    }

    override fun visitFile(declaration: IrFile) {
        if (declaration.hasAnnotation(GeneratedFileFqn)) return
        declaration.declarations.forEach { item ->
            item.accept(this, null)
        }
    }

    override fun visitSimpleFunction(declaration: IrSimpleFunction) {
        if (declaration.isQuackComponent) {
            val componentLocation = declaration.file.locationOf(declaration)
            val componentFqn = declaration.fqNameWhenAvailable ?: return logger.error(
                value = "A Quack component was detected, but unable to look up a fully qualified name. " +
                        "Is it an anonymous object? (${declaration.name.asString()})",
                location = componentLocation,
            )

            if (declaration.hasAnnotation(NoSugarFqn)) return

            val sugarAnnotation = declaration.getAnnotation(SugarNameFqn)
            val sugarName = sugarAnnotation?.getSugarNameIfNotDefault()

            var sugarToken: IrValueParameter? = null
            val sugarParameters = declaration.valueParameters.map { parameter ->
                val isSugarToken = parameter.hasAnnotation(SugarTokenFqn)
                if (isSugarToken) {
                    check(sugarToken == null) {
                        "A Sugar component can only contain one SugarToken. (${declaration.name.asString()})"
                    }
                    sugarToken = parameter
                }
                parameter.toSugarParameter(isToken = isSugarToken)
            }
            sugarToken ?: return logger.error(
                value = "A Quack component was detected, but no SugarToken was applied. " +
                        "(${componentFqn.asString()})",
                location = componentLocation,
            )

            val sugarIrData = SugarIrData(
                file = declaration.file,
                referFqn = componentFqn,
                kdoc = declaration.getSugarKDoc(referFqn = componentFqn),
                sugarName = sugarName,
                sugarToken = sugarToken!!,
                tokenFqExpressions = sugarToken!!.getAllTokenFqExpressions(),
                parameters = sugarParameters,
            )

            logger(sugarIrData.prependLogPrefix(withNewline = true))
            addSugarIrData(sugarIrData)
        }
    }
}

private fun IrConstructorCall.getSugarNameIfNotDefault(): String? {
    // Assuming the first argument is always "name"
    val sugarNameExpression = getValueArgument(0)
    return sugarNameExpression.cast<IrConst<String>>().value.takeIf { name ->
        (name != SugarDefaultName).also { isCustomSugarName ->
            if (isCustomSugarName) checkCustomSugarNameIsValid(name)
        }
    }
}

private fun checkCustomSugarNameIsValid(name: String) {
    val prefixIsNotQuackError =
        "Quack component names must start with `SugarName.PREFIX_NAME (= $QuackComponentPrefix)`."
    val tokenNameIsNotUsedError =
        "When specifying the sugar component name directly, `SugarName.TOKEN_NAME (= $SugarTokenName)` must be used."

    if (!name.startsWith(QuackComponentPrefix)) {
        throw IllegalArgumentException(prefixIsNotQuackError)
    }
    if (!name.contains(SugarTokenName)) {
        throw IllegalArgumentException(tokenNameIsNotUsedError)
    }
}

private fun IrValueParameter.toSugarParameter(isToken: Boolean): SugarParameter {
    val sugarImportsAnnotation = getAnnotation(ImportsFqn)
    val sugarImports = sugarImportsAnnotation?.let {
        // Assuming the first argument is always "clazz"
        val sugarImportsExpression = sugarImportsAnnotation.getValueArgument(0)
        sugarImportsExpression.cast<IrVararg>().elements.map { element ->
            element.cast<IrClassReference>().classType.classFqName!!
        }
    }

    return SugarParameter(
        name = name,
        type = type,
        isToken = isToken,
        isComposable = hasAnnotation(ComposableFqn),
        imports = sugarImports.orEmpty(),
        defaultValue = defaultValue,
    )
}

private fun IrSimpleFunction.getSugarKDoc(referFqn: FqName): String {
    var kdoc = psiElement?.cast<KtDeclaration>()?.docComment
        ?.getDefaultSection()?.getContent()?.trim()?.plus("\n\n")
        .orEmpty()
    kdoc += "This document was auto-generated. Please see [${referFqn.asString()}] for details."

    return kdoc
}

private fun IrValueParameter.getAllTokenFqExpressions(): List<String> {
    val tokenClass = type.getClass()!!
    val tokenClassName = tokenClass.name.asString()
    return tokenClass.companionObject()?.let { companion ->
        val tokenableProperties = companion.properties.filter { property ->
            property.visibility.isPublicAPI
        }
        val propertyFqExpressions = tokenableProperties.map { property ->
            "$tokenClassName.${property.name.asString()}"
        }
        propertyFqExpressions.toList()
    } ?: error(
        "" +
                "The SugarToken class must include a companion object. " +
                "See the sugar component creation policy for more information. ($tokenClassName)",
    )
}
