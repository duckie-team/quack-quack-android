/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `buildlogic-jvm-kotlin`
    `buildlogic-jvm-junit`
    alias(libs.plugins.kotlin.ksp)
}

dependencies {
    ksp(libs.google.autoservice.ksp.processor)
    implementations(
        libs.kotlin.ksp.api,
        libs.kotlin.ksp.internal, // needs for `this as KSValueParameterImpl`
        libs.kotlin.embeddable.compiler, // needs for `(this as KSValueParameterImpl).ktParameter.defaultValue`
        libs.kotlin.kotlinpoet.ksp,
        libs.google.autoservice.annotation,
        projects.commonPoet,
    )
    testImplementations(
        libs.test.strikt,
        libs.test.kotlin.compilation.ksp,
    )
}
