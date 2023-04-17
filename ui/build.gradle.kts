/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME as kotlinCompilerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
    `buildlogic-android-compose-metrics`
    `buildlogic-jvm-dokka`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
    `buildlogic-test-junit`
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.test.roborazzi)
}

tasks.withType<KotlinCompile> {
    val sugarProcessorKotlinCompilerPluginId = "team.duckie.quackquack.sugar.processor"
    val sugarPath = "$projectDir/src/main/kotlin/team/duckie/quackquack/ui/sugar"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$sugarProcessorKotlinCompilerPluginId:sugarPath=$sugarPath",
        )
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$sugarProcessorKotlinCompilerPluginId:poet=$sugarPoet",
        )
    }
}

android {
    namespace = "team.duckie.quackquack.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

ksp {
    arg("AidePath", "$rootDir/aide/src/main/kotlin/rule")
    arg("CasaPath", "$rootDir/catalog/src/main/kotlin/team/duckie/quackquack/catalog")
}

dependencies {
    api(projects.animation.orArtifact())
    implementations(
        libs.compose.coil,
        libs.compose.material,
        projects.util.orArtifact(),
        projects.runtime.orArtifact(),
        projects.casaAnnotation.orArtifact(),
        projects.sugarMaterial.orArtifact(),
        projects.aideAnnotation.orArtifact(),
    )

    androidTestImplementations(
        libs.test.strikt,
        libs.test.junit.compose,
        libs.bundles.test.mockito,
        projects.screenshotMatcher,
    )
    testImplementations(
        libs.test.robolectric,
        libs.test.junit.compose,
        libs.bundles.test.roborazzi,
    )

    kotlinCompilerPlugin(projects.sugarProcessor.orArtifact())
    lintPublish(projects.aide.orArtifact())

    if (!useArtifact) {
        ksps(
            projects.aideProcessor,
            projects.casaProcessor,
        )
    }
}
