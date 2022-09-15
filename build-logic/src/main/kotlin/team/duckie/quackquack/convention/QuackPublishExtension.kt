/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

// data class 하면 final 이라 안됨
open class QuackPublishExtension {
    open lateinit var version: String
    open lateinit var type: QuackArtifactType

    internal val isNotInitialized get() = !::version.isInitialized || !::type.isInitialized

    override fun toString() = "version: $version, " +
            "artifactName: ${type.artifactName}, " +
            "description: ${type.description}, " +
            "deployModuleArtifactName: ${type.deployModuleArtifactName}"
}

sealed class QuackArtifactType(
    val artifactName: String,
    val description: String,
    val deployModuleArtifactName: String,
) {
    object Bom : QuackArtifactType(
        artifactName = "bom",
        description = "Duckie's design system artifacts BOM",
        deployModuleArtifactName = ":quack-publish-bom",
    )

    object UiComponents : QuackArtifactType(
        artifactName = "ui-components",
        description = "Duckie's design system core module",
        deployModuleArtifactName = ":ui-components",
    )

    object LintCore : QuackArtifactType(
        artifactName = "lint-core",
        description = buildLintArtifactDescription(
            target = "Duckie codebase",
        ),
        deployModuleArtifactName = ":lint-core",
    )

    object LintQuack : QuackArtifactType(
        artifactName = "lint-quack",
        description = buildLintArtifactDescription(
            target = "QuackQuack ui components",
        ),
        deployModuleArtifactName = ":lint-quack",
    )

    object LintCompose : QuackArtifactType(
        artifactName = "lint-compose",
        description = buildLintArtifactDescription(
            target = "Jetpack Compose codebase",
        ),
        deployModuleArtifactName = ":lint-compose",
    )

    // TODO: UX Writing 린트 완성되면 주석 해제
    /*object LintWriting : QuackArtifactType(
        artifactName = "lint-writing",
        description = buildLintArtifactDescription(
            target = "UX Writing",
        ),
        deployModuleArtifactName = ":lint-writing",
    )*/

    internal val isBom = artifactName == "bom"
    internal val isLint = deployModuleArtifactName.contains("lint")
}

private fun buildLintArtifactDescription(
    target: String,
) = "Duckie's design system custom lint for $target"
