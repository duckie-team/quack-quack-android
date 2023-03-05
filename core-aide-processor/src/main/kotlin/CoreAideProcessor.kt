/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate
import java.io.File

private const val QuackComponentPrefix = "Quack"
private const val ModifierSn = "Modifier"
private const val UnitSn = "Unit"

private const val TypedModifierFqn = "team.duckie.quackquack.aide.annotation.TypedModifier"
private const val ComposableFqn = "androidx.compose.runtime.Composable"

internal typealias Type = String
internal typealias TypedValues = List<Pair<Type, Set<String>>>

class QuackCoreAideProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val aidePath: String?,
) {
    fun resolve(resolver: Resolver): List<KSAnnotated> {
        @Suppress("UNCHECKED_CAST")
        val components = resolver
            .getSymbolsWithAnnotation(ComposableFqn)
            .filter { functionDeclaration ->
                functionDeclaration is KSFunctionDeclaration &&
                        // 1. 공개 함수이고,
                        functionDeclaration.modifiers.contains(Modifier.PUBLIC) &&
                        // 2. 함수명이 Quack으로 시작하며,
                        functionDeclaration.simpleName.asString().startsWith(QuackComponentPrefix) &&
                        // 3. 확장 함수가 아니고,
                        functionDeclaration.extensionReceiver == null &&
                        // 4. 반환 타입이 없어야 함
                        functionDeclaration.returnType.toString() == UnitSn
            } as Sequence<KSFunctionDeclaration>

        @Suppress("UNCHECKED_CAST")
        val modifiers = resolver
            .getSymbolsWithAnnotation(TypedModifierFqn)
            .filter { functionDeclaration ->
                functionDeclaration is KSFunctionDeclaration &&
                        // 1. 공개 함수이고,
                        functionDeclaration.modifiers.contains(Modifier.PUBLIC) &&
                        // 2. Modifier의 확장 함수이며,
                        functionDeclaration.extensionReceiver.toString() == ModifierSn &&
                        // 3. Modifier를 반환해야 함
                        functionDeclaration.returnType.toString() == ModifierSn
            } as Sequence<KSFunctionDeclaration>

        generateAideKt(
            codeGenerator = codeGenerator,
            logger = logger,
            symbols = components,
            property = "aideComponents",
            aidePath = aidePath,
        )
        generateAideKt(
            codeGenerator = codeGenerator,
            logger = logger,
            symbols = modifiers,
            property = "aideModifiers",
            aidePath = aidePath,
            modifiers = true,
        )

        return components.filterNot(KSAnnotated::validate)
            .plus(modifiers.filterNot(KSAnnotated::validate))
            .toList()
    }
}

private fun generateAideKt(
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

    val aideMapKtSpec = createAideMapKtSpec(
        propertyName = property,
        typedValues = typedValues,
        modifiers = modifiers,
    )

    if (typedValues.isNotEmpty()) {
        if (aidePath == null) {
            val aideMapKt = codeGenerator.createNewFile(
                dependencies = Dependencies.Empty,
                packageName = aideMapKtSpec.packageName,
                fileName = aideMapKtSpec.name,
            )
            aideMapKt.writer().use(aideMapKtSpec::writeTo)
        } else {
            val aideMapKt = File(aidePath, "${aideMapKtSpec.name}.kt").also { file ->
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
            }
            aideMapKt.writeText(aideMapKtSpec.toString())
            logger.warn("generated at ${aideMapKt.path}")
        }
    }
}
