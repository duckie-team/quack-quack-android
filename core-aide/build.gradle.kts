/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    `java-library`
    `kotlin-kapt`
}

GradleInstallation.with(project) {
    kotlin()
    junit()
    lint(plugin = false)
}

dependencies {
    compileOnly(libs.google.autoservice.annotation)
    kapt(libs.google.autoservice.standard.processor)
    testImplementation(libs.test.strikt)
}
