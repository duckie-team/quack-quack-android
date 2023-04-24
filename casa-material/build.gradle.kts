/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-android-library`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
}

android {
    namespace = "team.duckie.quackquack.casa.material"
}

dependencies {
    implementations(
        libs.kotlin.collections.immutable,
        libs.compose.runtime,
    )
}
