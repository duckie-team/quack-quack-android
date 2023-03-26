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
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.engine.spec.tempdir
import ir.PoetError.sugarComponentButNoSugarRefer
import ir.SourceError.multipleSugarTokenIsNotAllowed
import ir.SourceError.quackComponentWithoutSugarToken
import ir.SourceError.sugarNamePrefixIsNotQuack
import ir.SourceError.sugarNameWithoutTokenName
import ir.SugarTransformError.sugarComponentAndSugarReferHasDifferentParameters
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class SugarIrErrorTest : ExpectSpec() {
    private val temporaryFolder = tempdir()
    private val sugarPath = temporaryFolder.path

    init {
        context("NotSupportedError") {
            expect("functionalType").config(enabled = false) {
                TODO()
            }
        }

        context("SourceError") {
            expect("quackComponentWithoutSugarToken") {
                val result = compile(
                    kotlin(
                        "quackComponentWithoutSugarToken.kt",
                        """
                        import androidx.compose.runtime.Composable
                
                        @Composable
                        fun QuackText() {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.COMPILATION_ERROR)
                expectThat(result.messages).contains(quackComponentWithoutSugarToken(null))
            }

            expect("multipleSugarTokenIsNotAllowed") {
                val result = compile(
                    kotlin(
                        "AwesomeType.kt",
                        """
                        @JvmInline
                        value class AwesomeType(val index: Int) {
                            companion object {
                                val One = AwesomeType(1)
                            }
                        }
                        """,
                    ),
                    kotlin(
                        "multipleSugarTokenIsNotAllowed.kt",
                        """
                        import team.duckie.quackquack.sugar.material.SugarToken
                        import androidx.compose.runtime.Composable
                
                        @Composable
                        fun QuackText(
                            @SugarToken style: AwesomeType,
                            @SugarToken style2: AwesomeType,
                        ) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(multipleSugarTokenIsNotAllowed(null))
            }

            expect("sugarNamePrefixIsNotQuack") {
                val result = compile(
                    kotlin(
                        "AwesomeType2.kt",
                        """
                        @JvmInline
                        value class AwesomeType2(val index: Int) {
                            companion object {
                                val One = AwesomeType2(1)
                            }
                        }
                        """,
                    ),
                    kotlin(
                        "sugarNamePrefixIsNotQuack.kt",
                        """
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarName
                        import team.duckie.quackquack.sugar.material.SugarToken
                
                        @SugarName("Text")
                        @Composable
                        fun QuackText(
                            @SugarToken type: AwesomeType2,
                        ) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarNamePrefixIsNotQuack(null))
            }

            expect("sugarNameWithoutTokenName") {
                val result = compile(
                    kotlin(
                        "AwesomeType3.kt",
                        """
                        @JvmInline
                        value class AwesomeType3(val index: Int) {
                            companion object {
                                val One = AwesomeType3(1)
                            }
                        }
                        """,
                    ),
                    kotlin(
                        "sugarNameWithoutTokenName.kt",
                        """
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarName
                        import team.duckie.quackquack.sugar.material.SugarToken
                
                        @SugarName("QuackText")
                        @Composable
                        fun QuackText(
                            @SugarToken type: AwesomeType3,
                        ) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarNameWithoutTokenName(null))
            }

            expect("sugarTokenButNoCompanionObject").config(enabled = false) {
                TODO()
            }
        }

        context("PoetError") {
            expect("sugarComponentButNoSugarRefer") {
                val result = compile(
                    kotlin(
                        "sugarComponentButNoSugarRefer.kt",
                        """
                        @file:GeneratedFile
        
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.GeneratedFile
        
                        @Composable
                        fun QuackOneText() {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarComponentButNoSugarRefer(null))
            }
        }

        context("SugarTransformError") {
            expect("sugarComponentAndSugarReferHasDifferentParameters") {
                val result = compile(
                    kotlin(
                        "AwesomeType4.kt",
                        """
                        @JvmInline
                        value class AwesomeType4(val index: Int) {
                            companion object {
                                val One = AwesomeType4(1)
                            }
                        }
                        """,
                    ),
                    kotlin(
                        "sugarComponentAndSugarReferHasDifferentParameters.kt",
                        """
                        import team.duckie.quackquack.sugar.material.SugarToken
                        import androidx.compose.runtime.Composable
                
                        @Composable
                        fun QuackText(@SugarToken style: AwesomeType4) {}
                        """,
                    ),
                    kotlin(
                        "sugarComponentAndSugarReferHasDifferentParameters2.kt",
                        """
                        @file:GeneratedFile
        
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.GeneratedFile
                        import team.duckie.quackquack.sugar.material.SugarRefer
                        import team.duckie.quackquack.sugar.material.sugar
        
                        @Composable
                        @SugarRefer("QuackText")
                        fun QuackOneText(newNumber: Int = sugar()) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarComponentAndSugarReferHasDifferentParameters(null, null))
            }
        }
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
