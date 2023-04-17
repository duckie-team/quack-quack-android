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
import ir.NotSupportedError.functionalType
import ir.PoetError.sugarComponentButNoSugarRefer
import ir.SourceError.multipleSugarTokenIsNotAllowed
import ir.SourceError.quackComponentWithoutSugarToken
import ir.SourceError.sugarNamePrefixIsNotQuack
import ir.SourceError.sugarNameWithoutTokenName
import ir.SourceError.sugarTokenButNoCompanionObject
import ir.SugarTransformError.sugarComponentAndSugarReferHasDifferentParameters
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class SugarIrErrorTest : ExpectSpec() {
    private val temporaryFolder = tempdir()

    init {
        context("NotSupportedError") {
            expect("functionalType") {
                val result = compile(
                    kotlin(
                        "main.kt",
                        """
                        import team.duckie.quackquack.sugar.material.SugarToken
                        import androidx.compose.runtime.Composable

                        @Composable
                        fun QuackText(
                            @SugarToken style: AwesomeType,
                            function: (unit: Unit, unit2: Unit) -> Unit,
                        ) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(functionalType(null))
            }
        }

        context("SourceError") {
            expect("quackComponentWithoutSugarToken") {
                val result = compile(
                    kotlin(
                        "main.kt",
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

            expect("quackComponentWithoutSugarToken - @NoSugar applied") {
                val result = compile(
                    kotlin(
                        "main.kt",
                        """
                        import team.duckie.quackquack.sugar.material.NoSugar
                        import androidx.compose.runtime.Composable

                        @NoSugar
                        @Composable
                        fun QuackText() {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
            }

            expect("multipleSugarTokenIsNotAllowed") {
                val result = compile(
                    kotlin(
                        "main.kt",
                        """
                        import team.duckie.quackquack.sugar.material.SugarToken
                        import androidx.compose.runtime.Composable

                        @Composable
                        fun QuackText(
                            @SugarToken style: AwesomeType,
                            @SugarToken style2: AwesomeType2,
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
                        "main.kt",
                        """
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarName
                        import team.duckie.quackquack.sugar.material.SugarToken

                        @SugarName("Text")
                        @Composable
                        fun QuackText(@SugarToken type: AwesomeType) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarNamePrefixIsNotQuack(null))
            }

            expect("sugarNameWithoutTokenName") {
                val result = compile(
                    kotlin(
                        "main.kt",
                        """
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarName
                        import team.duckie.quackquack.sugar.material.SugarToken

                        @SugarName("QuackText")
                        @Composable
                        fun QuackText(@SugarToken type: AwesomeType) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarNameWithoutTokenName(null))
            }

            expect("sugarTokenButNoCompanionObject") {
                val result = compile(
                    kotlin(
                        "main.kt",
                        """
                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarToken

                        @Composable
                        fun QuackText(@SugarToken type: AwesomeType3) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(sugarTokenButNoCompanionObject(null))
            }
        }

        context("PoetError") {
            expect("sugarComponentButNoSugarRefer") {
                val result = compile(
                    kotlin(
                        "main.kt",
                        """
                        @file:OptIn(SugarCompilerApi::class)
                        @file:SugarGeneratedFile

                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarCompilerApi
                        import team.duckie.quackquack.sugar.material.SugarGeneratedFile

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
                        "main.kt",
                        """
                        import team.duckie.quackquack.sugar.material.SugarToken
                        import androidx.compose.runtime.Composable

                        @Composable
                        fun QuackText(@SugarToken style: AwesomeType) {}
                        """,
                    ),
                    kotlin(
                        "main-generated.kt",
                        """
                        @file:OptIn(SugarCompilerApi::class)
                        @file:SugarGeneratedFile

                        import androidx.compose.runtime.Composable
                        import team.duckie.quackquack.sugar.material.SugarCompilerApi
                        import team.duckie.quackquack.sugar.material.SugarGeneratedFile
                        import team.duckie.quackquack.sugar.material.SugarRefer
                        import team.duckie.quackquack.sugar.material.sugar

                        @Composable
                        @SugarRefer("QuackText")
                        fun QuackOneText(newNumber: Int = sugar()) {}
                        """,
                    ),
                )

                expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
                expectThat(result.messages).contains(
                    sugarComponentAndSugarReferHasDifferentParameters(
                        sugarIrData = null,
                        parameter = null,
                    ),
                )
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
                    optionValue = temporaryFolder.path,
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
