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
  quackquack("test-junit")
  quackquack("test-roborazzi")
  quackquack("quack-publishing")
  alias(libs.plugins.test.roborazzi)
}

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":ui-plugin:dokkaHtmlMultiModule")
}

android {
  namespace = "team.duckie.quackquack.ui.plugin.image"
}

dependencies {
  api(projects.uiPlugin.orArtifact())
  implementations(
    libs.coil.compose,
    libs.compose.runtime,
  )
  testImplementations(
    libs.coil.test,
    libs.test.kotest.assertion.core,
    projects.ui,
    projects.utilComposeRuntimeTest,
  )
}
