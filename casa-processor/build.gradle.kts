/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
    quackquack("jvm-kotlin")
    quackquack("test-kotest")
    quackquack("quack-publishing")
    alias(libs.plugins.kotlin.ksp)
}

ksp {
    arg("autoserviceKsp.verify", "true")
    arg("autoserviceKsp.verbose", "true")
}

dependencies {
    ksp(libs.google.autoservice.ksp.processor)
    implementations(
        libs.kotlin.ksp.api,
        libs.kotlin.kotlinpoet.core,
        libs.kotlin.collections.immutable,
        libs.google.autoservice.annotation,
    )
    testImplementations(
        libs.test.strikt,
        libs.test.kotlin.compilation.ksp,
    )
}
