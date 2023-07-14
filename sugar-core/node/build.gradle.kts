/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
  quackquack("quack-publishing")
}

dependencies {
  implementations(
    libs.kotlin.embeddable.compiler,
    projects.sugarCore.names.orArtifact(),
    projects.sugarCore.error.orArtifact(),
    projects.sugarMaterial.orArtifact(),
    projects.casaAnnotation.orArtifact(),
    projects.utilBackendKotlinc.orArtifact(),
  )
}
