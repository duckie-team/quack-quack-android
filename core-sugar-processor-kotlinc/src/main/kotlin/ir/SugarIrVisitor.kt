/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import Logger
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
                value = quackComponentFqnUnavailable(declaration),
                location = componentLocation,
            )

            if (declaration.hasAnnotation(NoSugarFqn)) return

            val sugarNameAnnotation = declaration.getAnnotation(SugarNameFqn)
            val sugarName = sugarNameAnnotation?.getSugarNameIfNotDefault()

            var sugarToken: IrValueParameter? = null
            val sugarParameters = declaration.valueParameters.map { parameter ->
                val isSugarToken = parameter.hasAnnotation(SugarTokenFqn)
                if (isSugarToken) {
                    check(sugarToken == null) {
                        multipleSugarTokenIsNotAllowed(declaration)
                    }
                    sugarToken = parameter
                }
                parameter.toSugarParameter(isToken = isSugarToken)
            }
            sugarToken ?: return logger.error(
                value = quackComponentWithoutSugarToken(componentFqn),
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
    if (!name.startsWith(QuackComponentPrefix)) {
        throw IllegalArgumentException(sugarNamePrefixIsNotQuack(name))
    }
    if (!name.contains(SugarTokenName)) {
        throw IllegalArgumentException(sugarNameWithoutTokenName(name))
    }
}

private fun IrValueParameter.toSugarParameter(isToken: Boolean): SugarParameter {
    val sugarImports = getAnnotation(ImportsFqn)?.let { sugarImportsAnnotation ->
        // Assuming the first argument is always "clazz"
        val sugarImportsExpression = sugarImportsAnnotation.getValueArgument(0)
        sugarImportsExpression.cast<IrVararg>().elements.map { element ->
            // clazz로 제공된 클래스의 fqn을 조회할 수 없으면 버그로 진행해야 함
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
    } ?: error(sugarTokenButNoCompanionObject(tokenClassName))
}
