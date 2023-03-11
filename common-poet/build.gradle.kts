/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    `buildlogic-jvm-kotlin`
    `buildlogic-kotlin-explicitapi`
}

dependencies {
    implementations(
        libs.kotlin.ksp.api,
        libs.kotlin.kotlinpoet.core,
    )
}
