/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)
@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.quackquack.sugar.test

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import java.io.File
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import team.duckie.quackquack.sugar.compiler.SugarCompilerRegistrar
import team.duckie.quackquack.util.backend.test.findGeneratedFileOrNull

class CompilerTestCompilation(private val tempDir: File) {
  private var defaultPrepareSetting: (KotlinCompilation.(tempDir: File) -> Unit)? = null

  fun compile(vararg sourceFiles: SourceFile) =
    prepare(*sourceFiles).compile()

  fun prepare(vararg sourceFiles: SourceFile) =
    KotlinCompilation().apply {
      workingDir = tempDir
      sources = sourceFiles.asList() + stubs
      jvmTarget = JvmTarget.JVM_17.toString()
      inheritClassPath = true
      supportsK2 = false
      useK2 = false
      compilerPluginRegistrars = listOf(SugarCompilerRegistrar.asPluginRegistrar())
      defaultPrepareSetting?.invoke(this, tempDir)
    }

  fun defaultPrepareSetting(block: KotlinCompilation.(tempDir: File) -> Unit) {
    defaultPrepareSetting = block
  }

  fun findGeneratedFileOrNull(fileName: String) = tempDir.findGeneratedFileOrNull(fileName)
}
