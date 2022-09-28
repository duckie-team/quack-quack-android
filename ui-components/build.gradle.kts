/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
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
    id(ConventionEnum.JvmKover)
    id(ConventionEnum.JvmDokka)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.kotlin.api.validation)
}

android {
    namespace = "team.duckie.quackquack.ui"
    resourcePrefix = "quack_"

    buildTypes {
        sourceSets.getByName("debug") {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        sourceSets.getByName("release") {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

dependencies {
    apis(
        libs.kotlin.collections.immutable,
    )
    implementations(
        projects.lintCorePublish,
        projects.lintComposePublish,
        libs.compose.material,
        libs.compose.glide,
        libs.compose.flowlayout,
    )
    testImplementation(libs.test.parameter.injector)
}

quackArtifactPublish {
    type = QuackArtifactType.UiComponents
}
