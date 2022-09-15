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
    id(libs.plugins.gradle.maven.publish.base.get().pluginId)
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.AndroidQuackPublish)
    id(ConventionEnum.JvmKover)
    id(ConventionEnum.JvmDokka)
    alias(libs.plugins.paparazzi)
}

group = "team.duckie.quack"
version = libs.versions.quack.ui.components.get()

publishing {
    publications.withType<MavenPublication> {
        artifactId = "quack-ui-components"
    }
}

// TODO: resourcePrefix
android {
    namespace = "team.duckie.quackquack.ui"

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
    // api(projects.common)
    implementations(
        projects.common,
        projects.lintCorePublish,
        projects.lintComposePublish,
        libs.util.profileinstaller,
        libs.compose.material,
        libs.compose.glide,
        libs.kotlin.collections.immutable,
    )
    testImplementation(libs.test.parameter.injector)
}

quackArtifactPublish {
    type = QuackArtifactType.UiComponents
}
