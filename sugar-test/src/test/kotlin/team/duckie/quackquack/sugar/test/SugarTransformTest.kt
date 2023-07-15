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
import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.utils.addToStdlib.cast
import team.duckie.quackquack.sugar.compiler.SugarCompilerRegistrar
import team.duckie.quackquack.sugar.test.utils.TestCompilation

class SugarTransformTest : StringSpec() {
  private val testCompilation =
    TestCompilation(tempdir()).apply {
      prepareSetting {
        compilerPluginRegistrars = listOf(SugarCompilerRegistrar.asPluginRegistrar())
      }
    }

  init {
    "Default Argument에 SugarIrTransform이 정상 작동함" {
      val result = testCompilation.compile(
        kotlin(
          "main.kt",
          """
import androidx.compose.runtime.Composable
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

var number = 0

@Sugarable
@Composable
fun QuackText(
  @SugarToken style: AwesomeType,
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
}
