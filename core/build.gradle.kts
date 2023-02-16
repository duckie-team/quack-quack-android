/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    `android-library`
    alias(libs.plugins.test.paparazzi)
}

GradleInstallation.with(project) {
    library {
        namespace = "team.duckie.quackquack.core"
        resourcePrefix = "quack_"

        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
        }
    }
    compose()
    junit()
    explicitApi()
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
    testImplementation(libs.test.strikt)
    androidTestImplementation(libs.test.strikt)
    androidTestImplementation(libs.test.mockito)
    androidTestImplementation(libs.test.junit.compose)
}
