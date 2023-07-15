/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
  quackquack("quack-publishing")
  alias(libs.plugins.kotlin.ksp)
}

ksp {
  arg("autoserviceKsp.verify", "true")
  arg("autoserviceKsp.verbose", "true")
}

dependencies {
  compileOnly(libs.kotlin.embeddable.compiler)
  ksp(libs.google.autoservice.ksp.processor)
  implementations(
    libs.google.autoservice.annotation,
    projects.sugarHosted.names.orArtifact(),
    projects.sugarHosted.error.orArtifact(),
    projects.sugarHosted.node.orArtifact(),
    projects.sugarHosted.visitor.orArtifact(),
    projects.utilBackendKotlinc.orArtifact(),
  )
}
