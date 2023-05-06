/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import org.gradle.api.artifacts.ProjectDependency

/**
 * 로컬 모듈을 publishing된 아티팩트로 변환합니다.
 * 해당 모듈이 maven-central에 publishing 되어 있다고 가정합니다.
 *
 * @see useArtifact
 */
fun ProjectDependency.orArtifact(): Any {
    return if (useArtifact) {
        val artifact = ArtifactConfig.of(dependencyProject)
        artifact.toString()
    } else {
        this
    }
}
