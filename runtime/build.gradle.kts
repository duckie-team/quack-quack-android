/*
* Designed and developed by Duckie Team 2023.
*
* Licensed under the MIT.
* Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
*/

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
    quackquack("android-library")
    quackquack("android-compose")
    quackquack("kotlin-explicit-api")
    quackquack("quack-publishing")
    quackquack("test-kotest")
    alias(libs.plugins.kotlin.dataclass.nocopy)
}

android {
    namespace = "team.duckie.quackquack.runtime"

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementations(
        libs.compose.ui,
        projects.util.orArtifact(),
    )
    testImplementations(
        libs.test.kotlin.coroutines,
        libs.test.mockito.core,
        libs.test.mockito.kotlin,
    )
}
