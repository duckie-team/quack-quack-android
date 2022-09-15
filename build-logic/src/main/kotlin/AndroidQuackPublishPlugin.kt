/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import team.duckie.quackquack.convention.QuackPublishExtension
import team.duckie.quackquack.convention.ext
import team.duckie.quackquack.convention.lintPublish

private const val QuackLintPublishExtensionName = "quackArtifactPublish"

class AndroidQuackPublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val extension = project.extensions.create<QuackPublishExtension>(
                name = QuackLintPublishExtensionName,
            )

            afterEvaluate {
                if (extension.isNotInitialized) {
                    throw GradleException(
                        """
                        |QuackPublishExtension 초기화가 누락되었거나 완전하지 않습니다.
                        |version 과 type 모두 초기화가 필요합니다.
                        """.trimMargin()
                    )
                }

                ext {
                    val properties = mapOf(
                        "PUBLISH_VERSION" to extension.version,
                        "PUBLISH_GROUP_ID" to "team.duckie.quack",
                        "PUBLISH_ARTIFACT_ID" to "quack-${extension.type.artifactName}",
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

                if (extension.type.isLint) {
                    dependencies {
                        lintPublish(
                            project(
                                path = extension.type.deployModuleArtifactName,
                                configuration = "default",
                                // https://github.com/dialogflow/dialogflow-android-client/issues/57#issuecomment-341329755
                            )
                        )
                    }
                }
            }
        }
    }
}
