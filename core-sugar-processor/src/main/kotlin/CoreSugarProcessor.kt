/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import common.Names.ComposableFqn
import common.isPublicQuackComponent
import common.requireContainingFile

internal class CoreSugarProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val sugarPath: String?,
) {
    fun resolve(resolver: Resolver): List<KSAnnotated> {
        val sugarComponents = resolver
            .getSymbolsWithAnnotation(ComposableFqn)
            .mapNotNull { declaration ->
                if (
                    declaration !is KSFunctionDeclaration ||
                    !declaration.isPublicQuackComponent
                ) {
                    return@mapNotNull null
                }
                val typeParameterResolver = declaration.typeParameters.toTypeParameterResolver()
                val coreSugarParameters = declaration.parameters.map { parameter ->
                    parameter.asCoreSugarParameter(typeParameterResolver)
                }
                declaration to coreSugarParameters
            }
        val fileGroupedSugarComponents = sugarComponents.groupBy { (component, _) ->
            component.requireContainingFile.fileName
        }

        generateSugarKts(
            components = fileGroupedSugarComponents,
            codeGenerator = codeGenerator,
            logger = logger,
            sugarPath = sugarPath,
        )

        return sugarComponents.mapNotNull { (component, _) ->
            component.takeUnless(KSFunctionDeclaration::validate)
        }.toList()
    }
}
