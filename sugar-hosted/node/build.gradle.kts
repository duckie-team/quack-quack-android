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
}

dependencies {
  implementations(
    libs.kotlin.embeddable.compiler,
    projects.sugarHosted.names.orArtifact(),
    projects.sugarHosted.error.orArtifact(),
    projects.sugarMaterial.orArtifact(),
    projects.casaAnnotation.orArtifact(),
    projects.utilBackendKotlinc.orArtifact(),
  )
}
