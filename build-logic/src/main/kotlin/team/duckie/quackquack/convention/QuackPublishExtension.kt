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

    val isNotInitialized get() = !::version.isInitialized || !::type.isInitialized

    override fun toString(): String {
        return "version: $version, artifactName:" +
                " ${type.artifactName}, " +
                "description: ${type.description}, " +
                "deployModuleArtifactName: ${type.deployModuleArtifactName}"
    }
}

sealed class QuackArtifactType(
    val artifactName: String,
    val description: String,
    val deployModuleArtifactName: String,
) {
    // 플러그인 초기화에 사용하는 기본 값으로 쓰임
    internal object Empty : QuackArtifactType(
        artifactName = String.Empty,
        description = String.Empty,
        deployModuleArtifactName = String.Empty,
    )

    object UiComponents : QuackArtifactType(
        artifactName = "ui-components",
        description = "Duckie's design system core module",
        deployModuleArtifactName = ":ui-components",
    )

    object LintCompose : QuackArtifactType(
        artifactName = "compose",
        description = buildLintArtifactDescription(
            target = "Jetpack Compose codebase",
        ),
        deployModuleArtifactName = ":lint-compose",
    )

    object LintCore : QuackArtifactType(
        artifactName = "core",
        description = buildLintArtifactDescription(
            target = "Duckie codebase",
        ),
        deployModuleArtifactName = ":lint-core",
    )

    object LintQuack : QuackArtifactType(
        artifactName = "quack",
        description = buildLintArtifactDescription(
            target = "QuackQuack ui components",
        ),
        deployModuleArtifactName = ":lint-quack",
    )

    /*object LintWriting : QuackArtifactType(
        artifactName = "writing",
        description = buildLintArtifactDescription(
            target = "UX Writing",
        ),
        modulePackage= "lint.writing.publish",
    )*/

    internal fun isContainsBlank() = artifactName.isBlank() || description.isBlank()
}

private fun buildLintArtifactDescription(
    target: String,
) = "Duckie's design system custom lint for $target"
