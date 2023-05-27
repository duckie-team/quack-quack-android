/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("android-library")
  quackquack("kotlin-explicit-api")
  quackquack("quack-publishing")
  quackquack("test-kotest")
}

android {
  namespace = "team.duckie.quackquack.util.modifier"
}

dependencies {
  implementations(
    libs.compose.ui,
    libs.compose.uiutil,
  )
}
