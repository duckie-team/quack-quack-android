/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

plugins {
    `buildlogic-jvm-kotlin` // fix order ("compileOnly" configuration missing)
    `buildlogic-android-lint`
    `buildlogic-test-kotest`
    `buildlogic-quack-mavenpublishing`
    alias(libs.plugins.kotlin.ksp)
}

dependencies {
    implementation(libs.google.autoservice.annotation)
    ksp(libs.google.autoservice.ksp.processor)
    testImplementation(libs.test.strikt)
}
