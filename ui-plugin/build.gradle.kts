/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

plugins {
  quackquack("android-library")
  quackquack("android-compose")
  quackquack("kotlin-explicit-api")
  quackquack("test-kotest")
  quackquack("quack-publishing")
}

android {
  namespace = "team.duckie.quackquack.ui.plugin"

  testOptions {
    unitTests {
      isReturnDefaultValues = true
    }
  }
}

dependencies {
  implementations(
    libs.compose.runtime,
    libs.compose.ui,
  )
  testImplementations(
    projects.utilModifier,
    projects.utilComposeRuntimeTest,
  )
}
