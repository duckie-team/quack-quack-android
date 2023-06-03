/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
}

repositories {
  maven("https://jitpack.io")
}

dependencies {
  implementation(libs.compose.svg.converter)
}
