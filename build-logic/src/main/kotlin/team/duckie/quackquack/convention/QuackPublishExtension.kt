/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

// needs `open` class
open class QuackPublishExtension {
    open lateinit var type: QuackArtifactType

    internal val isNotInitialized get() = !::type.isInitialized

    override fun toString() = "artifactName: ${type.artifactId}, " +
            "description: ${type.description}"
}

sealed class QuackArtifactType(
    val artifactId: String,
    val description: String,
) {
    // https://github.com/duckie-team/duckie-quack-quack/issues/114
    /*object Bom : QuackArtifactType(
        artifactId = "quack-bom",
        description = "Duckie's design system artifacts BOM",
        deployModuleName = ":quack-publish-bom",
    )*/

    object UiComponents : QuackArtifactType(
        artifactId = "quack-ui-components",
        description = "Duckie's design system core module",
    )

    object LintCore : QuackArtifactType(
        artifactId = "quack-lint-core",
        description = buildLintArtifactDescription(
            target = "Duckie codebase",
        ),
    )

    object LintQuack : QuackArtifactType(
        artifactId = "quack-lint-quack",
        description = buildLintArtifactDescription(
            target = "QuackQuack ui components",
        ),
    )

    object LintCompose : QuackArtifactType(
        artifactId = "quack-lint-compose",
        description = buildLintArtifactDescription(
            target = "Jetpack Compose codebase",
        ),
    )

    // TODO: UX Writing 린트 완성되면 주석 해제
    /*object LintWriting : QuackArtifactType(
        artifactName = "quack-lint-writing",
        description = buildLintArtifactDescription(
            target = "UX Writing",
        ),
    )*/
}

private fun buildLintArtifactDescription(
    target: String,
) = "Duckie's design system custom lint for $target"
