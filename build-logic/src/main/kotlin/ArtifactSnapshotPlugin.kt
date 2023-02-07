/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("LocalVariableName")

import org.gradle.api.Plugin
import org.gradle.api.Project
import team.duckie.quackquack.convention.ReleaseTarget
import team.duckie.quackquack.convention.getVersionFile

class ArtifactSnapshotPlugin : Plugin<Project> {
    /**
     * 주어진 [ReleaseTarget] 에 해당하는 버전을 스냅샷으로 설정하여 반환합니다.
     *
     * 스냅샷 버전은 versions 폴더 안에 있는 현재 버전에서 `y` 값을 +1 하고,
     * `z` 값을 0 으로 교체한 값을 사용합니다. **즉, `z` 는 항상 0 으로 고정됩니다.**
     * 이는 "스냅샷" 버전임을 강조하기 위함입니다.
     *
     * @param target 스냅샷 버전을 사용할 대상
     * @return 스냅샷 설정된 버전
     */
    private fun Project.setSnapshotVersion(
        target: ReleaseTarget,
    ): String {
        val versionFile = getVersionFile(
            target = target,
        )
        val lines = versionFile.readLines().toMutableList()
        val minor = lines[1].split("=")[1].toInt()
        lines[1] = "minor=${minor + 1}"
        lines[2] = "patch=0"
        return lines.joinToString(
            separator = "\n",
            postfix = "-SNAPSHOT",
        )
    }

    /**
     * 주어진 label 을 파싱하여 설정된 [ReleaseTarget] 의 버전을
     * 스냅샷 버전으로 설정합니다.
     */
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            tasks.create("setSnapshotVersion") {
                val _target = (properties["target"] ?: return@create) as String
                val releaseTarget = ReleaseTarget.valueOf(
                    value = _target,
                )
                val snapshotVersion = setSnapshotVersion(
                    target = releaseTarget,
                )
                val versionFile = getVersionFile(
                    target = releaseTarget,
                )
                versionFile.writeText(
                    text = snapshotVersion,
                )
            }
        }
    }
}
