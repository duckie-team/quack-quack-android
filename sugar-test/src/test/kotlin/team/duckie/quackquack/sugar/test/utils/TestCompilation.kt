/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.quackquack.sugar.test.utils

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import java.io.File
import org.jetbrains.kotlin.config.JvmTarget
import team.duckie.quackquack.util.backend.test.findGeneratedFileOrNull

class TestCompilation(private val tempDir: File) {
  private var prepareSetting: (KotlinCompilation.(tempDir: File) -> Unit)? = null

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
      prepareSetting?.invoke(this, tempDir)
    }

  fun prepareSetting(block: KotlinCompilation.(tempDir: File) -> Unit) {
    prepareSetting = block
  }

  fun findGeneratedFileOrNull(fileName: String) = tempDir.findGeneratedFileOrNull(fileName)
}
