// composeOptions
@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.dokka)
    jacoco
}

android {
    namespace = "land.sungbin.duckie.quackquack.ui"

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementations(libs.bundles.compose.core, libs.util.profileinstaller)
    debugImplementations(libs.bundles.compose.debug, libs.bundles.customview)
}