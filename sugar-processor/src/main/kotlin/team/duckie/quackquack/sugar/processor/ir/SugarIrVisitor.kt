/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(UnsafeCastFunction::class)

package team.duckie.quackquack.sugar.processor.ir

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
import org.jetbrains.kotlin.kdoc.psi.api.KDoc
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection
import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction
import org.jetbrains.kotlin.utils.addToStdlib.cast
import team.duckie.quackquack.util.backend.Logger
import team.duckie.quackquack.util.backend.isQuackComponent
import team.duckie.quackquack.util.backend.locationOf
import team.duckie.quackquack.util.backend.prependLogPrefix

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
        if (declaration.hasAnnotation(SugarGeneratedFileFqn)) return
        declaration.declarations.forEach { item ->
            item.accept(this, null)
        }
    }

    override fun visitSimpleFunction(declaration: IrSimpleFunction) {
        if (declaration.isQuackComponent) {
            val componentLocation = declaration.file.locationOf(declaration)
            val componentFqn = declaration.fqNameWhenAvailable ?: return logger.error(
                value = SourceError.quackComponentFqnUnavailable(declaration),
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
                        SourceError.multipleSugarTokenIsNotAllowed(declaration)
                    }
                    sugarToken = parameter
                }
                parameter.toSugarParameter(isToken = isSugarToken)
            }
            sugarToken ?: return logger.error(
                value = SourceError.quackComponentWithoutSugarToken(componentFqn),
                location = componentLocation,
            )

            val sugarIrData = SugarIrData(
                file = declaration.file,
                referFqn = componentFqn,
                kdoc = declaration.getSugarKDoc(
                    referFqn = componentFqn,
                    sugarTokenName = sugarToken!!.name.asString(),
                ),
                sugarName = sugarName,
                sugarToken = sugarToken!!,
                tokenFqExpressions = sugarToken!!.getAllTokenFqExpressions(),
                parameters = sugarParameters,
            )

            logger(with(logger) { sugarIrData.prependLogPrefix(withNewline = true) })
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
    require(name.startsWith(QuackComponentPrefix)) {
        SourceError.sugarNamePrefixIsNotQuack(name)
    }
    require(name.contains(SugarTokenName)) {
        SourceError.sugarNameWithoutTokenName(name)
    }
}

private fun IrValueParameter.toSugarParameter(isToken: Boolean): SugarParameter {
    val casaValue = getAnnotation(CasaValueFqn)?.let { casaValueAnnotation ->
        // Assuming the first argument is always "literal"
        val casaValueExpression = casaValueAnnotation.getValueArgument(0)
        casaValueExpression.cast<IrConst<String>>().value
    }
    val sugarImports = getAnnotation(ImportsFqn)?.let { sugarImportsAnnotation ->
        // Assuming the first argument is always "clazz"
        val sugarImportsExpression = sugarImportsAnnotation.getValueArgument(0)
        sugarImportsExpression.cast<IrVararg>().elements.map { element ->
            element.cast<IrClassReference>().classType.classFqName ?: error(
                message = SourceError.importClazzFqnUnavailable(element),
            )
        }
    }

    return SugarParameter(
        name = name,
        type = type,
        isToken = isToken,
        imports = sugarImports.orEmpty(),
        casaValueLiteral = casaValue,
        defaultValue = defaultValue,
    )
}

// TODO(2): 사용된 토큰 기재
// TODO(1): util-backend로 KDoc 추출 공통 로직 분리
private fun IrSimpleFunction.getSugarKDoc(referFqn: FqName, sugarTokenName: String): String {
    val generatedDocComment = "This document was automatically generated by [${referFqn.asString()}].\n" +
            "If any links are broken, please check the original document."

    val kdocArea = psiElement?.children?.firstOrNull { it is KDoc } as? KDoc
    val kdocDefaultSection = kdocArea?.getDefaultSection() ?: return ""

    val kdocTags = kdocArea.children
        .firstOrNull { it is KDocSection }
        ?.children
        ?.filterIsInstance<KDocTag>()
        .orEmpty()

    return buildString {
        appendLine(kdocDefaultSection.getContent().trim())
        appendLine("\n$generatedDocComment\n")
        for (tag in kdocTags) {
            val tagName = tag.name?.let { "@$it " }.orEmpty()
            var subjectName = tag.getSubjectName()
            if (subjectName == sugarTokenName) continue
            subjectName = subjectName?.plus(" ").orEmpty()

            appendLine("${tagName}${subjectName}${tag.getContent()}")
        }
    }
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
    } ?: error(SourceError.sugarTokenButNoCompanionObject(tokenClassName))
}
