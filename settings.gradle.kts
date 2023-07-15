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
  ":runtime",
  ":material",
  ":material-icon",
  ":material-icon-generator",
  ":animation",
  ":ui",
  ":ui-plugin",
  ":ui-plugin:image",
  ":ui-plugin:image:gif",
  ":ui-plugin:interceptor",
  ":ui-plugin:interceptor:textfield",
  ":ui-sample",
  ":ui-sugar",
  ":sugar-material",
  ":sugar-compiler",
  ":sugar-hosted",
  ":sugar-hosted:node",
  ":sugar-hosted:visitor",
  ":sugar-hosted:transformer",
  ":sugar-hosted:codegen",
  ":sugar-hosted:names",
  ":sugar-hosted:error",
  ":sugar-test",
  ":casa-ui",
  ":casa-annotation",
  ":casa-material",
  ":casa-processor",
  ":util",
  ":util-modifier",
  ":util-backend-ksp",
  ":util-backend-kotlinc",
  ":util-backend-kotlinpoet",
  ":util-backend-test",
  ":util-compose-runtime-test",
  ":util-compose-snapshot-test",
  ":bom",
)
