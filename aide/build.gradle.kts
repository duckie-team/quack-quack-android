/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin") // fix order ("compileOnly" configuration missing)
  quackquack("android-lint")
  quackquack("test-kotest")
  quackquack("quack-publishing")
  alias(libs.plugins.kotlin.ksp)
}

dependencies {
  implementation(libs.google.autoservice.annotation)
  ksp(libs.google.autoservice.ksp.processor)
}
