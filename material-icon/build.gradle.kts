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
  alias(libs.plugins.test.roborazzi)
}

android {
  namespace = "team.duckie.quackquack.material.icon"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

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
  implementation(libs.compose.ui)

  testImplementations(
    libs.compose.foundation,
    libs.test.robolectric,
    libs.test.junit.compose,
    libs.test.kotest.assertion.core,
    libs.test.kotlin.coroutines, // needed for compose-ui-test
    libs.bundles.test.roborazzi,
  )
}
