/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.spotless)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.build.gradle)
        classpath(libs.build.kotlin)
        classpath(libs.build.dokka)
    }
}

allprojects {
    val projectPath = rootProject.file(".").absolutePath

    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        project.apply("$rootDir/gradle/common.gradle")

        detekt {
            buildUponDefaultConfig = true
            toolVersion = libs.versions.detekt.get()
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        tasks.withType<Detekt>().configureEach {
            jvmTarget = ApplicationConstants.jvmTarget
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.OptIn",
                    "-Xopt-in=kotlin.RequiresOptIn"
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$projectPath/report/compose-metrics"
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$projectPath/report/compose-reports"
                )
            }
        }
    }

    apply {
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }
}

subprojects {
    // https://github.com/gradle/gradle/issues/4823#issuecomment-715615422
    @Suppress("UnstableApiUsage")
    if (
        gradle.startParameter.isConfigureOnDemand &&
        buildscript.sourceFile?.extension?.toLowerCase() == "kts" &&
        parent != rootProject
    ) {
        generateSequence(parent) { project ->
            project.parent.takeIf { parent ->
                parent != rootProject
            }
        }.forEach { project ->
            evaluationDependsOn(project.path)
        }
    }

    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(rootProject.file("license.kt"))
            trimTrailingWhitespace()
        }
        format("kts") {
            target("**/*.kts")
            licenseHeaderFile(rootProject.file("license.kt"), "(^(?![\\/ ]\\*).*$)")
            trimTrailingWhitespace()
        }
    }
}

tasks.register("cleanAll", Delete::class) {
    allprojects.map { it.buildDir }.forEach(::delete)
}

apply {
    from("gradle/projectDependencyGraph.gradle")
}
