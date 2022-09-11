/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:58
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

BundleInsideHelper.forInsideLintJar(project)

plugins {
    id(PluginEnum.AndroidLint)
    id(PluginEnum.JvmKover)
    id(PluginEnum.JvmDokka)
}

dependencies {
    bundleInsides(
        projects.commonLint,
        projects.commonLintCompose,
    )
}
