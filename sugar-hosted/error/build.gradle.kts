import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

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

tasks.withType<DokkaMultiModuleTask> {
  dependsOn(":sugar-hosted:dokkaHtmlMultiModule")
}

dependencies {
  implementation(projects.sugarHosted.names.orArtifact())
}
