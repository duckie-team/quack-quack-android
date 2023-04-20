/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-android-compose-metrics`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
}

android {
    namespace = "team.duckie.quackquack.casa.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(projects.casaMaterial.orArtifact())
    implementations(
        libs.kotlin.collections.immutable,
        libs.compose.activity, // needed for BackHandler
        libs.compose.material3,
        libs.compose.uiutil,
    )
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
    )
}
