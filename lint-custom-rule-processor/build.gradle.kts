/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    id(ConventionEnum.JvmLibrary)
    id(ConventionEnum.JvmKover)
    id(ConventionEnum.JvmDokka)
}

dependencies {
    implementations(
        projects.lintCustomRuleAnnotation,
        libs.ksp.api,
    )
}
