/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `buildlogic-jvm-kotlin`
    `buildlogic-test-kotest`
    `buildlogic-quack-mavenpublishing`
    alias(libs.plugins.kotlin.ksp)
}

ksp {
    arg("autoserviceKsp.verify", "true")
    arg("autoserviceKsp.verbose", "true")
}

dependencies {
    compileOnly(libs.kotlin.embeddable.compiler)
    implementations(
        libs.google.autoservice.annotation,
        libs.kotlin.kotlinpoet.core,
        projects.sugarMaterial,
        // QuackArtifactType.CoreSugarProcessorKotlinc.setInternal().asArtifactFqPath(project),
    )
    ksp(libs.google.autoservice.ksp.processor)
    testImplementations(
        libs.test.strikt,
        libs.test.kotlin.compilation.core,
    )
}

quack {
    type = QuackArtifactType.CoreSugarProcessorKotlinc.setInternal()
}
