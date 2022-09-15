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
    namespace = "team.duckie.quackquack.publish.bom"
}

/*quackArtifactPublish {
    version = libs.versions.quack.bom.get()
    type = QuackArtifactType.Bom
}*/

/*dependencies {
    implementations(
        // libs.quack.ui.components,
        libs.quack.lint.core,
        libs.quack.lint.quack,
        libs.quack.lint.compose,
    )
}*/
