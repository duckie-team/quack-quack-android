/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-android-compose-metrics`
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
}

android {
    namespace = "team.duckie.quackquack.casa.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementations(
        libs.kotlin.collections.immutable,
        libs.compose.activity, // needed for BackHandler
        libs.compose.material3,
        libs.compose.uiutil,
        projects.casaMaterial,
    )
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
        libs.bundles.test.mockito,
    )
}
