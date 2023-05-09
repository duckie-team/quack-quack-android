/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.FileSpec
import java.io.File

/**
 * 주어진 [path]가 null이 아니면 해당 [path]에 파일을 생성하고,
 * 그렇지 않다면 제공된 build 폴더에 파일을 생성합니다.
 *
 * @param logger 생성된 파일 위치를 로깅할 선택적 logger
 * @param tag [logger]를 이용해 로깅할 때 사용할 선택적 태그
 */
public fun generateBuildOrLocalFile(
  codeGenerator: CodeGenerator,
  fileSpec: FileSpec,
  path: String?,
  logger: KSPLogger?,
  tag: String?,
) {
  val generatedPath: String
  if (path == null) {
    generatedPath = try {
      val fileOutstream = codeGenerator.createNewFile(
        dependencies = Dependencies.Empty,
        packageName = fileSpec.packageName,
        fileName = fileSpec.name,
      )
      fileOutstream.writer().use(fileSpec::writeTo)
      codeGenerator.generatedFile.first().path
    } catch (fileAlreadyExists: FileAlreadyExistsException) {
      fileAlreadyExists.file.path
    }
  } else {
    val file = File(path, "${fileSpec.name}.kt").also { file ->
      if (!file.exists()) {
        file.parentFile?.mkdirs()
        file.createNewFile()
      }
    }
    file.writeText(fileSpec.toString())
    generatedPath = file.path
  }
  logger?.warn("${tag?.let { "[$tag] " }}Generated at $generatedPath")
}
