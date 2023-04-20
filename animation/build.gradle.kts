/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-android-compose-metrics`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
}

android {
    namespace = "team.duckie.quackquack.animation"
}

dependencies {
    api(projects.material.orArtifact())
    implementations(
        libs.compose.animation,
        projects.util.orArtifact(),
    )
}
