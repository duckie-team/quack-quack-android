/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
}

tasks.withType<DokkaTask> {
    moduleName.set("QuackQuack-Runtime")
    // notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/1217")
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
        projects.util,
    )
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
    )
}
