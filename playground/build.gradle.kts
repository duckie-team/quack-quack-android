/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

// buildFeatures, composeOptions
@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-android")
    alias(libs.plugins.dokka)
    jacoco
}

android {
    namespace = "land.sungbin.duckie.quackquack.playground"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementations(projects.uiComponents)
    linkChecksOverride(projects.lintCore, projects.lintCompose, projects.lintWriting)
    setupCompose(core = libs.bundles.compose.core, debug = libs.bundles.compose.debug)
}
