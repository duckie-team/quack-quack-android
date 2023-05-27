/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package internal

import java.io.File
import org.gradle.api.Project

// TODO: 테스트 방식 변경
internal var projectTestingMode = false

internal fun Project.parseArtifactVersion(): String {
  if (projectTestingMode) return "TEST"
  val versionFile = File(projectDir, "version.txt")
  if (!versionFile.exists() || !versionFile.isFile) {
    error(
      """
      There is no version.txt file in the project path. 
      Try `./gradlew :${project.name}:versioning -Ptype=init` for initialize version.
      """.trimIndent(),
    )
  }
  return versionFile.readLines().first()
}
