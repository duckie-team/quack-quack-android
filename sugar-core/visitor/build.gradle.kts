/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
  quackquack("test-kotest")
}

dependencies {
  implementations(
    libs.kotlin.embeddable.compiler,
    projects.sugarMaterial,
    projects.sugarCore.node,
  )
  testImplementations(
    libs.test.kotlin.compilation.core,
    projects.utilBackendTest,
  )
}
