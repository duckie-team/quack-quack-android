/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `buildlogic-jvm-kotlin`
    `buildlogic-jvm-junit`
    `buildlogic-quack-mavenpublishing`
    alias(libs.plugins.kotlin.ksp)
}

dependencies {
    compileOnly(libs.kotlin.embeddable.compiler)
    implementations(
        libs.google.autoservice.annotation,
        libs.kotlin.kotlinpoet.core,
        projects.coreSugarMaterial,
        // "team.duckie.quack:${QuackArtifactType.CoreSugarMaterial.forceInternal().asArtifactId()}:0.1.1"
    )
    ksp(libs.google.autoservice.ksp.processor)
    testImplementations(
        libs.test.strikt,
        libs.test.kotlin.compilation.core,
    )
}

quack {
    type = QuackArtifactType.CoreSugarProcessorKotlinc.forceInternal()
}
