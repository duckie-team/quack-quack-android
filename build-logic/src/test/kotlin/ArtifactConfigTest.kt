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
    "단일 프로젝트의 아티팩트 주소를 정상적으로 파싱함" {
      val buildLogicProject =
        ProjectBuilder
          .builder()
          .withName("buildlogic")
          .build()
      val artifactConfigProject =
        ProjectBuilder
          .builder()
          .withName("buildlogic-testing-artifactconfig")
          .build()
      val buildLogicArtifact = ArtifactConfig.of(buildLogicProject)
      val artifactConfigArtifact = ArtifactConfig.of(artifactConfigProject)

      buildLogicArtifact.toString() shouldBe "team.duckie.quackquack.buildlogic:buildlogic:TEST"
      artifactConfigArtifact.toString() shouldBe "team.duckie.quackquack.buildlogic:buildlogic-testing-artifactconfig:TEST"
    }

    "중첩 프로젝트의 아티팩트 주소를 정상적으로 파싱함" {
      val rootProject =
        ProjectBuilder
          .builder()
          .withName("root")
          .build()
      val subProject =
        ProjectBuilder
          .builder()
          .withName("root-sub")
          .withParent(rootProject)
          .build()
      val subNestedProject =
        ProjectBuilder
          .builder()
          .withName("nested-project")
          .withParent(subProject)
          .build()
      val subNestedLevel2Project =
        ProjectBuilder
          .builder()
          .withName("level2")
          .withParent(subNestedProject)
          .build()

      val config = ArtifactConfig.of(subNestedLevel2Project).toString()
      config shouldBe "team.duckie.quackquack.root:root-sub-nested-project-level2:TEST"
    }
  }

  override suspend fun afterAny(testCase: TestCase, result: TestResult) {
    projectTestingMode = false
  }
}
