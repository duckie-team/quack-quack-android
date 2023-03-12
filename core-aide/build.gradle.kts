/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

plugins {
    `buildlogic-jvm-kotlin`
    `kotlin-kapt`
    `buildlogic-android-lint`
    `buildlogic-jvm-junit`
}

dependencies {
    compileOnly(libs.google.autoservice.annotation)
    kapt(libs.google.autoservice.standard.processor)
    testImplementation(libs.test.strikt)
}
