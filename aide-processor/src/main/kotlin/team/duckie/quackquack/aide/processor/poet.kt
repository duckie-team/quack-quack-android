/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.aide.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.buildCodeBlock
import team.duckie.quackquack.util.backend.FormatterOffComment
import team.duckie.quackquack.util.backend.SuppressAnnotation
import team.duckie.quackquack.util.backend.generateBuildOrLocalFile
import team.duckie.quackquack.util.backend.getGeneratedComment
import team.duckie.quackquack.util.backend.requireContainingFile
import team.duckie.quackquack.util.backend.toLiteralListString

private val GeneratedComment = getGeneratedComment("aide-processor")

internal fun generateQuackComponents(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
    symbols: Sequence<KSFunctionDeclaration>,
    aidePath: String?,
) {
    val quackComponents = symbols.toDomainWithSimpleNameGroups()
    val quackComponentsFileSpec = createQuackComponentsFileSpec(quackComponents)

    generateBuildOrLocalFile(
        codeGenerator = codeGenerator,
        fileSpec = quackComponentsFileSpec,
        path = aidePath,
        logger = logger,
        tag = "aide",
    )
}

private fun createQuackComponentsFileSpec(groupedComponents: List<Pair<String, Set<String>>>): FileSpec {
    val quackComponents = PropertySpec
        .builder(
            name = "quackComponents",
            type = Map::class.asTypeName().parameterizedBy(
                String::class.asTypeName(),
                String::class.asTypeName(),
            ),
        )
        .addModifiers(KModifier.INTERNAL)
        .initializer(
            codeBlock = buildCodeBlock {
                beginControlFlow("run")
                addStatement(
                    "val aide = mutableMapOf<%T, %T>()",
                    String::class,
                    String::class,
                )
                groupedComponents.forEach { (domain, components) ->
                    addStatement("")
                    components.forEach { component ->
                        addStatement("aide[%S] = %S", component, domain)
                    }
                }
                addStatement("")
                addStatement("aide")
                endControlFlow()
            },
        )
        .build()

    return FileSpec
        .builder(
            packageName = "",
            fileName = "QuackComponents",
        )
        .addFileComment(GeneratedComment)
        .addFileComment(FormatterOffComment)
        .addAnnotation(SuppressAnnotation)
        .addProperty(quackComponents)
        .build()
}

internal fun generateAideModifiers(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
    symbols: Sequence<KSFunctionDeclaration>,
    aidePath: String?,
) {
    val aideModifiers = symbols.toDomainWithSimpleNameGroups()
    val aideModifiersFileSpec = createAideModifiersFileSpec(aideModifiers)

    generateBuildOrLocalFile(
        codeGenerator = codeGenerator,
        fileSpec = aideModifiersFileSpec,
        path = aidePath,
        logger = logger,
        tag = "aide",
    )
}

private fun createAideModifiersFileSpec(groupedModifiers: List<Pair<String, Set<String>>>): FileSpec {
    val aideModifiers = PropertySpec
        .builder(
            name = "aideModifiers",
            type = Map::class.asTypeName().parameterizedBy(
                String::class.asTypeName(),
                List::class.asTypeName().parameterizedBy(String::class.asTypeName()),
            ),
        )
        .addModifiers(KModifier.INTERNAL)
        .initializer(
            codeBlock = buildCodeBlock {
                beginControlFlow("run")
                addStatement(
                    "val aide = mutableMapOf<%T, %T>()",
                    String::class,
                    List::class.parameterizedBy(String::class),
                )
                groupedModifiers.forEach { (domain, modifiers) ->
                    addStatement("")
                    addStatement("aide[%S] = %L", domain, modifiers.toLiteralListString())
                    modifiers.forEach { modifier ->
                        addStatement("aide[%S] = %L()", "_$modifier", "emptyList")
                    }
                }
                addStatement("")
                addStatement("aide")
                endControlFlow()
            },
        )
        .build()

    return FileSpec
        .builder(
            packageName = "",
            fileName = "AideModifiers",
        )
        .addFileComment(GeneratedComment)
        .addFileComment(FormatterOffComment)
        .addAnnotation(SuppressAnnotation)
        .addProperty(aideModifiers)
        .build()
}

private fun Sequence<KSFunctionDeclaration>.toDomainWithSimpleNameGroups(): List<Pair<String, Set<String>>> {
    return this
        .groupBy { value ->
            val fileName = value.requireContainingFile.fileName
            fileName.removeSuffix(".kt")
        }
        .map { (domain, values) ->
            val valueSimpleNames = values.map { value ->
                value.simpleName.asString()
            }
            domain to valueSimpleNames.toSet()
        }
}
