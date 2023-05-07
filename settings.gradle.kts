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
  ":util-backend",
  ":util-backend-test",
  ":runtime",
  ":material",
  ":animation",
  ":ui",
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
  ":docusaurus-integration",
)
