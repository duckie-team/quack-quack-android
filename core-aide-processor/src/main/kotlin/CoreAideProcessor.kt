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

private const val QuackComponentPrefix = "Quack"

private const val TypedModifierFqn = "team.duckie.quackquack.aide.annotation.TypedModifier"
private const val ComposableFqn = "androidx.compose.runtime.Composable"

internal typealias Type = String
internal typealias TypedValues = List<Pair<Type, Set<String>>>

class QuackCoreAideProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) {
    fun resolve(resolver: Resolver): List<KSAnnotated> {
        val components = resolver
            .getSymbolsWithAnnotation(ComposableFqn)
            .filterIsInstance<KSFunctionDeclaration>()
            .filter { functionDeclaration ->
                functionDeclaration.simpleName.asString().startsWith(QuackComponentPrefix) &&
                        functionDeclaration.modifiers.contains(Modifier.PUBLIC)
            }

        val modifiers = resolver
            .getSymbolsWithAnnotation(TypedModifierFqn)
            .filterIsInstance<KSFunctionDeclaration>()
            .filter { functionDeclaration ->
                functionDeclaration.returnType != null &&
                        functionDeclaration.extensionReceiver != null &&
                        functionDeclaration.modifiers.contains(Modifier.PUBLIC)
            }

        generateAideKt(
            codeGenerator = codeGenerator,
            logger = logger,
            symbols = components,
            property = "aideComponents",
        )
        generateAideKt(
            codeGenerator = codeGenerator,
            logger = logger,
            symbols = modifiers,
            property = "aideModifiers",
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

    if (typedValues.isNotEmpty()) {
        val aideMapKtSpec = createAideMapKtSpec(
            propertyName = property,
            typedValues = typedValues,
        )
        val aideMapKt = codeGenerator.createNewFile(
            dependencies = Dependencies.Empty,
            packageName = aideMapKtSpec.packageName,
            fileName = aideMapKtSpec.name,
        )
        aideMapKt.writer().use(aideMapKtSpec::writeTo)
    }
}
