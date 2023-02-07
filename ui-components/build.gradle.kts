/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

import team.duckie.quackquack.convention.QuackArtifactType

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.AndroidQuackPublish)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
    // alias(libs.plugins.kotlin.api.validation)
}

android {
    namespace = "team.duckie.quackquack.ui"
    resourcePrefix = "quack_"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }

    lint {
        // FIXME: https://github.com/duckie-team/quack-quack-android/issues/144
        disable.add("PreferredImmutableCollections")

        // FIXME: https://github.com/duckie-team/quack-quack-android/issues/368
        disable.add("SpecifyAnimationSpec")

        // FIXME: https://github.com/duckie-team/quack-quack-android/issues/383
        disable.add("TrailingComma")
    }
}

dependencies {
    implementations(
        libs.compose.coil,
        libs.compose.ui.util,
        libs.compose.material,
        // projects.uxWritingModel,
    )
    api(libs.kotlin.collections.immutable)
    // lintChecks(projects.lintCore)
    // lintChecks(projects.lintCompose)
}

quackArtifactPublish {
    type = QuackArtifactType.UiComponents
}
