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
  quackquack("test-junit")
  quackquack("quack-publishing")
  alias(libs.plugins.test.roborazzi)
}

android {
  namespace = "team.duckie.quackquack.ui.plugin.image.gif"

  sourceSets {
    getByName("test").resources.srcDir("src/test/assets")
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
  api(projects.uiPlugin.image.orArtifact())
  implementations(
    libs.compose.runtime,
    libs.coil.gif,
  )
  testImplementations(
    libs.test.robolectric,
    libs.test.junit.compose,
    libs.test.kotlin.coroutines, // needed for compose-ui-test
    libs.bundles.test.roborazzi,
    projects.ui,
  )
}
