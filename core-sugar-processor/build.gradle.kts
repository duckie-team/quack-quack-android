/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `buildlogic-jvm-kotlin`
    alias(libs.plugins.kotlin.ksp)
}

dependencies {
    ksp(libs.google.autoservice.ksp.processor)
    implementations(
        libs.kotlin.ksp.api,
        libs.kotlin.kotlinpoet,
        libs.google.autoservice.annotation,
    )
    testImplementation(libs.test.kotlin.compile)
}
