/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

import java.io.File
import org.gradle.api.Project
import team.duckie.quackquack.convention.BumpType.Major
import team.duckie.quackquack.convention.BumpType.Minor
import team.duckie.quackquack.convention.BumpType.Patch
import team.duckie.quackquack.convention.ReleaseTarget.LintCompose
import team.duckie.quackquack.convention.ReleaseTarget.LintCore
import team.duckie.quackquack.convention.ReleaseTarget.LintQuack
import team.duckie.quackquack.convention.ReleaseTarget.LintWriting
import team.duckie.quackquack.convention.ReleaseTarget.Playground
import team.duckie.quackquack.convention.ReleaseTarget.UiComponents

/**
 * Bump 할 버전을 정의합니다.
 *
 * @property Major Major 버전을 bump 합니다.
 * @property Minor Minor 버전을 bump 합니다.
 * @property Patch Patch 버전을 bump 합니다.
 */
internal enum class BumpType {
    Major,
    Minor,
    Patch;
}

/**
 * Release 할 대상을 정의합니다.
 *
 * @property Playground ui-components 의 playground 를 release 합니다.
 * @property LintCore lint-core 를 release 합니다.
 * @property LintQuack lint-quack 를 release 합니다.
 * @property LintCompose lint-compose 를 release 합니다.
 * @property LintWriting lint-writing 를 release 합니다.
 * @property UiComponents ui-components 를 release 합니다.
 */
internal enum class ReleaseTarget {
    UiComponents,
    Playground,
    LintCore,
    LintQuack,
    LintCompose,
    LintWriting;
}

/**
 * 특정 [ReleaseTarget] 에 맞는 versioning [파일][File] 인스턴스를 반환합니다.
 *
 * @param target versioning 파일을 얻어오고자 하는 [ReleaseTarget]
 * @return [ReleaseTarget] 에 맞는 versioning [파일][File] 인스턴스
 */
internal fun Project.getVersionFile(
    target: ReleaseTarget,
) = File(
    when (target) {
        UiComponents -> "$rootDir/versions/ui-components.txt"
        Playground -> "$rootDir/versions/playground.txt"
        LintCore -> "$rootDir/versions/lint-core.txt"
        LintQuack -> "$rootDir/versions/lint-quack.txt"
        LintCompose -> "$rootDir/versions/lint-compose.txt"
        LintWriting -> "$rootDir/versions/lint-writing.txt"
    }
)
