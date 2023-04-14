/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME as kotlinCompilerPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// -- FIX LOCATION
private val poet = true

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-android-compose-metrics`
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.test.paparazzi)
}

tasks.withType<DokkaTask> {
    moduleName.set("QuackQuack-Core")
    // notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/1217")
}

tasks.withType<KotlinCompile> {
    val sugarProcessorKotlinCompilerPluginId = "team.duckie.quackquack.sugar.processor.kotlinc"
    val sugarPath = "$projectDir/src/main/kotlin/team/duckie/quackquack/core/component/sugar"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$sugarProcessorKotlinCompilerPluginId:sugarPath=$sugarPath",
        )
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$sugarProcessorKotlinCompilerPluginId:poet=$poet",
        )
    }
}

android {
    namespace = "team.duckie.quackquack.core"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

ksp {
    arg("AidePath", "$rootDir/core-aide/src/main/kotlin/rule")
}

dependencies {
    api(libs.kotlin.collections.immutable)
    implementations(
        libs.compose.uiutil,
        libs.compose.coil,
        libs.compose.material,
        projects.util,
        projects.runtime,
        projects.material,
        projects.aideAnnotation,
        projects.sugarMaterial,
        // QuackArtifactType.CoreAideAnnotation.setInternal().asArtifactFqPath(project),
        // QuackArtifactType.CoreSugarMaterial.setInternal().asArtifactFqPath(project),
    )

    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
        libs.bundles.test.mockito,
        projects.screenshotMatcher,
    )

    kotlinCompilerPlugin(
         projects.sugarProcessor,
        // QuackArtifactType.CoreSugarProcessorKotlinc.setInternal().asArtifactFqPath(project),
    )
    ksp(projects.aideProcessor)

    // lintPublish(projects.coreAide)
}

quack {
    type = QuackArtifactType.Core.setInternal()
}
