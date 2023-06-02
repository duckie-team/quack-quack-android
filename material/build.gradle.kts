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
  quackquack("kotlin-explicit-api")
  quackquack("test-kotest")
  quackquack("quack-publishing")
  alias(libs.plugins.kotlin.dataclass.nocopy)
}

android {
  namespace = "team.duckie.quackquack.material"
  resourcePrefix = "quack_"
}

dependencies {
  apis(
    projects.uiPlugin.orArtifact(),
    projects.materialIcon.orArtifact(),
  )
  implementations(
    libs.compose.uiutil,
    libs.compose.material.ripple,
    libs.androidx.core.ktx, // needed for androidx.core.graphics (used in SquircleShape)
    projects.util.orArtifact(),
  )
}
