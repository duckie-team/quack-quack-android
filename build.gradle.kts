/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.kotlin.detekt)
    alias(libs.plugins.kotlin.ktlint)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.android)
        classpath(libs.kotlin.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    apply {
        plugin(rootProject.libs.plugins.kotlin.detekt.get().pluginId)
        plugin(rootProject.libs.plugins.kotlin.ktlint.get().pluginId)
    }

    afterEvaluate {
        extensions.configure<DetektExtension> {
            parallel = true
            buildUponDefaultConfig = true
            toolVersion = libs.versions.kotlin.detekt.get()
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        extensions.configure<KtlintExtension> {
            version.set(rootProject.libs.versions.kotlin.ktlint.source.get())
            android.set(true)
            outputToConsole.set(true)
            additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.OptIn",
                    "-opt-in=kotlin.RequiresOptIn",
                )
            }
        }
    }
}

tasks.register(name = "cleanAll", type = Delete::class) {
    allprojects.map(Project::getBuildDir).forEach(::delete)
}
