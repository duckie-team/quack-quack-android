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
  quackquack("test-kotest")
  quackquack("quack-publishing")
}

android {
  namespace = "team.duckie.quackquack.util.compose.runtime.test"

  testOptions {
    unitTests {
      isReturnDefaultValues = true
    }
  }
}

dependencies {
  implementation(libs.compose.runtime)
}
