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

// https://github.com/Kotlin/dokka/issues/2956
tasks.matching { task ->
  task.name.contains("javaDocReleaseGeneration", ignoreCase = true)
}.configureEach {
  enabled = false
}

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":ui-plugin:dokkaHtmlMultiModule")
}

android {
  namespace = "team.duckie.quackquack.ui.plugin.interceptor.textfield"
}

dependencies {
  api(projects.uiPlugin.interceptor.orArtifact())
  implementations(
    libs.compose.runtime,
    libs.compose.ui.text,
    projects.ui,
  )
  testImplementations(
    libs.compose.foundation,
    libs.test.kotest.assertion.core,
    projects.utilComposeSnapshotTest,
  )
}
