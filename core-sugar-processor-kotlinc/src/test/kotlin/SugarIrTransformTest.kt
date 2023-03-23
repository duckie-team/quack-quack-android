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
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.utils.addToStdlib.cast
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SugarIrTransformTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    private val sugarPath by lazy { temporaryFolder.root.path }

    @Test
    fun defaultValue_transform() {
        val result = compile(
            kotlin(
                "TextStyle.kt",
                """
                class TextStyle(val index: Int) {
                    companion object {
                        val One = TextStyle(1)
                    }
                }
                """,
            ),
            kotlin(
                "text.kt",
                """
                import team.duckie.quackquack.sugar.material.SugarToken
                import androidx.compose.runtime.Composable

                var number = 0
        
                @Composable
                fun QuackText(
                    @SugarToken style: TextStyle? = null,
                    newNumber: Int = Int.MAX_VALUE,
                ) {
                    number = newNumber
                }
                """,
            ),
            kotlin(
                "text-sugar.kt",
                """
                @file:GeneratedFile

                import androidx.compose.runtime.Composable
                import team.duckie.quackquack.sugar.material.GeneratedFile
                import team.duckie.quackquack.sugar.material.SugarRefer
                import team.duckie.quackquack.sugar.material.sugar

                @Composable
                @SugarRefer("QuackText")
                fun QuackOneText(newNumber: Int = sugar()) {
                    QuackText(
                        style = TextStyle.One,
                        newNumber = newNumber,
                    )
                }
                """,
            ),
        )

        expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)

        val sugarClass = result.classLoader.loadClass("Text_sugarKt")
        val quackTextMethod = sugarClass.getMethod(
            "QuackOneText\$default",
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            java.lang.Object::class.java,
        )
        quackTextMethod.invoke(sugarClass, 0, 1, null)

        val mainClass = result.classLoader.loadClass("TextKt")
        val getNumberMethod = mainClass.getMethod("getNumber")

        expectThat(getNumberMethod.invoke(mainClass).cast<Int>()).isEqualTo(Int.MAX_VALUE)
    }

    private fun compile(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return prepareCompilation(*sourceFiles).compile()
    }

    private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            workingDir = temporaryFolder.root
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
