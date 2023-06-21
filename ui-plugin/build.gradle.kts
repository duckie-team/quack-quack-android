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
  quackquack("test-kotest")
  quackquack("quack-publishing")
}

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":dokkaHtmlMultiModule")
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
    libs.compose.ui.core,
  )
  testImplementations(
    projects.utilModifier,
    projects.utilComposeRuntimeTest,
  )
}
