/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "quack-quack"
include(
    ":playground",
    ":common-lint",
    ":common-lint-test",
    ":ui-components",
    ":lint-core",
    ":lint-quack",
    ":lint-compose",
    ":lint-writing",
    ":lint-custom-rule-annotation",
    ":lint-custom-rule-processor",
    ":lint-core-publish",
    ":lint-quack-publish",
    ":lint-compose-publish",
    ":quack-publish-bom",
    ":benchmark-ui-components",
    ":benchmark-ui-components-app",
)
