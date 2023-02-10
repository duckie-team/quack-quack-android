/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage",
)

plugins {
    id(ConventionEnum.AndroidQuackUiComponentsBenchmark)
    id(ConventionEnum.JvmDokka)
}

android {
    namespace = "team.duckie.quackquack.ui.benchmark"

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
            create("release") {
                isDebuggable = false
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}
