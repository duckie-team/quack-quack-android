/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.JvmLibrary)
    id(PluginEnum.JvmKover)
    id(PluginEnum.JvmDokka)
}

dependencies {
    implementations(
        projects.common,
        libs.test.junit.core,
        libs.test.lint,
        libs.lint.api,
    )
}
