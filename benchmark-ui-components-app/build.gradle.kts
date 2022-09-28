/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidApplicationCompose)
    id(ConventionEnum.JvmKover)
}

android {
    namespace = "team.duckie.quackquack.ui.benchmark.app"

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
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
    }

    lint {
        disable.add("NotificationPermission")
    }
}

dependencies {
    implementations(
        projects.uiComponents,
        libs.util.profileinstaller,
    )
}
