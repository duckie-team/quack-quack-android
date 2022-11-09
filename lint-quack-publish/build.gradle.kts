/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import team.duckie.quackquack.convention.QuackArtifactType

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidQuackPublish)
}

android {
    namespace = "team.duckie.quackquack.lint.quack.publish"
}

dependencies {
    lintPublish(
        project(
            path = ":lint-quack",
            configuration = "default",
            // https://github.com/dialogflow/dialogflow-android-client/issues/57#issuecomment-341329755
        )
    )
}

quackArtifactPublish {
    type = QuackArtifactType.LintQuack
}
