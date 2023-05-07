/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate

internal class CasaProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
  private val casaPath: String?,
) {
  fun resolve(resolver: Resolver): List<KSAnnotated> {
    @Suppress("UNCHECKED_CAST")
    val casas = resolver
      .getSymbolsWithAnnotation(CasaFqn) as Sequence<KSFunctionDeclaration>

    generateCasaModels(
      codeGenerator = codeGenerator,
      logger = logger,
      casas = casas,
      casaPath = casaPath,
    )

    return casas.filterNot(KSAnnotated::validate).toList()
  }
}
