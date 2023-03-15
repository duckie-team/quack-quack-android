/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME as kotlinCompilerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `buildlogic-android-application`
    `buildlogic-android-compose`
}

tasks.withType<KotlinCompile> {
    val sugarProcessorKotlinCompilerPluginId = "team.duckie.quackquack.sugar.processor.kotlinc"
    val sugarPath = "$projectDir/src/main/kotlin/team/duckie/quackquack/core/component/sugar"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$sugarProcessorKotlinCompilerPluginId:sugarPath=$sugarPath",
        )
    }
}

android {
    namespace = "team.duckie.quackquack.playground"
}

dependencies {
    implementations(
        libs.compose.activity,
        projects.core,
    )

    kotlinCompilerPlugin(projects.coreSugarProcessorKotlinc)
}
