/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-jvm-junit`
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-android-compose-metrics`
    alias(libs.plugins.test.paparazzi)
}

tasks.withType<DokkaTask> {
    moduleName.set("QuackQuack-Core")
}

android {
    namespace = "team.duckie.quackquack.core"
    resourcePrefix = "quack_"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.compose.uiutil,
        libs.compose.coil,
        libs.compose.animation,
        libs.compose.material,
        projects.coreAideAnnotation,
        projects.coreSugarAnnotation,
    )
    api(libs.kotlin.collections.immutable)
    testImplementation(libs.test.strikt)
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
        libs.bundles.test.mockito,
        projects.screenshotMatcher,
    )
}
