/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

plugins {
    `buildlogic-android-application`
    `buildlogic-android-compose`
}

android {
    namespace = "team.duckie.quackquack.playground"
}

dependencies {
    implementations(
        libs.compose.activity,
        "team.duckie.quack:${QuackArtifactType.Core.forceInternal().asArtifactId()}:0.1.4"
    )
}
