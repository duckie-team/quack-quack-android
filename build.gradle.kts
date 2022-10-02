/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)
    alias(libs.plugins.gradle.maven.publish.asProvider())
}

koverMerged {
    enable()
    xmlReport {
        reportFile.set(file("$rootDir/report/test-coverage/report.xml"))
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.build.gradle)
        classpath(libs.build.kotlin)
        classpath(libs.build.dokka.base)
        classpath(libs.build.oss.license)
        classpath(libs.build.gradle.maven.publish)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        detekt {
            parallel = true
            buildUponDefaultConfig = true
            toolVersion = libs.versions.detekt.get()
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.OptIn",
                    "-opt-in=kotlin.RequiresOptIn",
                    "Xsam-conversions=class", // https://twitter.com/ZacSweers/status/1520399593577582593?s=20&t=cwZyDPyT9HQGGIK1zxkybQ
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$rootDir/report/compose-metrics",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$rootDir/report/compose-reports",
                )
            }
        }
    }

    if (pluginManager.hasPlugin(rootProject.libs.plugins.dokka.get().pluginId)) {
        tasks.dokkaHtmlMultiModule.configure {
            moduleName.set("Duckie-QuackQuack")
            outputDirectory.set(file("$rootDir/documents/dokka"))

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                footerMessage =
                    """made with <span style="color: #ff8300;">❤</span> by <a href="https://duckie.team/">Duckie-QuackQuack</a>"""
                customAssets = listOf(file("assets/logo-icon.svg"))
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

    configure<KtlintExtension> {
        version.set(rootProject.libs.versions.ktlint.source.get())
        android.set(true)
        outputToConsole.set(true)
        additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    }
}

tasks.register(
    name = "cleanAll",
    type = Delete::class
) {
    allprojects.map { project ->
        project.buildDir
    }.forEach(::delete)
}

apply(
    from = "gradle/projectDependencyGraph.gradle",
)
