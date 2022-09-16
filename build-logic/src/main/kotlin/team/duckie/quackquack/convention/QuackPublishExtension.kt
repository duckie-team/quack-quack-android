/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

import org.gradle.api.Project

// needs `open` class
open class QuackPublishExtension {
    open lateinit var type: QuackArtifactType

    internal val isNotInitialized get() = !::type.isInitialized

    override fun toString() = "artifactName: ${type.artifactId}, " +
            "description: ${type.description}, " +
            "deployModuleArtifactName: ${type.deployModuleName}"
}

sealed class QuackArtifactType(
    val artifactId: String,
    val description: String,
    val deployModuleName: String,
) {
    object Bom : QuackArtifactType(
        artifactId = "quack-bom",
        description = "Duckie's design system artifacts BOM",
        deployModuleName = ":quack-publish-bom",
    )

    object UiComponents : QuackArtifactType(
        artifactId = "quack-ui-components",
        description = "Duckie's design system core module",
        deployModuleName = ":ui-components",
    )

    object LintCore : QuackArtifactType(
        artifactId = "quack-lint-core",
        description = buildLintArtifactDescription(
            target = "Duckie codebase",
        ),
        deployModuleName = ":lint-core",
    )

    object LintQuack : QuackArtifactType(
        artifactId = "quack-lint-quack",
        description = buildLintArtifactDescription(
            target = "QuackQuack ui components",
        ),
        deployModuleName = ":lint-quack",
    )

    object LintCompose : QuackArtifactType(
        artifactId = "quack-lint-compose",
        description = buildLintArtifactDescription(
            target = "Jetpack Compose codebase",
        ),
        deployModuleName = ":lint-compose",
    )

    // TODO: UX Writing 린트 완성되면 주석 해제
    /*object LintWriting : QuackArtifactType(
        artifactName = "quack-lint-writing",
        description = buildLintArtifactDescription(
            target = "UX Writing",
        ),
        deployModuleArtifactName = ":lint-writing",
    )*/

    internal val isLint = deployModuleName.contains("lint")

    fun getVersion(project: Project) = project.libs.findVersion(
        when (this) {
            is Bom -> "quack-bom"
            is UiComponents -> "quack-ui-components"
            is LintCore -> "quack-lint-core"
            is LintQuack -> "quack-lint-quack"
            is LintCompose -> "quack-lint-compose"
        }
    ).get().toString()
}

private fun buildLintArtifactDescription(
    target: String,
) = "Duckie's design system custom lint for $target"
