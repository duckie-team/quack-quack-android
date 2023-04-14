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
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
}

tasks.withType<DokkaTask> {
    moduleName.set("QuackQuack-Material")
    // notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/1217")
}

android {
    namespace = "team.duckie.quackquack.material"
    resourcePrefix = "quack_"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementations(
        libs.compose.uiutil,
        libs.compose.foundation,
        libs.androidx.core.ktx, // needed for androidx.core.graphics (used in SquircleShape)
    )
    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
    )
}
