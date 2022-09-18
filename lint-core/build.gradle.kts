/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

BundleInsideHelper.forInsideLintJar(project)

plugins {
    id(ConventionEnum.AndroidLint)
    id(ConventionEnum.JvmKover)
    id(ConventionEnum.JvmDokka)
}

dependencies {
    bundleInside(projects.commonLint)
}
