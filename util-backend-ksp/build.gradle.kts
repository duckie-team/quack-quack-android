/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
  quackquack("kotlin-explicit-api")
}

dependencies {
  implementations(
    libs.kotlin.ksp.api,
    libs.kotlin.kotlinpoet.core,
  )
}
