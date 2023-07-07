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
  quackquack("test-roborazzi")
  quackquack("quack-publishing")
  alias(libs.plugins.test.roborazzi)
}

// https://github.com/Kotlin/dokka/issues/2956
tasks.matching { task ->
  task.name.contains("javaDocReleaseGeneration", ignoreCase = true)
}.configureEach {
  enabled = false
}

android {
  namespace = "team.duckie.quackquack.ui.plugin.image.gif"

  sourceSets {
    getByName("test").resources.srcDir("src/test/assets")
  }
}

dependencies {
  api(projects.uiPlugin.image.orArtifact())
  implementations(
    libs.compose.runtime,
    libs.coil.gif,
  )
  testImplementation(projects.ui)
}
