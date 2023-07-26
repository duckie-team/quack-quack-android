/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

plugins {
  quackquack("jvm-kotlin")
  quackquack("quack-publishing")
}

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":sugar-hosted:dokkaHtmlMultiModule")
}

dependencies {
  implementations(
    libs.kotlin.embeddable.compiler,
    projects.sugarMaterial.orArtifact(),
    projects.sugarHosted.error.orArtifact(),
    projects.sugarHosted.names.orArtifact(),
    projects.sugarHosted.node.orArtifact(),
    projects.utilBackendKotlinc.orArtifact(),
  )
}
