/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import internal.applyPlugins
import internal.libs
import internal.parseArtifactVersion
import java.time.LocalDate
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType

private const val RepositoryName = "duckie-team/quack-quack-android"
private const val QuackBaseArtifactId = "team.duckie.quack"
private const val QuackPublishExtensionName = "quack"

class QuackMavenPublishingPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                libs.findPlugin("gradle-publish-maven-core").get().get().pluginId,
                libs.findPlugin("gradle-publish-maven-base").get().get().pluginId,
            )

            group = QuackBaseArtifactId
            val extension = project.extensions.create<QuackMavenExtension>(
                name = QuackPublishExtensionName,
            )

            afterEvaluate {
                if (!extension.isInitialized) {
                    logger.log(
                        LogLevel.WARN,
                        "QuackMavenExtension 초기화가 누락되었습니다. quack 초기화가 필요합니다.",
                    )
                    return@afterEvaluate
                }

                version = parseArtifactVersion()

                extensions.configure<PublishingExtension> {
                    publications.withType<MavenPublication> {
                        artifactId = extension.type.asArtifactId()
                    }
                }

                extensions.configure<MavenPublishBaseExtension> {
                    publishToMavenCentral(
                        host = SonatypeHost.S01,
                        automaticRelease = true,
                    )

                    signAllPublications()

                    pom {
                        configureMavenPom(extension)
                    }
                }
            }
        }
    }
}

private fun MavenPom.configureMavenPom(extension: QuackMavenExtension) {
    name.set(extension.type.artifactId)
    description.set(extension.type.description)
    inceptionYear.set(LocalDate.now().year.toString())
    url.set("https://github.com/$RepositoryName")
    licenses {
        license {
            name.set("MIT License")
            url.set("https://github.com/$RepositoryName/blob/2.x.x/LICENSE")
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
        url.set("https://github.com/$RepositoryName/tree/main")
        connection.set("scm:git:github.com/$RepositoryName.git")
        developerConnection.set("scm:git:ssh://github.com/$RepositoryName.git")
    }
}

open class QuackMavenExtension {
    open lateinit var type: QuackArtifactType

    internal val isInitialized get() = ::type.isInitialized

    override fun toString() = """
        artifactId: ${type.artifactId}
        description: ${type.description}
        level: ${type.level}
    """.trimIndent()
}

enum class QuackArtifactType(
    internal val artifactId: String,
    internal val description: String,
    internal val level: ArtifactLevel = ArtifactLevel.Public,
) {
    BOM(
        artifactId = "quackquack-bom",
        description = "Duckie's design system artifacts BOM",
    ),
    Core(
        artifactId = "quackquack-core",
        description = "The UI components of the Duckie design system",
    ),
    CoreAide(
        artifactId = "quackquack-core-aide",
        description = "Design convention lint for the Duckie design system",
    ),
    CoreAideAnnotation(
        artifactId = "quackquack-core-aide-annotation",
        description = "Marker annotations for CoreAide",
    ),
    CoreAideProcessor(
        artifactId = "quackquack-core-aide-processor",
        description = "Kotlin Symbol Processing for CoreAideAnnotation",
        level = ArtifactLevel.Internal,
    ),
    CoreSugarAnnotation(
        artifactId = "quackquack-core-sugar-annotation",
        description = "Marker annotations for CoreSugar",
    ),
    CoreSugarProcessorKotlinc(
        artifactId = "quackquack-core-sugar-processor-kotlinc",
        description = "Kotlin Compiler Plugin for CoreSugarAnnotation",
        level = ArtifactLevel.Internal,
    ),
    DokkaPaparazziIntegrate(
        artifactId = "dokka-paparazzi-integrate",
        description = "DokkaPlugin to integrate Dokka and Paparazzi",
        level = ArtifactLevel.Internal,
    );

    fun asArtifactId() =
        artifactId + if (level == ArtifactLevel.Internal) "-internal" else ""
}

internal enum class ArtifactLevel {
    Public,
    Internal,
}
