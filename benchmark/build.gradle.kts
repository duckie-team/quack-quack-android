/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.AndroidBenchmark)
    id(PluginEnum.JvmDokka)
    alias(libs.plugins.kover)
}

android {
    namespace = "team.duckie.quackquack.ui.benchmark"
}
