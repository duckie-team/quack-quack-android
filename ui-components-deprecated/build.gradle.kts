/*
* Designed and developed by Duckie Team, 2022~2023
*
* Licensed under the MIT.
* Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
*/

@file:Suppress("UnstableApiUsage")

plugins {
    `android-library`
}

GradleInstallation.with(project) {
    library {
        namespace = "team.duckie.quackquack.ui"
        resourcePrefix = "quack_"

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
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
    )
    api(libs.kotlin.collections.immutable)
}
