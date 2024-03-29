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
    projects.sugarHosted,
    projects.sugarHosted.error,
    projects.sugarHosted.visitor,
    projects.sugarHosted.codegen,
    projects.utilBackendTest,
  )
}
