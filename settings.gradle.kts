/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("UnstableApiUsage")

rootProject.name = "quack-quack"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// https://github.com/vanniktech/gradle-maven-publish-plugin/issues/259
// enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

buildCache {
    local {
        removeUnusedEntriesAfterDays = 7
    }
}

include(
    ":playground",
    ":core",
    ":core-aide",
    ":core-aide-annotation",
    ":core-aide-processor",
    ":core-sugar-material",
    ":core-sugar-processor-kotlinc",
    ":casa-ui",
    ":casa-material",
    ":casa-processor",
    // ":dokka-paparazzi-integration",
    ":screenshot-matcher",
)
