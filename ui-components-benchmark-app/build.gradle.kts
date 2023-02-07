/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidApplicationCompose)
}

android {
    namespace = "team.duckie.quackquack.ui.benchmark.app"

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
                isDebuggable = false
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}

dependencies {
    implementations(
        projects.uiComponents,
        libs.util.profileinstaller,
    )
}
