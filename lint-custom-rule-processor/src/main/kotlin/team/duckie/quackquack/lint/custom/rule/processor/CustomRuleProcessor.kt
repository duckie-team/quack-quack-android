/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [CustomLintProcessor.kt] created by Ji Sungbin on 22. 8. 29. 오전 2:47
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.custom.rule.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.File
import java.io.IOException
import team.duckie.quackquack.lint.custom.rule.annotation.NewCollection
import team.duckie.quackquack.lint.custom.rule.annotation.NewImmutableCollection
import team.duckie.quackquack.lint.custom.rule.annotation.NewModifier

private fun createCustomRuleFile(
    ruleName: String,
    allowList: Set<String>,
    logger: KSPLogger,
    rootPath: String,
) {
    logger.warn("Creating custom rule file for $ruleName")
    val fileName = "Custom_$ruleName.txt"
    val allowListByString = allowList.joinToString("\n")
    val file = File("$rootPath/quack-lint-custom-rule", fileName).also { file ->
        if (!file.exists()) {
            file.parentFile.mkdirs()
        }
    }
    try {
        file.writeText(allowListByString)
        file.createNewFile()
    } catch (exception: IOException) {
        logger.error("Failed to create custom rule file for $ruleName. exception message: ${exception.message}")
    } finally {
        logger.warn("Finished creating custom rule file for $ruleName at ${file.absolutePath}")
    }
}

class CustomRuleProcessor(
    private val logger: KSPLogger,
    private val options: Map<String, String>,
) : SymbolProcessor {
    private val newModifiers = mutableSetOf<String>()
    private val newCollections = mutableSetOf<String>()
    private val newImmutableCollections = mutableSetOf<String>()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        collectCustomRules(
            resolver = resolver,
            ruleAnnotation = NewModifier::class.java,
            allowList = newModifiers
        )
        collectCustomRules(
            resolver = resolver,
            ruleAnnotation = NewCollection::class.java,
            allowList = newCollections
        )
        collectCustomRules(
            resolver = resolver,
            ruleAnnotation = NewImmutableCollection::class.java,
            allowList = newImmutableCollections
        )
        return emptyList()
    }

    override fun finish() {
        super.finish()
        listOf(
            "Modifier" to newModifiers,
            "Collection" to newCollections,
            "ImmutableCollection" to newImmutableCollections,
        ).forEach { (ruleName, allowList) ->
            createCustomRuleFile(
                ruleName = ruleName,
                allowList = allowList,
                logger = logger,
                rootPath = checkNotNull(options["path"]) { "project path is not defined" }
            )
        }
    }

    override fun onError() {
        logger.error("Error on SymbolProcessor processing")
    }

    private fun collectCustomRules(
        resolver: Resolver,
        ruleAnnotation: Class<*>,
        allowList: MutableSet<String>,
    ) {
        val symbols = resolver.getSymbolsWithAnnotation(ruleAnnotation.canonicalName)
        logger.warn("found new ${ruleAnnotation.simpleName}: ${symbols.joinToString()}")
        symbols
            .filterIsInstance<KSClassDeclaration>()
            .forEach { classDeclaration ->
                val name = classDeclaration.simpleName.asString()
                allowList.add(name)
            }
    }
}
