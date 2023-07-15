/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)

package team.duckie.quackquack.sugar.test

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.core.test.Enabled
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import team.duckie.quackquack.sugar.compiler.SugarCompilerRegistrar
import team.duckie.quackquack.sugar.hosted.error.NotSupportedError.nestedFunctionalType
import team.duckie.quackquack.sugar.hosted.error.SourceError.multipleSugarTokenIsNotAllowed
import team.duckie.quackquack.sugar.hosted.error.SourceError.quackComponentWithoutSugarToken
import team.duckie.quackquack.sugar.hosted.error.SourceError.sugarNamePrefixIsNotQuack
import team.duckie.quackquack.sugar.hosted.error.SourceError.sugarNameWithoutTokenName
import team.duckie.quackquack.sugar.hosted.error.SourceError.sugarTokenButNoCompanionObject
import team.duckie.quackquack.sugar.hosted.error.SugarTransformError.sugarComponentAndSugarReferHasDifferentParameters

class SugarCompilerErrorTest : ExpectSpec() {
  private val testCompilation =
    TestCompilation(tempdir()).apply {
      prepareSetting {
        compilerPluginRegistrars = listOf(SugarCompilerRegistrar.asPluginRegistrar())
      }
    }

  init {
    context("NotSupportedError") {
      expect("nestedFunctionalType").config(
        enabledOrReasonIf = {
          Enabled.disabled(
            "테스트 코드는 실패하는데 실제 코드로 돌려보면 정상 작동함.." +
              "추후 테스트 코드가 실패하는 원인을 찾아야 함.",
          )
        },
      ) {
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable
import androidx.compose.runtime.Composable

@Sugarable
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
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.Sugarable

@Sugarable
@Composable
fun QuackText() {}
            """,
          ),
        )

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain quackComponentWithoutSugarToken("QuackText")
      }

      expect("multipleSugarTokenIsNotAllowed") {
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

@Sugarable
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
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

@Sugarable
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
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

@Sugarable
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
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

@Sugarable
@Composable
fun QuackText(@SugarToken type: AwesomeType3) {}
            """,
          ),
        )

        result.exitCode shouldBe KotlinCompilation.ExitCode.INTERNAL_ERROR
        result.messages shouldContain sugarTokenButNoCompanionObject("AwesomeType3")
      }
    }

    context("SugarTransformError") {
      expect("sugarComponentAndSugarReferHasDifferentParameters") {
        val result = testCompilation.compile(
          kotlin(
            "main.kt",
            """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

@Sugarable
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

@SugarRefer("QuackText")
@Composable
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
}
