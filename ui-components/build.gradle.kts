/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
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
    implementations(projects.common, libs.util.profileinstaller)
    lintPublishs(projects.lintCore, projects.lintCompose, projects.lintWriting)
}
