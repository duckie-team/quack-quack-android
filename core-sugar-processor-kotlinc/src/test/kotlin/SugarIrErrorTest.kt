/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)
@file:Suppress(
    "RedundantUnitReturnType",
    "RedundantVisibilityModifier",
    "RedundantUnitExpression",
    "RedundantSuppression",
    "LongMethod",
    "HasPlatformType",
    "KDocUnresolvedReference",
)

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.engine.spec.tempdir
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget

@Ignored
class SugarIrErrorTest : ExpectSpec() {
    private val temporaryFolder = tempdir()
    private val sugarPath by lazy { temporaryFolder.path }

    init {
        TODO()
    }

    private fun compile(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return prepareCompilation(*sourceFiles).compile()
    }

    private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            workingDir = temporaryFolder
            sources = sourceFiles.asList() + stubs
            jvmTarget = JvmTarget.JVM_17.toString()
            supportsK2 = false
            pluginOptions = listOf(
                PluginOption(
                    pluginId = PluginId,
                    optionName = OPTION_SUGAR_PATH.optionName,
                    optionValue = sugarPath,
                ),
                PluginOption(
                    pluginId = PluginId,
                    optionName = OPTION_POET.optionName,
                    optionValue = "true",
                ),
            )
            verbose = false
            inheritClassPath = true
            compilerPluginRegistrars = listOf(SugarComponentRegistrar.asPluginRegistrar())
            commandLineProcessors = listOf(SugarCommandLineProcessor())
            useK2 = false
        }
    }
}
