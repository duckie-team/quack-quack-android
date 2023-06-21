/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("android-library")
  quackquack("kotlin-explicit-api")
  quackquack("test-kotest")
  quackquack("quack-publishing")
}

android {
  namespace = "team.duckie.quackquack.util"
}

dependencies {
  api(libs.compose.ui.util)
  implementation(libs.compose.foundation)
}
