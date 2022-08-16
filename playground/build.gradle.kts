/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

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
     lintChecks(projects.lintCompose)
}
