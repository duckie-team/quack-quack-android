/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

plugins {
    `android-library`
}

GradleInstallation.with(project) {
    library {
        namespace = "team.duckie.quackquack.core"
        resourcePrefix = "quack_"

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
            freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
        }
    }
    compose()
}

dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.compose.uiutil,
        libs.compose.coil,
        libs.compose.animation,
        libs.compose.material,
        projects.coreSugarAnnotation,
    )
    api(libs.kotlin.collections.immutable)
}
