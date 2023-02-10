/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.convention

import java.io.File
import org.gradle.api.Project

/**
 * 아티팩트의 버전을 구합니다.
 *
 * @param type 아티팩트 타입
 * @return 아티팩트의 버전
 */
internal fun Project.getArtifactVersion(
    type: QuackArtifactType,
): String {
    val versionFileFolder = "$rootDir/versions/"
    val versionFilePath = when (type) {
        is QuackArtifactType.UiComponents -> "ui-components.txt"
        is QuackArtifactType.LintCore -> "lint-core.txt"
        is QuackArtifactType.LintQuack -> "lint-quack.txt"
        is QuackArtifactType.LintCompose -> "lint-compose.txt"
        is QuackArtifactType.LintWriting -> "lint-writing.txt"
    }
    val versionFile = File(versionFileFolder, versionFilePath)
    val versions = versionFile.readLines().map { line ->
        line.split("=")[1]
    }
    return versions.joinToString(
        separator = ".",
    )
}

/**
 * 플레이그라운드의 VersionName 과 VersionCode 를 구합니다.
 *
 * @return 플레이그라운드의 VersionName 과 VersionCode 를 담은 [Pair] 객체
 */
internal fun Project.getPlaygroundVersion(): Pair<String, Int> {
    val versionLines = File("$rootDir/versions/playground.txt").readLines()
    val versions = versionLines
        .dropLast(
            n = 1,
        ).map { line ->
            line.split("=")[1]
        }
    return versions.joinToString(
        separator = ".",
    ) to versionLines.last().split("=")[1].toInt()
}

