/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import Names.ComposableFqn
import Names.QuackComponentPrefix
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import kotlin.reflect.KClass

private const val CoreNamespace = "team.duckie.quackquack.sugar"

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

private fun ComponentAndCoreSugarParameters.createSugarComponentFunction(): Pair<FunSpec, Imports> {
    val (component, coreSugarParameters) = this

    val imports = mutableListOf<Import>()
    var sugarToken: String? = null
    val parameters = coreSugarParameters.map { parameter ->
        parameter.import?.let(imports::add)
        parameter.sugarToken?.let { token ->
            check(sugarToken == null) { "sugarToken is already set" }
            sugarToken = token
        }
        parameter.toParameterSpec()
    }

    val sugarName = component.simpleName.toCoreComponentName(sugarToken)
    val funSpec = FunSpec
        .builder(sugarName)
        .addAnnotation(ClassName.bestGuess(ComposableFqn))
        .addModifiers(KModifier.PUBLIC)
        .addParameters(parameters)
        .apply {
            component.docString?.let { kdoc ->
                addKdoc(kdoc)
            }
        }
        .build()

    TODO("함수 본문 추가")

     return funSpec to imports
}

private fun List<ComponentAndCoreSugarParameters>.createSugarComponentFunctions(): Pair<List<FunSpec>, Imports> {
    val components = this
    val sugarSpecs = mutableListOf<FunSpec>()
    val sugarImports = mutableListOf<Import>()
    components.forEach { component ->
        val (funSpec, imports) = component.createSugarComponentFunction()
        sugarSpecs += funSpec
        sugarImports += imports
    }
    return sugarSpecs to sugarImports
}

private fun generateCoreKt(
    @Suppress("SameParameterValue") packageName: String,
    filename: String,
    components: List<ComponentAndCoreSugarParameters>,
): FileSpec {
    val (sugarSpecs, sugarImports) = components.createSugarComponentFunctions()
    return FileSpec
        .builder(
            packageName = packageName,
            fileName = filename,
        )
        .addGeneratedComment()
        .addSuppressAnnotation()
        .addImports(sugarImports)
        .addFunctions(sugarSpecs)
        .build()
}

internal fun generateCoreKts(components: Map<String, List<ComponentAndCoreSugarParameters>>): List<FileSpec> {
    val sugarFileSpecs = components.map { (filename, componentsPerFile) ->
        generateCoreKt(
            packageName = CoreNamespace,
            filename = filename,
            components = componentsPerFile,
        )
    }
    TODO("CodeGenerator 연결")
}
