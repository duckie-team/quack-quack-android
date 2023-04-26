/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    quackquack("android-library")
    quackquack("android-compose")
    quackquack("android-compose-metrics")
    quackquack("kotlin-explicit-api")
    quackquack("quack-publishing")
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
