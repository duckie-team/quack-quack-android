/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

plugins {
  quackquack("android-library")
  quackquack("android-compose")
  quackquack("kotlin-explicit-api")
  quackquack("quack-publishing")
  alias(libs.plugins.test.roborazzi)
}

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":ui-plugin:dokkaHtmlMultiModule")
}

android {
  namespace = "team.duckie.quackquack.ui.plugin.interceptor"

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
  api(projects.uiPlugin.orArtifact())
  implementations(
    libs.compose.runtime,
    libs.compose.ui.core,
    projects.material,
    projects.utilModifier,
  )
  testImplementations(
    libs.test.robolectric,
    libs.test.junit.compose,
    libs.test.kotest.assertion.core,
    libs.test.kotlin.coroutines, // needed for compose-ui-test
    libs.bundles.test.roborazzi,
    projects.ui,
    projects.utilComposeSnapshotTest,
  )
}
