/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:56
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.AndroidLibrary)
    id(PluginEnum.ModuleJacoco)
    alias(libs.plugins.dokka)
}

android {
    namespace = "team.duckie.quackquack.common"
}

dependencies {
    apis(libs.util.logeukes)
}
