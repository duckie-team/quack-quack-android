/*
 * Designed and developed by Duckie Team, 2022
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
    namespace = "team.duckie.quackquack.lint.core.publish"
}

dependencies {
    lintPublish(
        project(
            path = ":lint-core",
            configuration = "default",
            // https://github.com/dialogflow/dialogflow-android-client/issues/57#issuecomment-341329755
        )
    )
}

quackArtifactPublish {
    type = QuackArtifactType.LintCore
}
