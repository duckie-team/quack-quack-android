/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

// buildFeatures, composeOptions
@file:Suppress("UnstableApiUsage")

plugins {
    id(PluginEnum.Application)
    id(PluginEnum.ApplicationJacoco)
    id(PluginEnum.ApplicationCompose)
    alias(libs.plugins.dokka)
}

android {
    namespace = "land.sungbin.duckie.quackquack.playground"

    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
}

dependencies {
    implementations(projects.uiComponents)
}
