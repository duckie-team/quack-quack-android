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
    `buildlogic-kotlin-explicitapi`
    `buildlogic-jvm-dokka`
    `buildlogic-test-kotest`
}

tasks.withType<DokkaTask> {
    moduleName.set("QuackQuack-Util")
    // notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/1217")
}

android {
    namespace = "team.duckie.quackquack.util"
}

dependencies {
    implementations(
        libs.compose.uiutil,
        libs.compose.foundation,
    )
    testImplementation(libs.test.strikt)
}
