/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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
    id(libs.plugins.oss.license.get().pluginId)
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
    }

    lint {
        disable.apply {
            // 플레이그라운드용 데모 컴포저블에 주석을 필수로 명시하는건 너무 과함
            add("KDocFields")
        }
    }
}

dependencies {
    implementations(
        libs.ktx.core,
        libs.util.oss.license,
        libs.util.systemuicontroller,
        libs.compose.material3,
        libs.androidx.appcompat,
        libs.androidx.splash,
        libs.androidx.datastore,
        libs.kotlin.collections.immutable,
        projects.uiComponents,
        projects.uxWritingRule,
        projects.uxWritingModel,
        projects.uxWritingOverlay,
    )
    lintChecks(projects.lintCore)
    lintChecks(projects.lintCompose)
}
