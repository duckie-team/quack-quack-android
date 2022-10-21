/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidApplicationCompose)
}

android {
    namespace = "team.duckie.quackquack.ui.benchmark.app"
}

dependencies {
    implementations(
        projects.uiComponents,
        libs.util.profileinstaller,
    )
}
