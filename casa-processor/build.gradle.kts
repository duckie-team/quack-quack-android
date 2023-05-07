/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
  quackquack("jvm-kotlin")
  quackquack("test-kotest")
  quackquack("quack-publishing")
  alias(libs.plugins.kotlin.ksp)
}

ksp {
  arg("autoserviceKsp.verify", "true")
  arg("autoserviceKsp.verbose", "true")
}

dependencies {
  ksp(libs.google.autoservice.ksp.processor)
  implementations(
    libs.kotlin.ksp.api,
    libs.kotlin.kotlinpoet.core,
    libs.kotlin.collections.immutable,
    libs.google.autoservice.annotation,
    projects.utilBackend,
  )
  testImplementations(
    libs.test.kotlin.compilation.ksp,
    projects.utilBackendTest,
  )
}
