/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
}

android {
    namespace = "team.duckie.quackquack.runtime"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementations(
        libs.compose.ui,
        projects.util.orArtifact(),
    )
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
    )
}
