/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import common.Empty
import common.Names.ComposableFqn
import common.Names.QuackComponentPrefix
import common.addGeneratedComment
import common.addSuppressAnnotation
import java.io.File
import kotlin.reflect.KClass
import org.jetbrains.kotlin.utils.addToStdlib.applyIf

private const val SugarNamespace = "team.duckie.quackquack.sugar"

private typealias Import = KClass<*>
internal typealias Imports = List<Import>

private typealias CoreSugarParameters = List<CoreSugarParameter>
private typealias ComponentAndCoreSugarParameters = Pair<KSFunctionDeclaration, CoreSugarParameters>

private fun KSName.toCoreComponentName(token: String?): String {
    var name = asString()
    if (token != null) {
        name = name
            .toMutableList()
            .apply { addAll(QuackComponentPrefix.length, token.toList()) }
            .joinToString("")
    }
    return name
}

private fun ComponentAndCoreSugarParameters.createSugarComponentFunction(
    @Suppress("UNUSED_PARAMETER") logger: KSPLogger,
): Pair<FunSpec, Imports> {
    val (component, coreSugarParameters) = this

    val imports = mutableListOf<Import>()
    var sugarToken: String? = null
    val parameters = coreSugarParameters.map { parameter ->
        parameter.imports?.let(imports::addAll)
        parameter.sugarToken?.let { token ->
            check(sugarToken == null) { "sugarToken is already set" }
            sugarToken = token
        }
        parameter.toParameterSpec()
    }

    val sugarName = component.simpleName.toCoreComponentName(sugarToken)
    val sugarBody = buildCodeBlock {
        addStatement("%L(", component.simpleName.asString())
        withIndent {
            component.parameters.forEach { parameter ->
                val parameterName = parameter.name!!.asString()
                if (parameterName != sugarToken) {
                    addStatement("%L = %L", parameterName, parameterName)
                }
            }
        }
        addStatement(")")
    }
    val sugarFunSpec = FunSpec
        .builder(sugarName)
        .addAnnotation(ClassName.bestGuess(ComposableFqn))
        .addModifiers(KModifier.PUBLIC)
        .addParameters(parameters)
        .addCode(sugarBody)
        .applyIf(component.docString != null) {
            addKdoc(component.docString!!)
        }
        .build()

    return sugarFunSpec to imports
}

private fun List<ComponentAndCoreSugarParameters>.createSugarComponentFunctions(
    logger: KSPLogger,
): Pair<List<FunSpec>, Imports> {
    val components = this
    val sugarSpecs = mutableListOf<FunSpec>()
    val sugarImports = mutableListOf<Import>()
    components.forEach { component ->
        val (funSpec, imports) = component.createSugarComponentFunction(logger)
        sugarSpecs += funSpec
        sugarImports += imports
    }
    return sugarSpecs to sugarImports
}

private fun generateSugarKt(
    @Suppress("SameParameterValue") packageName: String,
    fileName: String,
    components: List<ComponentAndCoreSugarParameters>,
    logger: KSPLogger,
): FileSpec {
    val (sugarSpecs, sugarImports) = components.createSugarComponentFunctions(logger)
    return FileSpec
        .builder(
            packageName = packageName,
            fileName = fileName,
        )
        .addGeneratedComment()
        .addSuppressAnnotation()
        .addImports(sugarImports)
        .addFunctions(sugarSpecs)
        .build()
}

internal fun generateSugarKts(
    components: Map<String, List<ComponentAndCoreSugarParameters>>,
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
    sugarPath: String?,
) {
    val sugarFileSpecs = components.map { (fileName, componentsPerFile) ->
        generateSugarKt(
            packageName = SugarNamespace,
            fileName = fileName,
            components = componentsPerFile,
            logger = logger,
        )
    }

    sugarFileSpecs.forEach { sugarFileSpec ->
        val generatedPath: String
        if (sugarPath == null) {
            val aideMapKt = codeGenerator.createNewFile(
                dependencies = Dependencies.Empty,
                packageName = sugarFileSpec.packageName,
                fileName = sugarFileSpec.name,
            )
            aideMapKt.writer().use(sugarFileSpec::writeTo)
            generatedPath = sugarFileSpec.packageName + "/" + sugarFileSpec.name + ".kt"
        } else {
            val aideMapKt = File(sugarPath, "${sugarFileSpec.name}.kt").also { file ->
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
            }
            aideMapKt.writeText(sugarFileSpec.toString())
            generatedPath = aideMapKt.path
        }
        logger.warn("[SUGAR] generated at $generatedPath")
    }
}
