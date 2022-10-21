/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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

    if (isReleaseVariantAvailable) {
        signingConfigs {
            create("release") {
                storeFile = file(BuildConstants.StoreFilePath)
                storePassword = BuildConstants.StorePassword
                keyAlias = BuildConstants.KeyAlias
                keyPassword = BuildConstants.KeyPassword
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
