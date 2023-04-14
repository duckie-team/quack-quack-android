/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-android-compose-metrics`
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
    alias(libs.plugins.test.paparazzi)
}

tasks.withType<DokkaTask> {
    moduleName.set("QuackQuack-Animation")
    // notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/1217")
}

android {
    namespace = "team.duckie.quackquack.animation"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementations(
        libs.compose.animation,
        projects.util,
    )
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
    )
}
