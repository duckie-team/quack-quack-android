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

    // 외부에서 의존성이 제공되고 있는데 출처를 모르겠음
    resolutionStrategy.eachPlugin {
        if (requested.id.id == "com.vanniktech.maven.publish") {
            useVersion("0.24.0")
        }
    }
}

buildCache {
    local {
        removeUnusedEntriesAfterDays = 7
    }
}

include(
    ":playground",
    // ":common-poet",
    ":core",
    ":core-aide",
    ":core-aide-annotation",
    ":core-aide-processor",
    ":core-sugar",
    ":core-sugar-annotation",
    ":core-sugar-processor",
    // ":dokka-paparazzi-integration",
    ":screenshot-matcher",
)
