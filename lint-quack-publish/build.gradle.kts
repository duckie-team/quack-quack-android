/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import team.duckie.quackquack.convention.QuackArtifactType

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidQuackPublish)
}

android {
    namespace = "team.duckie.quackquack.lint.quack.publish"
}

quackArtifactPublish {
    version = libs.versions.quack.lint.quack.get()
    type = QuackArtifactType.LintQuack
}
