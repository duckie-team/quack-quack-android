/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class, UnsafeCastFunction::class)
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
import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction
import org.jetbrains.kotlin.utils.addToStdlib.cast

class SugarIrTransformTest : StringSpec() {
    private val temporaryFolder = tempdir()

    init {
        "Default Argument에 SugarIrTransform이 정상 작동함" {
            val result = compile(
                kotlin(
                    "main.kt",
                    """
                    import team.duckie.quackquack.sugar.material.SugarToken
                    import androidx.compose.runtime.Composable
    
                    var number = 0
            
                    @Composable
                    fun QuackText(
                        @SugarToken style: AwesomeType? = null,
                        newNumber: Int = Int.MAX_VALUE,
                    ) {
                        number = newNumber
                    }
                    """,
                ),
                kotlin(
                    "text-sugar.kt",
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
                    fun QuackOneText(newNumber: Int = sugar()) {
                        QuackText(
                            style = AwesomeType.One,
                            newNumber = newNumber,
                        )
                    }
                    """,
                ),
            )

            result.exitCode shouldBe KotlinCompilation.ExitCode.OK

            val sugarClass = result.classLoader.loadClass("Text_sugarKt")
            val quackTextMethod = sugarClass.getMethod(
                "QuackOneText\$default",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                java.lang.Object::class.java,
            )
            quackTextMethod.invoke(sugarClass, 0, 1, null)

            val mainClass = result.classLoader.loadClass("MainKt")
            val getNumberMethod = mainClass.getMethod("getNumber")

            getNumberMethod.invoke(mainClass).cast<Int>() shouldBe Int.MAX_VALUE
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
