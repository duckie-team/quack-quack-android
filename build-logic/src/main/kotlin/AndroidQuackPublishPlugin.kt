/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "unused",
    "UnstableApiUsage",
)

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType
import team.duckie.quackquack.convention.QuackPublishExtension
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.getArtifactVersion
import team.duckie.quackquack.convention.libs

private const val QuackLintPublishExtensionName = "quackArtifactPublish"

class AndroidQuackPublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins(
                libs.findPlugin("gradle-maven-publish-base").get().get().pluginId,
            )

            val extension = project.extensions.create<QuackPublishExtension>(
                name = QuackLintPublishExtensionName,
            )

            group = "team.duckie.quack"
            extensions.configure<MavenPublishBaseExtension> {
                configure(
                    platform = AndroidSingleVariantLibrary(
                        publishJavadocJar = false,
                    ),
                )
            }

            afterEvaluate {
                if (extension.isNotInitialized) {
                    throw GradleException(
                        """
                        |QuackPublishExtension 초기화가 누락되었습니다.
                        |type 초기화가 필요합니다.
                        """.trimMargin()
                    )
                }

                version = getArtifactVersion(
                    type = extension.type,
                ).also { version ->
                    println(
                        "${extension.type.artifactId} version: $version",
                    )
                }

                extensions.configure<PublishingExtension> {
                    publications.withType<MavenPublication> {
                        artifactId = extension.type.artifactId
                    }
                }

                // It is too late to set namespace
                // It has already been read to configure this project.
                // Consider either moving this call to be during evaluation,
                // or using the variant API.
                // extensions.configure<LibraryExtension> {
                //     namespace = extension.type.modulePackageName
                // }

                extensions.configure<MavenPublishBaseExtension> {
                    publishToMavenCentral(
                        host = SonatypeHost.S01,
                        automaticRelease = true,
                    )

                    signAllPublications()

                    pom {
                        name.set(extension.type.artifactId)
                        description.set(extension.type.description)
                        inceptionYear.set("2022")
                        url.set("https://github.com/duckie-team/duckie-quack-quack")
                        configureQuackPom()
                    }
                }
            }
        }
    }
}

private fun MavenPom.configureQuackPom() {
    licenses {
        license {
            name.set("MIT License")
            url.set("https://github.com/duckie-team/duckie-quack-quack/blob/develop/LICENSE")
        }
    }
    developers {
        developer {
            id.set("jisungbin")
            name.set("Ji Sungbin")
            url.set("https://sungb.in")
            email.set("ji@sungb.in")
        }
    }
    scm {
        url.set("https://github.com/duckie-team/duckie-quack-quack/tree/main")
        connection.set("scm:git:github.com/duckie-team/duckie-quack-quack.git")
        developerConnection.set("scm:git:ssh://github.com/duckie-team/duckie-quack-quack.git")
    }
}
