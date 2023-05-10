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
import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
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
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.ir.util.properties
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.kdoc.psi.api.KDoc
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection
import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction
import org.jetbrains.kotlin.utils.addToStdlib.cast
import team.duckie.quackquack.util.backend.kotlinc.Logger
import team.duckie.quackquack.util.backend.kotlinc.isQuackComponent
import team.duckie.quackquack.util.backend.kotlinc.locationOf

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
      val componentFqn = declaration.fqNameWhenAvailable ?: logger.throwError(
        message = SourceError.quackComponentFqnUnavailable(declaration.name.asString()),
        location = componentLocation,
      )

      if (declaration.hasAnnotation(NoSugarFqn)) return

      val sugarNameAnnotation = declaration.getAnnotation(SugarNameFqn)
      val sugarName = sugarNameAnnotation?.getSugarNameIfNotDefault(owner = declaration)

      var sugarToken: IrValueParameter? = null
      val sugarParameters = declaration.valueParameters.map { parameter ->
        val isSugarToken = parameter.hasAnnotation(SugarTokenFqn)
        if (isSugarToken) {
          check(sugarToken == null) {
            SourceError.multipleSugarTokenIsNotAllowed(declaration.name.asString())
          }
          sugarToken = parameter
        }
        parameter.toSugarParameter(owner = declaration, isToken = isSugarToken)
      }
      sugarToken ?: logger.throwError(
        message = SourceError.quackComponentWithoutSugarToken(componentFqn.asString()),
        location = componentLocation,
      )

      val optins = declaration.annotations.filter { annotation ->
        annotation.symbol.owner.parentAsClass.hasAnnotation(RequiresOptInFqn)
      }

      val sugarIrData = SugarIrData(
        owner = declaration,
        referFqn = componentFqn,
        kdocGetter = { usedTokenLiteral ->
          declaration.getSugarKDoc(
            referFqn = componentFqn,
            tokenName = sugarToken!!.name.asString(),
            usedTokenLiteral = usedTokenLiteral,
          )
        },
        sugarName = sugarName,
        sugarToken = sugarToken!!,
        tokenFqExpressions = sugarToken!!.getAllTokenFqExpressions(),
        parameters = sugarParameters,
        optins = optins,
      )

      // logger(with(logger) { sugarIrData.prependLogPrefix(withNewline = true) })
      addSugarIrData(sugarIrData)
    }
  }
}

private fun IrConstructorCall.getSugarNameIfNotDefault(owner: IrFunction): String? {
  // Assuming the first argument is always "name"
  val sugarNameExpression = getValueArgument(0)
  return sugarNameExpression.cast<IrConst<String>>().value.takeIf { name ->
    (name != SugarDefaultName).also { isCustomSugarName ->
      if (isCustomSugarName) checkCustomSugarNameIsValid(owner, name)
    }
  }
}

private fun checkCustomSugarNameIsValid(owner: IrFunction, name: String) {
  val cause = "${owner.name.asString()} ($name)"

  require(name.startsWith(QuackComponentPrefix)) {
    SourceError.sugarNamePrefixIsNotQuack(cause)
  }
  require(name.contains(SugarTokenName)) {
    SourceError.sugarNameWithoutTokenName(cause)
  }
}

private fun IrValueParameter.toSugarParameter(owner: IrFunction, isToken: Boolean): SugarParameter {
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
        SourceError.importClazzFqnUnavailable(element.cast<IrClassReference>().type.asString()),
      )
    }
  }
  val isComposable = hasAnnotation(ComposableFqn)

  return SugarParameter(
    owner = owner,
    name = name,
    type = type,
    isToken = isToken,
    isComposable = isComposable,
    imports = sugarImports.orEmpty(),
    casaValueLiteral = casaValue,
    defaultValue = defaultValue,
  )
}

// TODO(1): util-backend-kotlinc로 KDoc 추출 공통 로직 분리
// TODO: default section은 없고 KDoc Tag만 있는 경우는 로직이 어떻게?
// TODO: subject의 fqn을 가져올 수는 없을까?
private fun IrSimpleFunction.getSugarKDoc(
  referFqn: FqName,
  tokenName: String,
  usedTokenLiteral: String,
): String {
  val usedTokenComment = "This component uses [$usedTokenLiteral] as the token value for `$tokenName`."
  val generatedDocComment = "This document was automatically generated by [${referFqn.shortName().asString()}].\n" +
    "If any contents are broken, please check the original document."

  val kdocArea = psiElement?.children?.firstOrNull { it is KDoc } as? KDoc
  val kdocDefaultSection = kdocArea?.getDefaultSection() ?: return ""

  val kdocTags = kdocArea.children
    .firstOrNull { it is KDocSection }
    ?.children
    ?.filterIsInstance<KDocTag>()
    .orEmpty()

  return buildString {
    appendLine(kdocDefaultSection.getContent().trim())
    appendLine("\n$usedTokenComment\n\n$generatedDocComment\n")
    for (tag in kdocTags) {
      val tagName = tag.name?.let { "@$it " }.orEmpty()
      var subjectName = tag.getSubjectName()
      if (subjectName == tokenName) continue
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
