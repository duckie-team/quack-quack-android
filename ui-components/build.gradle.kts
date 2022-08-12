// buildFeatures, composeOptions
@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.dokka)
    jacoco
}

android {
    namespace = "land.sungbin.duckie.quackquack.ui"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementations(projects.common, libs.util.profileinstaller)
    setupCompose(core = libs.bundles.compose.core, debug = libs.bundles.compose.debug)
}
