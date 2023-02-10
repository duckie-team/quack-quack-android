/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.dokka)
}

dependencies {
    implementation(libs.gradle.android)
    implementation(libs.gradle.publish.maven)
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.dokka.base)
    implementation(libs.kotlin.dokka.plugin)
}
