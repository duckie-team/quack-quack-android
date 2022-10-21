@file:Suppress("UnstableApiUsage")

/*
* Designed and developed by 2022 SungbinLand, Team Duckie
*
* Licensed under the MIT.
* Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
*/

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.JvmJUnit4)
}

android {
    namespace = "team.duckie.quackquack.uxwriting.overlay"

    lint {
        disable.add(
            // SnapshotStateList 사용이 필수임
            "PreferredImmutableCollections",
        )
    }
}

dependencies {
    implementations(
        projects.uxWritingModel,
        projects.uxWritingRule,
        libs.compose.material3,
    )
    lintChecks(projects.lintCore)
    lintChecks(projects.lintCompose)
}
