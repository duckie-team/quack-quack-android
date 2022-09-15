/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    // id(ConventionEnum.AndroidQuackPublish)
}

android {
    namespace = "team.duckie.quackquack.lint.core.publish"
}

/*quackArtifactPublish {
    version = libs.versions.quack.lint.core.get()
    type = QuackArtifactType.LintCore
}*/
