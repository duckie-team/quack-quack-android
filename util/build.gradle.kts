/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-android-library`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-test-kotest`
    `buildlogic-quack-mavenpublishing`
}

android {
    namespace = "team.duckie.quackquack.util"
}

dependencies {
    api(libs.compose.uiutil)
    implementation(libs.compose.foundation)
    testImplementation(libs.test.strikt)
}
