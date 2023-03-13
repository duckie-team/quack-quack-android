/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

private const val SugarPathArg = "SugarPath"

@AutoService(SymbolProcessorProvider::class)
class CoreSugarProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return CoreSugarSymbolProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
            options = environment.options,
        )
    }
}

private class CoreSugarSymbolProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
    options: Map<String, Any>,
) : SymbolProcessor {
    private val processor = CoreSugarProcessor(
        codeGenerator = codeGenerator,
        logger = logger,
        sugarPath = options[SugarPathArg]?.toString(),
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return processor.resolve(resolver)
    }
}
