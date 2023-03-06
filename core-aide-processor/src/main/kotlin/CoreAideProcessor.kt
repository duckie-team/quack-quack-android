/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import Names.ComposableFqn
import Names.TypedModifierFqn
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate

internal class CoreAideProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val aidePath: String?,
) {
    fun resolve(resolver: Resolver): List<KSAnnotated> {
        @Suppress("UNCHECKED_CAST")
        val components = resolver
            .getSymbolsWithAnnotation(ComposableFqn)
            .filter { declaration ->
                declaration is KSFunctionDeclaration && declaration.isPublicQuackComponent
            } as Sequence<KSFunctionDeclaration>

        @Suppress("UNCHECKED_CAST")
        val modifiers = resolver
            .getSymbolsWithAnnotation(TypedModifierFqn)
            .filter { declaration ->
                declaration is KSFunctionDeclaration && declaration.isPublicModifier
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
