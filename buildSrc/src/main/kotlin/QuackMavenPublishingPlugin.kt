/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project

class QuackMavenPublishingPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        TODO("Not yet implemented")
    }
}

open class QuackMavenExtension {
    open lateinit var type: QuackArtifactType

    internal val isInitialized get() = ::type.isInitialized

    override fun toString() = """
        ## Debug
        artifactId: ${type.artifactId}
        description: ${type.description}
        level: ${type.level}
        
        ## toString
        artifactId: $type
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
    CommonKotlinPoet(
        artifactId = "quackquack-common-kotlinpoet",
        description = "Utils used by QuackQuack for KotlinPoet",
        level = ArtifactLevel.Internal,
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
    CoreSugar(
        artifactId = "quackquack-core-sugar",
        description = "A sugar syntax for the Duckie design system",
    ),
    CoreSugarAnnotation(
        artifactId = "quackquack-core-sugar-annotation",
        description = "Marker annotations for CoreSugar",
    ),
    CoreSugarProcessor(
        artifactId = "quackquack-core-sugar-processor",
        description = "Kotlin Symbol Processing for CoreSugarAnnotation",
        level = ArtifactLevel.Internal,
    ),
    DokkaPaparazziIntegrate(
        artifactId = "dokka-paparazzi-integrate",
        description = "DokkaPlugin to integrate Dokka and Paparazzi",
        level = ArtifactLevel.Internal,
    );

    override fun toString(): String {
        TODO()
    }
}

enum class ArtifactLevel {
    Public, Internal,
}
