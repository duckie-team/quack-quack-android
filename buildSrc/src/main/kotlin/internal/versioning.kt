/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package internal

import java.io.File
import org.gradle.api.Project

internal fun Project.parseArtifactVersion(): String {
    val versionFile = File(projectDir, "version.txt")
    if (!versionFile.exists() || !versionFile.isFile) {
        error(
            """
            There is no version.txt file in the project path. 
            Try `./gradlew :project:versioning -Ptype=init` for version configuration.
            """.trimIndent(),
        )
    }
    return versionFile.readText()
}
