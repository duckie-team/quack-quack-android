/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(PluginEnum.AndroidLibrary)
    id(PluginEnum.AndroidLibraryCompose)
    id(PluginEnum.AndroidLibraryComposeUiTest)
    id(PluginEnum.JvmKover)
    id(PluginEnum.JvmDokka)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "team.duckie.quackquack.ui"

    buildTypes {
        sourceSets.getByName("debug") {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        sourceSets.getByName("release") {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

dependencies {
    implementations(
        projects.common,
        libs.util.profileinstaller,
        libs.compose.material,
        libs.compose.glide,
        libs.kotlin.collections.immutable,
    )
    testImplementation(libs.test.parameter.injector)
    customLints(
        projects.lintCore,
        projects.lintCompose,
    )
}
