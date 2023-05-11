/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  quackquack("android-library")
  quackquack("android-compose")
}

android {
  namespace = "team.duckie.quackquack.ui.sample"

  sourceSets {
    getByName("main").java.srcDir("src/sample/kotlin")
  }
}

tasks.withType<KotlinCompile> {
  enabled = false
}

dependencies {
  compileOnly(projects.ui)
}
