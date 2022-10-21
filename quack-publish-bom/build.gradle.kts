/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

// ISSUE: https://github.com/sungbinland/duckie-quack-quack/issues/114

plugins {
    id(ConventionEnum.AndroidLibrary)
    // id(ConventionEnum.AndroidQuackPublish)
}

android {
    namespace = "team.duckie.quackquack.publish.bom"
}

/*quackArtifactPublish {
    type = team.duckie.quackquack.convention.QuackArtifactType.Bom
}*/
