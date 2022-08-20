/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 16. 오후 7:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.AndroidLint)
}

BundleInsideHelper.forInsideLintJar(project)

dependencies {
    bundleInsides(
        projects.commonLintCompose,
    )
}
