/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
    quackquack("android-library")
    quackquack("android-compose")
    quackquack("android-compose-metrics")
    quackquack("kotlin-explicit-api")
    quackquack("quack-publishing")
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
