/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `buildlogic-jvm-kotlin`
    alias(libs.plugins.kotlin.ksp)
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:${libs.versions.kotlin.core.get()}")
    implementations(
        libs.google.autoservice.annotation,
        libs.kotlin.kotlinpoet.core,
    )
    ksp(libs.google.autoservice.ksp.processor)
}
