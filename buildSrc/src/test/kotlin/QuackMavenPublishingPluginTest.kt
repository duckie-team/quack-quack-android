import io.kotest.core.spec.style.StringSpec
import org.gradle.testfixtures.ProjectBuilder
import rules.setUpProjectTestModeRule
import rules.tearDownProjectTestingRule
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

class QuackMavenPublishingPluginTest : StringSpec() {
    init {
        beforeSpec(setUpProjectTestModeRule)
        afterSpec(tearDownProjectTestingRule)

        // TODO: Testing
        "ArtifactConfig.from".config(enabled = false) {
            val mockProject = ProjectBuilder
                .builder()
                .withName("my-awesome-project")
                .build()

            val artifactConfig = ArtifactConfig.of(mockProject)
            expectThat(artifactConfig) {
                get { group } isEqualTo "team.duckie.quackquack.my"
                get { module } isEqualTo "my-awesome-project"
                get { version } isEqualTo "TEST"
            }
        }
    }
}
