/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.Library)
    id(PluginEnum.LibraryJacoco)
    alias(libs.plugins.dokka)
}

android {
    namespace = "land.sungbin.duckie.quackquack.lint.writing"
}

dependencies {
    implementations(projects.common)
    setupLint(core = libs.bundles.lint, test = libs.test.lint)
}
