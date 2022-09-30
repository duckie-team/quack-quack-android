/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidApplicationCompose)
    id(ConventionEnum.JvmKover)
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
