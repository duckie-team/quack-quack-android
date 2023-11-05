/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.util.backend.kotlinpoet.getGeneratedFileComment
import team.duckie.quackquack.util.backend.ksp.generateBuildOrLocalFile
import team.duckie.quackquack.util.backend.ksp.requireContainingFile

// @Suppress("OPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION")
// private val OptInCompilerAnnotation =
//  AnnotationSpec
//    .builder(OptIn::class)
//    .addMember(
//      "%T::class",
//      ExperimentalQuackQuackApiCn,
//    )
//    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
//    .build()

private val GeneratedComment = getGeneratedFileComment("casa-processor")
private val RequiredImports =
  listOf(
    "kotlinx.collections.immutable.persistentListOf",
    "kotlinx.collections.immutable.toImmutableList",
    "androidx.compose.runtime.Composable",
  )

internal fun generateCasaModels(
  codeGenerator: CodeGenerator,
  logger: KSPLogger,
  casas: Sequence<KSFunctionDeclaration>,
  casaPath: String?,
) {
  val imports =
    mutableListOf<String>().also { imports ->
      imports += RequiredImports
    }
  val casasWithDomainGroup =
    casas.groupBy { declaration ->
      declaration.requireContainingFile.fileName.removeSuffix(".kt")
    }
  val groupedCasasWithNameGroup =
    buildMap(capacity = casasWithDomainGroup.size) {
      casasWithDomainGroup.forEach { (domain, casas) ->
        val casasWithNameGroup = casas.groupBy { casa ->
          casa.parseSugarRefer().substringAfterLast(".")
        }
        set(domain, casasWithNameGroup)
      }
    }
  val casaModelPropertySpecs =
    buildList {
      groupedCasasWithNameGroup.forEach { (domain, casasWithNameGroup) ->
        casasWithNameGroup.forEach { (name, casas) ->
          val (_imports, casaModelPropertySpec) = createCasaModelPropertySpecWithImports(
            domain = domain,
            name = name,
            casas = casas,
          )
          imports += _imports
          add(casaModelPropertySpec)
        }
      }
    }
  val casaModelPropertySpecsAccessor =
    PropertySpec
      .builder(
        name = "casaModels",
        type = ImmutableList::class.asClassName().parameterizedBy(CasaModelCn),
      )
      .initializer(
        codeBlock = buildCodeBlock {
          addStatement("persistentListOf(")
          withIndent {
            casaModelPropertySpecs.forEach { casaModelPropertySpec ->
              addStatement("${casaModelPropertySpec.name},")
            }
          }
          addStatement(")")
        },
      )
      .addModifiers(KModifier.PUBLIC)
      .build()

  val casaModelFileSpec =
    FileSpec
      .builder(
        packageName = "",
        fileName = "CasaModels",
      )
      .addFileComment(GeneratedComment)
      .apply {
        // FIXME OptInCompilerAnnotation 추가 시 코드가 제대로 생성되지 않음
//        addAnnotation(OptInCompilerAnnotation)
//         memberImports += if (packageName.isNotEmpty()) {
//           Import("$packageName.$import")
//         } else {
//           Import(import)
//         }
        addImport(packageName = "", imports)
        casaModelPropertySpecs.forEach(::addProperty)
      }
      .addProperty(casaModelPropertySpecsAccessor)
      .build()

  generateBuildOrLocalFile(
    codeGenerator = codeGenerator,
    fileSpec = casaModelFileSpec,
    path = casaPath,
    logger = logger,
    tag = "casa-processor",
  )
}

private fun KSFunctionDeclaration.toCasaComponentLiteralWithImport(): Pair<String, String> {
  val import = "team.duckie.quackquack.ui.sugar.${simpleName.asString()}"
  val parameterValueMap =
    parameters.mapNotNull { parameter ->
      if (parameter.hasDefault) {
        return@mapNotNull null
      }
      val name = parameter.name!!.asString()
      val value =
        parameter.annotations.singleOrNullStrict { annotation ->
          annotation.shortName.asString() == CasaValueSn
        }
      if (value != null) {
        name to value.arguments.single().value as String
      } else if (parameter.type.resolve().isMarkedNullable) {
        name to "null"
      } else {
        error("Argument $name is non-null and no `CasaValue` was provided.")
      }
    }
  val componentLiteral =
    buildString {
      appendLine("${simpleName.asString()}(")
      parameterValueMap.forEach { (name, value) ->
        appendLineWithIndent("$name = $value,")
      }
      append(")")
    }

  return import to componentLiteral
}

private fun StringBuilder.appendCasaComponentPairWithImport(casa: KSFunctionDeclaration): Pair<String, StringBuilder> {
  val name = casa.simpleName.asString()
  val (import, componentLiteral) = casa.toCasaComponentLiteralWithImport()
  val component = "{ $componentLiteral }"
  return import to append("\"$name\" to $component")
}

private fun createCasaModelPropertySpecWithImports(
  domain: String,
  name: String,
  casas: List<KSFunctionDeclaration>,
): Pair<List<String>, PropertySpec> {
  val imports =
    casas
      .first()
      .parameters
      .map { parameter ->
        parameter.type.resolve().declaration.qualifiedName!!.asString()
      }
      .toMutableList()
  val kdocString =
    casas
      .first()
      .docString
      .orEmpty()
      .split("This document was automatically generated")
      .first()
      .trimIndent()
  val components =
    buildString {
      appendLine("persistentListOf<Pair<String, @Composable () -> Unit>>(")
      casas.forEach { casa ->
        appendCasaComponentPairWithImport(casa = casa).let { (import, _) ->
          imports += import
        }
        appendLine(",")
      }
      append(").toImmutableList()")
    }

  val casaModelPropertySpec =
    PropertySpec
      .builder(
        name = "${domain}${name}CasaModel",
        type = CasaModelCn,
      )
      .addModifiers(KModifier.PRIVATE)
      .initializer(
        codeBlock = buildCodeBlock {
          addStatement("CasaModel(")
          withIndent {
            addStatement("name = %S,", name)
            addStatement("domain = %S,", domain)
            addStatement("kdocDefaultSection = %S,", kdocString)
            addStatement("components = %L,", components)
          }
          addStatement(")")
        },
      )
      .build()

  return imports to casaModelPropertySpec
}

private fun KSFunctionDeclaration.parseSugarRefer(): String {
  val sugarRefer =
    annotations.singleOrNullStrict { annotation ->
      annotation.shortName.asString() == SugarReferSn
    } ?: error("casa-processor only supports sugar components.")

  return sugarRefer.arguments.first().value as String
}
