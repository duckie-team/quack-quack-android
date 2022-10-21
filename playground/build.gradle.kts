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
    id(ConventionEnum.JvmDokka)
    id(libs.plugins.oss.license.get().pluginId)
}

android {
    namespace = "team.duckie.quackquack.playground"

    if (keystoreSigningAvailable) {
        val (storePath, storePassword, keyAlias, keyPassword) = keystoreSecrets
        signingConfigs {
            create("release") {
                storeFile = file(storePath)
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
            }
        }

        buildTypes {
            release {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    lint {
        // 플레이그라운드용 데모 컴포저블에 주석을 필수로 명시하는건 너무 과함
        disable.add("KDocFields")
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
        libs.compose.material,
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
