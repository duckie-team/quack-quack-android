/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

import java.io.File
import org.gradle.api.Project

internal fun Project.getArtifectVersion(
    type: QuackArtifactType,
): String {
    val versionFileFolder = "$rootDir/quackquack-version"
    val versionFilePath = when (type) {
        is QuackArtifactType.UiComponents -> "ui-components.txt"
        is QuackArtifactType.LintCore -> "lint-core.txt"
        is QuackArtifactType.LintQuack -> "lint-quack.txt"
        is QuackArtifactType.LintCompose -> "lint-compose.txt"
    }
    val versionFile = File(versionFileFolder, versionFilePath)
    val versions = versionFile.readLines().map { line ->
        line.split("=")[1]
    }
    return versions.joinToString(
        separator = ".",
    )
}

internal fun Project.getPlaygroundVersionName(): String {
    val versionFile = File("$rootDir/playground-version.txt")
    val versions = versionFile
        .readLines()
        .dropLast(
            n = 1,
        ).map { line ->
            line.split("=")[1]
        }
    return versions.joinToString(
        separator = ".",
    )
}

internal fun Project.getPlaygroundVersionCode(): Int {
    val versionFile = File("$rootDir/playground-version.txt")
    return versionFile.readLines().last().split("=")[1].toInt()
}

