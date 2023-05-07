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
  compileOnly(libs.kotlin.embeddable.compiler)
  implementations(
    libs.google.autoservice.annotation,
    libs.kotlin.kotlinpoet.core,
    projects.casaAnnotation.orArtifact(),
    projects.sugarMaterial.orArtifact(),
    projects.utilBackendKotlinc.orArtifact(),
  )
  ksp(libs.google.autoservice.ksp.processor)
  testImplementations(
    libs.test.kotlin.compilation.core,
    projects.utilBackendTest,
  )
}
