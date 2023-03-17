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
)

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@Ignore("미완성")
@RunWith(Parameterized::class)
class SugarProcessorKotlincTest(private val useK2: Boolean) {
    @get:Rule
    val temporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "useK2 = {0}")
        fun data() = listOf(true, false)
    }

    @Test
    fun irTransformation() {
        val compilation = prepareCompilation(
            kotlin(
                "text.kt",
                """
                import androidx.compose.runtime.Composable
                import androidx.compose.ui.Modifier
    
                interface Hi
                class HiImpl : Hi
    
                var valueText: String? = null
                var valueNumber: Int? = null
                var valueHi: Hi? = null
                var valueList: List<Int>? = null

                @Composable
                fun Text(
                    text: String? = "HI~~~",
                    number: Int? = 1_231,
                    hi: Hi? = HiImpl(),
                    list: List<Int> = setOf(1, 2, 3).toList(),
                ) {
                    test = number
                }
                """,
            ),
        )
        val result = compilation.compile()

        expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
    }

    private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            workingDir = temporaryFolder.root
            sources = sourceFiles.asList() + stubs
            jvmTarget = JvmTarget.JVM_17.toString()
            supportsK2 = true
            pluginOptions = listOf(
                PluginOption(
                    pluginId = PluginId,
                    optionName = OPTION_SUGAR_PATH.optionName,
                    optionValue = workingDir.path,
                ),
            )
            inheritClassPath = true
            compilerPluginRegistrars = listOf(SugarComponentRegistrar())
            commandLineProcessors = listOf(SugarCommandLineProcessor())
            useK2 = this@SugarProcessorKotlincTest.useK2
        }
    }
}
