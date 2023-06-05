/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("UnstableApiUsage")

rootProject.name = "quack-quack"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }

  includeBuild("build-logic")
}

buildCache {
  local {
    removeUnusedEntriesAfterDays = 7
  }
}

include(
  ":catalog",
  ":util",
  ":util-modifier",
  ":util-backend",
  ":util-backend-ksp",
  ":util-backend-kotlinc",
  ":util-backend-test",
  ":util-compose-runtime-test",
  ":runtime",
  ":material",
  ":material-icon",
  ":material-icon-generator",
  ":animation",
  ":ui",
  ":ui-plugin",
  ":ui-plugin:image",
  ":ui-plugin:image:gif",
  ":ui-sample",
  ":aide",
  ":aide-annotation",
  ":aide-processor",
  ":sugar-material",
  ":sugar-processor",
  ":casa-ui",
  ":casa-annotation",
  ":casa-material",
  ":casa-processor",
  ":screenshot-matcher",
  ":bom",
  ":rubberdoc",
  ":rubberdoc-material",
)
