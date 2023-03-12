/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import common.Empty
import common.addGeneratedComment
import common.addSuppressAnnotation
import common.requireContainingFile
import java.io.File

private typealias Type = String
private typealias TypedValues = List<Pair<Type, Set<String>>>

private fun createAideFileSpec(
    propertyName: String,
    typedValues: TypedValues,
    modifiers: Boolean = false,
): FileSpec {
    val aideMap = PropertySpec
        .builder(
            name = propertyName,
            type = Map::class.asTypeName().parameterizedBy(
                Type::class.asTypeName(),
                List::class.asTypeName().parameterizedBy(String::class.asTypeName()),
            ),
        )
        .addModifiers(KModifier.INTERNAL)
        .initializer(
            CodeBlock.Builder()
                .beginControlFlow("run")
                .addStatement(
                    "val aide = mutableMapOf<%T, List<%T>>()",
                    String::class,
                    String::class,
                )
                .apply {
                    typedValues.forEach { (type, values) ->
                        if (modifiers) addStatement("")
                        addStatement("aide[%S] = %L", type, values.toLiteralListString())
                        if (modifiers) {
                            values.forEach { modifier ->
                                addStatement("aide[%S] = %L", modifier, "emptyList()")
                            }
                        }
                    }
                    if (modifiers) addStatement("")
                }
                .addStatement("aide")
                .endControlFlow()
                .build(),
        )
        .build()

    return FileSpec
        .builder(
            packageName = "",
            fileName = propertyName.replaceFirstChar(Char::titlecase),
        )
        .addGeneratedComment()
        .addSuppressAnnotation()
        .addProperty(aideMap)
        .build()
}

internal fun generateAideKt(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
    symbols: Sequence<KSFunctionDeclaration>,
    property: String,
    aidePath: String?,
    modifiers: Boolean = false,
) {
    val typedValues = mutableListOf<Pair<Type, Set<String>>>()

    symbols
        .groupBy { functionDeclaration ->
            val valueType = functionDeclaration.requireContainingFile.fileName
            valueType.removeSuffix(".kt")
        }
        .forEach { (type, values) ->
            val valueNames = values.map { functionDeclaration ->
                functionDeclaration.simpleName.asString()
            }
            typedValues.add(type to valueNames.toSet())
            logger.warn("$type to ${valueNames.toLiteralListString()}")
        }

    val aideMapKtSpec = createAideFileSpec(
        propertyName = property,
        typedValues = typedValues,
        modifiers = modifiers,
    )

    val generatedPath: String
    if (typedValues.isNotEmpty()) {
        if (aidePath == null) {
            val aideMapKt = codeGenerator.createNewFile(
                dependencies = Dependencies.Empty,
                packageName = aideMapKtSpec.packageName,
                fileName = aideMapKtSpec.name,
            )
            aideMapKt.writer().use(aideMapKtSpec::writeTo)
            generatedPath = aideMapKtSpec.packageName + "/" + aideMapKtSpec.name + ".kt"
        } else {
            val aideMapKt = File(aidePath, "${aideMapKtSpec.name}.kt").also { file ->
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
            }
            aideMapKt.writeText(aideMapKtSpec.toString())
            generatedPath = aideMapKt.path
        }
        logger.warn("generated at $generatedPath")
    }
}
