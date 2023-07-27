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
}

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":sugar-hosted:dokkaHtmlMultiModule")
}

dependencies {
  implementations(
    libs.kotlin.embeddable.compiler,
    libs.kotlin.kotlinpoet.core,
    projects.sugarHosted.node,
    projects.sugarHosted.names,
    projects.sugarHosted.error,
    projects.utilBackendKotlinc,
    projects.utilBackendKotlinpoet,
  )
}
