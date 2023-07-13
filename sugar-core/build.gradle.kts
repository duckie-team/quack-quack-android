/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
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
    projects.sugarCore.node,
    projects.sugarCore.visitor,
    projects.sugarCore.codegen,
  )
  ksp(libs.google.autoservice.ksp.processor)
}
