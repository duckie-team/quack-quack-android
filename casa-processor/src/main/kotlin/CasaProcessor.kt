/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(KspExperimental::class)

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import kotlin.reflect.full.createInstance
import team.duckie.quackquack.casa.material.CasaValues

internal class CasaProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val casaPath: String?,
) {
    fun resolve(resolver: Resolver): List<KSAnnotated> {
        val casaValues = resolver
            .getSymbolsWithAnnotation(CasaValuesFqn)
            .singleWithTransform { declaration ->
                declaration as KSClassDeclaration
                val casaValues = declaration.getAnnotationsByType(CasaValues::class).single()
                val values = buildMap<String, Any>(capacity = casaValues.providers.size) {
                    casaValues.providers.forEach { provider ->
                        provider.createInstance().value.let { value ->
                            value!!::class.simpleName!! to value
                        }
                    }
                }
                true to values
            }

        @Suppress("UNCHECKED_CAST")
        val casas = resolver
            .getSymbolsWithAnnotation(CasaFqn) as Sequence<KSFunctionDeclaration>

        generateCasaModels(
            codeGenerator = codeGenerator,
            logger = logger,
            casas = casas,
            casaValues = casaValues,
            casaPath = casaPath,
        )

        return casas.filterNot(KSAnnotated::validate).toList()
    }
}
