/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.processor.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import java.io.File
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.name.FqName
import team.duckie.quackquack.sugar.processor.ir.CasaCn
import team.duckie.quackquack.sugar.processor.ir.ComposableCn
import team.duckie.quackquack.sugar.processor.ir.SugarCompilerApiCn
import team.duckie.quackquack.sugar.processor.ir.SugarFqn
import team.duckie.quackquack.sugar.processor.ir.SugarGeneratedFileCn
import team.duckie.quackquack.sugar.processor.ir.SugarGeneratorUsageCn
import team.duckie.quackquack.sugar.processor.ir.SugarIrData
import team.duckie.quackquack.sugar.processor.ir.SugarParameter
import team.duckie.quackquack.sugar.processor.ir.SugarReferCn
import team.duckie.quackquack.util.backend.FormatterOffComment
import team.duckie.quackquack.util.backend.SuppressAnnotation
import team.duckie.quackquack.util.backend.addFunctions
import team.duckie.quackquack.util.backend.addImports
import team.duckie.quackquack.util.backend.bestGuessToKotlinPackageName
import team.duckie.quackquack.util.backend.getGeneratedComment

private val GeneratedComment = getGeneratedComment("sugar-processor")

@Suppress("OPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION")
private val SugarCompilerOptInAnnotation = AnnotationSpec
    .builder(OptIn::class)
    .addMember(
        "%T::class, %T::class",
        SugarCompilerApiCn,
        SugarGeneratorUsageCn,
    )
    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
    .build()

private val SugarGeneratedFileMarkerAnnotation = AnnotationSpec
    .builder(SugarGeneratedFileCn)
    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
    .build()

internal fun generateSugarComponentFiles(
    irDatas: List<SugarIrData>,
    sugarPath: String,
) {
    val fileGroupedIrDatas = irDatas.groupBy { irData ->
        irData.file.name
    }

    fileGroupedIrDatas.forEach { (fileName, irDatas) ->
        val (imports, funSpecs) = irDatas.toFunSpecsWithImports()
        val ktSpec = FileSpec
            .builder(
                packageName = sugarPath.bestGuessToKotlinPackageName(),
                fileName = fileName.substringBeforeLast("."),
            )
            .addFileComment(GeneratedComment)
            .addFileComment(FormatterOffComment)
            .addAnnotation(SuppressAnnotation)
            .addAnnotation(SugarCompilerOptInAnnotation)
            .addAnnotation(SugarGeneratedFileMarkerAnnotation)
            .addImports(imports.toMutableList().apply { add(SugarFqn) })
            .addFunctions(funSpecs)
            .build()

        File(sugarPath, fileName).also { file ->
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                file.createNewFile()
            }
        }.writeText(ktSpec.toString())
    }
}

private fun List<SugarIrData>.toFunSpecsWithImports(): Pair<List<FqName>, List<FunSpec>> {
    val imports = mutableListOf<FqName>()
    val funSpecs = mutableListOf<FunSpec>()
    forEach { sugarIrData ->
        imports += sugarIrData.referFqn
        sugarIrData.tokenFqExpressions.forEach { tokenFqExpression ->
            @Suppress("WRONG_ANNOTATION_TARGET", "LocalVariableName")
            val (_imports, funSpec) = sugarIrData.toFunSpecWithImports(tokenFqExpression)
            imports += _imports
            funSpecs += funSpec
        }
    }
    return imports to funSpecs
}

private fun SugarIrData.toFunSpecWithImports(tokenFqExpression: String): Pair<List<FqName>, FunSpec> {
    val imports = mutableListOf<FqName>()

    val sugarReferAnnotation = AnnotationSpec
        .builder(SugarReferCn)
        .addMember("%S", referFqn.asString())
        .build()

    val sugarName = toSugarComponentName(tokenFqExpression)
    val sugarBody = buildCodeBlock {
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

    val funSpec = FunSpec
        .builder(sugarName)
        .addAnnotation(ComposableCn)
        .addAnnotation(CasaCn)
        .addAnnotation(sugarReferAnnotation)
        .addModifiers(KModifier.PUBLIC)
        .addParameters(parametersWithoutToken.map(SugarParameter::toParameterSpec))
        .addCode(sugarBody)
        .addKdoc(kdoc)
        .build()

    return imports to funSpec
}
