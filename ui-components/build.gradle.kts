/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 1:00
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

plugins {
    id(PluginEnum.Library)
    id(PluginEnum.LibraryJacoco)
    id(PluginEnum.LibraryCompose)
    alias(libs.plugins.dokka)
}

android {
    namespace = "land.sungbin.duckie.quackquack.ui"
}

dependencies {
    implementations(
        projects.common,
        libs.util.profileinstaller,
        libs.compose.material,
        libs.compose.glide,
    )
    // 한개의 린트만 publish 가능
    lintPublish(projects.lintCompose)
}
