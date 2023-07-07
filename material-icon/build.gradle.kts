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
  quackquack("test-roborazzi")
  quackquack("quack-publishing")
  alias(libs.plugins.test.roborazzi)
}

android {
  namespace = "team.duckie.quackquack.material.icon"
}

dependencies {
  implementation(libs.compose.ui.core)
  testImplementations(
    libs.compose.foundation,
    libs.test.kotest.assertion.core,
  )
}
