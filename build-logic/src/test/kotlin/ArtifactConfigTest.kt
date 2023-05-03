/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import internal.projectTestingMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class ArtifactConfigTest : StringSpec() {
    override suspend fun beforeAny(testCase: TestCase) {
        projectTestingMode = true
    }

    init {
        "프로젝트의 아티팩트 주소를 정상적으로 파싱함" {
            val buildLogicProject = ProjectBuilder
                .builder()
                .withName("buildlogic")
                .build()
            val artifactConfigProject = ProjectBuilder
                .builder()
                .withName("buildlogic-testing-artifactconfig")
                .build()
            val buildLogicArtifact = ArtifactConfig.of(buildLogicProject)
            val artifactConfigArtifact = ArtifactConfig.of(artifactConfigProject)

            buildLogicArtifact.toString() shouldBe "team.duckie.quackquack.buildlogic:buildlogic:TEST"
            artifactConfigArtifact.toString() shouldBe "team.duckie.quackquack.buildlogic:buildlogic-testing-artifactconfig:TEST"
        }
    }

    override suspend fun afterAny(testCase: TestCase, result: TestResult) {
        projectTestingMode = false
    }
}
