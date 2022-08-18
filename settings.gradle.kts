/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [settings.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 1:01
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle-conventions")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "quack-quack"
include(
    ":common",
    ":common-lint",
    ":common-lint-test",
    ":common-lint-compose",
    ":playground",
    ":lint-core",
    ":lint-quack",
    ":lint-compose",
    ":lint-writing",
    ":ui-components",
    ":benchmark",
)
