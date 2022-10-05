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
    id(ConventionEnum.JvmDokka)
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
        disable.apply {
            add("NotificationPermission")
            // 플레이그라운드용 데모 컴포저블에 주석을 필수로 명시하는건 너무 과함
            add("KDocFields")
        }
    }
}

dependencies {
    implementations(
        projects.uiComponents,
        projects.lintCorePublish,
        projects.lintComposePublish,
        libs.ktx.core,
        libs.util.systemuicontroller,
        libs.compose.material3,
        libs.compose.material,
        libs.androidx.datastore,
        libs.kotlin.collections.immutable,
    )
}
