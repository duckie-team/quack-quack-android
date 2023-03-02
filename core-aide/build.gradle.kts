/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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
