/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
    quackquack("android-library")
    quackquack("android-compose")
    quackquack("kotlin-explicit-api")
    quackquack("quack-publishing")
}

android {
    namespace = "team.duckie.quackquack.material"
    resourcePrefix = "quack_"
}

dependencies {
    implementations(
        libs.compose.uiutil,
        libs.compose.material, // needed for ripple feature (used in Modifier.quackClickable)
        libs.androidx.core.ktx, // needed for androidx.core.graphics (used in SquircleShape)
        projects.util.orArtifact(),
    )
}
