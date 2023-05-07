/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

private const val CasaPathArg = "CasaPath"

@Suppress("unused")
@AutoService(SymbolProcessorProvider::class)
class CasaProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return CasaSymbolProcessor(
      codeGenerator = environment.codeGenerator,
      logger = environment.logger,
      options = environment.options,
    )
  }
}

private class CasaSymbolProcessor(
  codeGenerator: CodeGenerator,
  logger: KSPLogger,
  options: Map<String, Any>,
) : SymbolProcessor {
  private val processor = CasaProcessor(
    codeGenerator = codeGenerator,
    logger = logger,
    casaPath = options[CasaPathArg]?.toString(),
  )

  override fun process(resolver: Resolver): List<KSAnnotated> {
    return processor.resolve(resolver)
  }
}
