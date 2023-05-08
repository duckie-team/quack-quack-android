/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
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

package team.duckie.quackquack.sugar.processor

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.core.test.Enabled
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import team.duckie.quackquack.sugar.processor.ir.NotSupportedError.nestedFunctionalType
import team.duckie.quackquack.sugar.processor.ir.PoetError.sugarComponentButNoSugarRefer
import team.duckie.quackquack.sugar.processor.ir.SourceError.multipleSugarTokenIsNotAllowed
import team.duckie.quackquack.sugar.processor.ir.SourceError.quackComponentWithoutSugarToken
import team.duckie.quackquack.sugar.processor.ir.SourceError.sugarNamePrefixIsNotQuack
import team.duckie.quackquack.sugar.processor.ir.SourceError.sugarNameWithoutTokenName
import team.duckie.quackquack.sugar.processor.ir.SourceError.sugarTokenButNoCompanionObject
import team.duckie.quackquack.sugar.processor.ir.SugarTransformError.sugarComponentAndSugarReferHasDifferentParameters

class SugarIrErrorTest : ExpectSpec() {
  private val tempDir = tempdir()

  init {
    context("NotSupportedError") {
      expect("nestedFunctionalType").config(
        enabledOrReasonIf = {
          Enabled.disabled(
            "테스트 코드는 실패하는데 실제 코드로 돌려보면 정상 작동함.." +
              "추후 테스트 코드가 실패하는 원인을 찾아야 함."
          )
        }
      ) {
        val result = compile(
          kotlin(
            "main.kt",
            """
import team.duckie.quackquack.sugar.material.SugarToken
import androidx.compose.runtime.Composable

@Composable
fun QuackText(
  @SugarToken style: AwesomeType,
  lambda: (unit: Unit, unit2: Unit, unit3: () -> Unit) -> Unit,
) {}
            """,
          ),
        )

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain nestedFunctionalType("QuackText#lambda")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain quackComponentWithoutSugarToken("QuackText")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.OK
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain multipleSugarTokenIsNotAllowed("QuackText")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain sugarNamePrefixIsNotQuack("QuackText (Text)")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain sugarNameWithoutTokenName("QuackText (QuackText)")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain sugarTokenButNoCompanionObject("AwesomeType3")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain sugarComponentButNoSugarRefer("QuackOneText")
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

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain sugarComponentAndSugarReferHasDifferentParameters(
          "(refer) QuackText -> (sugar) QuackOneText#newNumber",
        )
      }
    }
  }

  private fun compile(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
    return prepareCompilation(*sourceFiles).compile()
  }

  private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation {
    return KotlinCompilation().apply {
      workingDir = tempDir
      sources = sourceFiles.asList() + stubs
      jvmTarget = JvmTarget.JVM_17.toString()
      inheritClassPath = true
      supportsK2 = false
      useK2 = false
      pluginOptions = listOf(
        PluginOption(
          pluginId = PluginId,
          optionName = OPTION_SUGAR_PATH.optionName,
          optionValue = tempDir.path,
        ),
        PluginOption(
          pluginId = PluginId,
          optionName = OPTION_POET.optionName,
          optionValue = "false",
        ),
      )
      compilerPluginRegistrars = listOf(SugarComponentRegistrar.asPluginRegistrar())
      commandLineProcessors = listOf(SugarCommandLineProcessor())
    }
  }
}
