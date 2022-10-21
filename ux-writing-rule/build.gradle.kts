/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmKover)
}

android {
    namespace = "team.duckie.quackquack.uxwriting.rule"
}

dependencies {
    lintChecks(projects.lintCore)
}
