/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(PluginEnum.AndroidApplication)
    id(PluginEnum.AndroidApplicationCompose)
    id(PluginEnum.JvmKover)
    id(PluginEnum.JvmDokka)
}

android {
    namespace = "team.duckie.quackquack.playground"

    signingConfigs {
        create("release") {
            storeFile = file(BuildConstants.StoreFilePath)
            storePassword = BuildConstants.StorePassword
            keyAlias = BuildConstants.KeyAlias
            keyPassword = BuildConstants.KeyPassword
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }

        sourceSets.getByName("debug") {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        sourceSets.getByName("release") {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }

        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    lint {
        disable.add("NotificationPermission")
    }
}

dependencies {
    implementations(
        projects.common,
        projects.uiComponents,
        libs.ktx.core,
        libs.util.systemuicontroller,
        libs.compose.material3,
        libs.androidx.datastore,
        libs.kotlin.collections.immutable,
    )
    customLints(
        projects.lintCore,
        projects.lintCompose,
    )
}
