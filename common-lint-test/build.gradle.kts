/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 19. 오전 7:30
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.JvmLibrary)
}

dependencies {
    implementations(
        projects.common,
        libs.test.junit.core,
        libs.test.lint,
        libs.lint.api,
    )
}
