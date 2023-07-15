/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.hosted.codegen

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import java.io.File
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.name.FqName
import team.duckie.quackquack.sugar.hosted.names.CasaCn
import team.duckie.quackquack.sugar.hosted.names.ComposableCn
import team.duckie.quackquack.sugar.hosted.names.NonRestartableComposableCn
import team.duckie.quackquack.sugar.hosted.names.SugarCompilerApiCn
import team.duckie.quackquack.sugar.hosted.names.SugarFqn
import team.duckie.quackquack.sugar.hosted.names.SugarGeneratedFileCn
import team.duckie.quackquack.sugar.hosted.names.SugarGeneratorUsageCn
import team.duckie.quackquack.sugar.hosted.names.SugarReferCn
import team.duckie.quackquack.sugar.hosted.node.SugarComponentNode
import team.duckie.quackquack.sugar.hosted.node.SugarParameter
import team.duckie.quackquack.util.backend.kotlinc.addImports
import team.duckie.quackquack.util.backend.kotlinc.toFqnStringOrEmpty
import team.duckie.quackquack.util.backend.kotlinpoet.addAnnotations
import team.duckie.quackquack.util.backend.kotlinpoet.addFunctions
import team.duckie.quackquack.util.backend.kotlinpoet.getGeneratedFileComment

private val GeneratedComment = getGeneratedFileComment("sugar-core")

@Suppress("OPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION")
private val SugarCompilerOptInAnnotation =
  AnnotationSpec
    .builder(OptIn::class)
    .addMember(
      "%T::class, %T::class",
      SugarCompilerApiCn,
      SugarGeneratorUsageCn,
    )
    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
    .build()

private val SugarGeneratedFileMarkerAnnotation =
  AnnotationSpec
    .builder(SugarGeneratedFileCn)
    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
    .build()

fun generateSugarComponentFiles(sugarComponentNodes: List<SugarComponentNode>, sugarPath: String) {
  val fileGroupedNodeDatas = sugarComponentNodes.groupBy { node -> node.owner.file.name }

  fileGroupedNodeDatas.forEach { (fileName, componentNode) ->
    val (imports, funSpecs) = componentNode.toFunSpecsWithImports()
    val ktSpec =
      FileSpec
        .builder(
          packageName = sugarPath.bestGuessToKotlinPackageName(),
          fileName = fileName.substringBeforeLast("."),
        )
        .addFileComment(GeneratedComment)
        .addAnnotations(
          SugarCompilerOptInAnnotation,
          SugarGeneratedFileMarkerAnnotation,
        )
        .addImports(imports.toMutableList().apply { add(SugarFqn) })
        .addFunctions(funSpecs)
        .build()

    File(sugarPath, fileName)
      .also { file ->
        if (!file.exists()) {
          file.parentFile?.mkdirs()
          file.createNewFile()
        }
      }
      .writeText(ktSpec.toString())
  }
}

private fun List<SugarComponentNode>.toFunSpecsWithImports(): Pair<List<FqName>, List<FunSpec>> {
  val imports = mutableListOf<FqName>()
  val funSpecs = mutableListOf<FunSpec>()
  forEach { sugarIrData ->
    imports += sugarIrData.referFqn
    sugarIrData.tokenFqExpressions.forEach { tokenFqExpression ->
      val (_imports, funSpec) = sugarIrData.toFunSpecWithImports(tokenFqExpression)
      imports += _imports
      funSpecs += funSpec
    }
  }
  return imports to funSpecs
}

private fun SugarComponentNode.toFunSpecWithImports(tokenFqExpression: String): Pair<List<FqName>, FunSpec> {
  val imports = mutableListOf<FqName>()

  val sugarReferAnnotation =
    AnnotationSpec
      .builder(SugarReferCn)
      .addMember("%S", referFqn.asString())
      .build()

  val sugarName = toSugarComponentName(tokenFqExpression)
  val sugarBody =
    buildCodeBlock {
      addStatement("%L(", referFqn.shortName().asString())
      withIndent {
        parameters.forEach { parameter ->
          imports += parameter.type.classFqName!!
          imports += parameter.imports

          val parameterName = parameter.name.asString()
          val parameterValue = if (parameter.isToken) tokenFqExpression else parameterName

          addStatement("%L = %L,", parameterName, parameterValue)
        }
      }
      addStatement(")")
    }

  val optinCns =
    optins.map { irOptin ->
      ClassName.bestGuess(irOptin.toFqnStringOrEmpty())
    }

  val funSpec =
    FunSpec
      .builder(sugarName)
      .addAnnotations(
        CasaCn,
        ComposableCn,
        NonRestartableComposableCn,
        *optinCns.toTypedArray(),
      )
      .addAnnotation(sugarReferAnnotation)
      .addModifiers(KModifier.PUBLIC)
      .addParameters(parametersWithoutToken.map(SugarParameter::toParameterSpec))
      .addCode(sugarBody)
      .addKdoc(kdocGetter(tokenFqExpression))
      .build()

  return imports to funSpec
}
