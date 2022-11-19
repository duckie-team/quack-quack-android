/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("UnstableApiUsage")

rootProject.name = "quack-quack"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
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
    ":common-lint",
    ":common-lint-test",
    ":ui-components",
    // ":ui-components-snapshots",
    // ":ui-components-benchmark",
    // ":ui-components-benchmark-app",
    ":lint-core",
    ":lint-quack",
    ":lint-compose",
    ":lint-writing",
    ":lint-core-publish",
    ":lint-quack-publish",
    ":lint-compose-publish",
    ":quack-publish-bom",
    ":ux-writing-rule",
    ":ux-writing-overlay",
    ":ux-writing-model",
)
