/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

// ISSUE: https://github.com/sungbinland/duckie-quack-quack/issues/114

plugins {
    id(ConventionEnum.AndroidLibrary)
    // id(ConventionEnum.AndroidQuackPublish)
}

android {
    namespace = "team.duckie.quackquack.publish.bom"
}

// bom 배포는 따로 해야 함
/*quackArtifactPublish {
    type = team.duckie.quackquack.convention.QuackArtifactType.Bom
}*/
