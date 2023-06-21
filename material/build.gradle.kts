/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM", "UnstableApiUsage")

plugins {
  quackquack("android-library")
  quackquack("android-compose")
  quackquack("kotlin-explicit-api")
  quackquack("test-junit")
  quackquack("quack-publishing")
  alias(libs.plugins.kotlin.dataclass.nocopy)
  alias(libs.plugins.test.roborazzi)
}

android {
  namespace = "team.duckie.quackquack.material"
  resourcePrefix = "quack_"

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      isReturnDefaultValues = true
      all { test ->
        test.systemProperty("robolectric.graphicsMode", "NATIVE")
      }
    }
  }
}

dependencies {
  apis(
    projects.uiPlugin.orArtifact(),
    projects.materialIcon.orArtifact(),
  )
  implementations(
    libs.compose.ui.util,
    libs.compose.material.ripple,
    libs.androidx.core.ktx, // needed for androidx.core.graphics (used in SquircleShape)
    projects.util.orArtifact(),
  )

  testImplementations(
    libs.test.robolectric,
    libs.test.junit.compose,
    libs.test.kotlin.coroutines, // needed for compose-ui-test
    libs.test.kotest.assertion.core,
    libs.bundles.test.roborazzi,
  )
}
