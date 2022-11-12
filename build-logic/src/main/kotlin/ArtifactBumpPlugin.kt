/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import team.duckie.quackquack.convention.BumpType
import team.duckie.quackquack.convention.ReleaseTarget
import team.duckie.quackquack.convention.getVersionFile

class ArtifactBumpPlugin : Plugin<Project> {
    /**
     * 주어진 [BumpType] 과 [ReleaseTarget] 에 해당하는 버전을 bump 하여 반환합니다.
     *
     * @param type Bump 할 버전
     * @param target Bump 할 대상
     * @return Bump 된 버전
     */
    private fun Project.bumpVersion(
        type: BumpType,
        target: ReleaseTarget,
    ): String {
        val versionFile = getVersionFile(
            target = target,
        )
        val lines = versionFile.readLines().toMutableList()
        when (type) {
            BumpType.Major -> {
                val major = lines[0].split("=")[1].toInt()
                lines[0] = "major=${major + 1}"
                lines[1] = "minor=0"
                lines[2] = "patch=0"
            }
            BumpType.Minor -> {
                val minor = lines[1].split("=")[1].toInt()
                lines[1] = "minor=${minor + 1}"
                lines[2] = "patch=0"
            }
            BumpType.Patch -> {
                val patch = lines[2].split("=")[1].toInt()
                lines[2] = "patch=${patch + 1}"
            }
        }
        if (target == ReleaseTarget.Playground) {
            val code = lines[3].split("=")[1].toInt()
            lines[3] = "code=${code + 1}"
        }
        return lines.joinToString(separator = "\n")
    }

    /**
     * 주어진 label 을 파싱하여 설정된 [BumpType] 과 [ReleaseTarget] 에 맞게
     * bump 를 진행합니다.
     */
    override fun apply(project: Project) {
        with(project) {
            tasks.create("bump") {
                val type = (properties["type"] ?: return@create) as String
                val target = (properties["target"] ?: return@create) as String
                val bumpType = BumpType.valueOf(
                    value = type,
                )
                val releaseTarget = ReleaseTarget.valueOf(
                    value = target,
                )
                val version = bumpVersion(
                    type = bumpType,
                    target = releaseTarget,
                )
                val versionFile = getVersionFile(
                    target = releaseTarget,
                )
                versionFile.writeText(
                    text = version,
                )
            }
        }
    }
}
