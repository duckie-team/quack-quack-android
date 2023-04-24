/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
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
