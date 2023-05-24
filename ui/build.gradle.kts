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
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

ksp {
  arg("AidePath", "$rootDir/aide/src/main/kotlin/team/duckie/quackquack/aide/rule")
  arg("CasaPath", "$rootDir/catalog/src/main/kotlin/team/duckie/quackquack/catalog")
}

val sample: Configuration by configurations.creating {
  isCanBeResolved = false
  isCanBeConsumed = false
}

dependencies {
  sample(projects.uiSample)

  apis(
    projects.animation.orArtifact(),
    projects.sugarMaterial.orArtifact(),
  )
  implementations(
    libs.compose.coil,
    libs.compose.ui,
    libs.compose.foundation,
    libs.androidx.annotation,
    projects.runtime.orArtifact(),
    projects.material.orArtifact(),
    projects.util.orArtifact(),
    projects.casaAnnotation.orArtifact(),
    projects.aideAnnotation.orArtifact(),
  )

  androidTestImplementations(
    libs.test.junit.compose,
    libs.test.kotest.assertion.core,
    libs.bundles.test.mockito,
    projects.screenshotMatcher,
  )
  testImplementations(
    libs.test.robolectric,
    libs.test.junit.compose,
    libs.test.kotest.assertion.core,
    libs.bundles.test.roborazzi,
  )

  // kotlinCompilerPlugin(projects.sugarProcessor.orArtifact())

  // Found more than one jar in the 'lintPublish' configuration. (compose lint)
  // lintPublish(projects.aide.orArtifact())

  safeRunWithinDevelopmentMode {
    ksps(
      projects.aideProcessor,
      // TODO: projects.casaProcessor,
    )
    // kotlinCompilerPlugin(projects.docusaurusIntegration)
  }
}
