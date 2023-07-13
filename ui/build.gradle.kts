/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME as kotlinCompilerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  quackquack("android-library")
  quackquack("android-compose")
  quackquack("android-compose-metrics")
  quackquack("android-gmd")
  quackquack("kotlin-explicit-api")
  quackquack("quack-publishing")
  quackquack("test-junit")
  quackquack("test-roborazzi")
  alias(libs.plugins.kotlin.ksp)
  alias(libs.plugins.test.roborazzi)
}

tasks.withType<KotlinCompile> {
  val sugarProcessorKotlinCompilerPluginId = "team.duckie.quackquack.sugar.processor"
  val sugarPath = "$projectDir/src/main/kotlin/team/duckie/quackquack/ui/sugar"
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + listOf(
      "-P",
      "plugin:$sugarProcessorKotlinCompilerPluginId:sugarPath=$sugarPath",
    )
    freeCompilerArgs = freeCompilerArgs + listOf(
      "-P",
      "plugin:$sugarProcessorKotlinCompilerPluginId:poet=$sugarPoet",
    )
  }
}

android {
  namespace = "team.duckie.quackquack.ui"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.all { test ->
      test.maxHeapSize = "4G"
    }
  }
}

ksp {
  arg("AidePath", "$rootDir/aide/src/main/kotlin/team/duckie/quackquack/aide/rule")
  arg("CasaPath", "$rootDir/catalog/src/main/kotlin/team/duckie/quackquack/catalog")
}

// https://github.com/Kotlin/dokka/issues/2956
tasks.matching { task ->
  task.name.contains("javaDocReleaseGeneration", ignoreCase = true)
}.configureEach {
  enabled = false
}

val sample: Configuration by configurations.creating {
  isCanBeResolved = false
  isCanBeConsumed = false
}

dependencies {
  sample(projects.uiSample)

  apis(
    projects.animation.orArtifact(),
    projects.uiPlugin.orArtifact(),
    projects.sugarMaterial.orArtifact(),
  )
  implementations(
    libs.coil.compose,
    libs.compose.ui.core,
    libs.compose.ui.text,
    libs.compose.foundation,
    libs.androidx.annotation,
    projects.runtime.orArtifact(),
    projects.material.orArtifact(),
    projects.materialIcon.orArtifact(),
    projects.uiPlugin.image.orArtifact(),
    projects.uiPlugin.interceptor.orArtifact(),
    projects.util.orArtifact(),
    projects.utilModifier.orArtifact(),
    projects.casaAnnotation.orArtifact(),
  )

  testImplementation(libs.test.kotest.assertion.core)
  androidTestImplementations(
    libs.test.junit.compose,
    libs.test.kotest.assertion.core,
  )

   kotlinCompilerPlugin(projects.sugarCompiler.orArtifact())

  safeRunWithinDevelopmentMode {
    ksps(
      // TODO: projects.casaProcessor,
    )
     kotlinCompilerPlugin(projects.sugarCore)
  }
}
