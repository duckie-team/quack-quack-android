/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.Empty
import team.duckie.quackquack.convention.QuackArtifactType
import team.duckie.quackquack.convention.QuackPublishExtension
import team.duckie.quackquack.convention.ext
import team.duckie.quackquack.convention.lintPublish

private const val QuackLintPublishExtensionName = "quackLintPublish"

class AndroidQuackPublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val extension = project.extensions.create<QuackPublishExtension>(
                name = QuackLintPublishExtensionName,
                /*default constructor = */
                String.Empty, QuackArtifactType.Empty,
            )

            afterEvaluate {
                if (extension.version.isBlank() || extension.type.isContainsBlank()) return@afterEvaluate

                ext {
                    val properties = mapOf(
                        "PUBLISH_VERSION" to extension.version,
                        "PUBLISH_GROUP_ID" to "team.duckie.quack",
                        "PUBLISH_ARTIFACT_ID" to "quack-lint-${extension.type.artifactName}",
                        "PUBLISH_DESCRIPTION" to extension.type.description,
                        "PUBLISH_URL" to "https://github.com/sungbinland/duckie-quack-quack",
                        "PUBLISH_LICENSE_NAME" to "MIT License",
                        "PUBLISH_LICENSE_URL" to "https://github.com/sungbinland/duckie-quack-quack/blob/develop/LICENSE",
                        "PUBLISH_DEVELOPER_ID" to "jisungbin",
                        "PUBLISH_DEVELOPER_NAME" to "Ji Sungbin",
                        "PUBLISH_DEVELOPER_EMAIL" to "ji@sungb.in",
                        "PUBLISH_SCM_CONNECTION" to "scm:git:github.com/sungbinland/duckie-quack-quack.git",
                        "PUBLISH_SCM_DEVELOPER_CONNECTION" to "scm:git:ssh://github.com/sungbinland/duckie-quack-quack.git",
                        "PUBLISH_SCM_URL" to "https://github.com/sungbinland/duckie-quack-quack/tree/main",
                    )

                    properties.forEach { (key, value) ->
                        set(key, value)
                    }
                }

                apply(from = "${rootDir}/scripts/publish-module.gradle")

                dependencies {
                    lintPublish(extension.type.deployModuleArtifactName)
                }
            }
        }
    }
}
