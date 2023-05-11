/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
  quackquack("jvm-kotlin")
  quackquack("quack-publishing")
  alias(libs.plugins.kotlin.ksp)
}

dependencies {
  compileOnly(libs.kotlin.embeddable.compiler)
  implementations(
    libs.google.autoservice.annotation,
    projects.utilBackendKotlinc,
  )
  ksp(libs.google.autoservice.ksp.processor)
}
