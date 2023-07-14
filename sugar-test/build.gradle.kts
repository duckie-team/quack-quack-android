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
  testImplementations(
    libs.test.kotlin.compilation.core,
    projects.sugarCompiler,
    projects.sugarCore,
    projects.sugarCore.error,
    projects.sugarCore.visitor,
    projects.sugarCore.codegen,
    projects.utilBackendTest,
  )
}
