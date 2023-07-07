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
}

android {
  namespace = "team.duckie.quackquack.util.compose.snapshot.test"
}

dependencies {
  implementations(
    libs.compose.runtime,
    libs.compose.ui.core,
    libs.compose.foundation,
    libs.test.robolectric,
    libs.test.junit.core,
  )
}
