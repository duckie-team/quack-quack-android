/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

plugins {
    `buildlogic-jvm-kotlin`
    `buildlogic-quack-mavenpublishing`
    alias(libs.plugins.kotlin.ksp)
}

dependencies {
    compileOnly(libs.kotlin.embeddable.compiler)
    implementations(libs.google.autoservice.annotation)
    ksp(libs.google.autoservice.ksp.processor)
}
