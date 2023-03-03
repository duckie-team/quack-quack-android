/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

@AutoService(value = [SymbolProcessorProvider::class])
class QuackCoreAideSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return QuackCoreAideSymbolProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}

private class QuackCoreAideSymbolProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : SymbolProcessor {
    private val processor = QuackCoreAideProcessor(
        codeGenerator = codeGenerator,
        logger = logger,
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return processor.resolve(resolver)
    }
}
