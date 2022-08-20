/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:49
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.dokka)
    id(PluginEnum.ProjectJacoco)
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
    val duckieOrange = "#ff8300"

    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        detekt {
            buildUponDefaultConfig = true
            toolVersion = libs.versions.detekt.get()
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.OptIn",
                    "-Xopt-in=kotlin.RequiresOptIn",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$projectPath/report/compose-metrics",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$projectPath/report/compose-reports",
                )
            }
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }

    if (pluginManager.hasPlugin(rootProject.libs.plugins.dokka.get().pluginId)) {
        tasks.withType<DokkaTaskPartial>().configureEach {
            outputDirectory.set(file("$rootDir/documents/dokka"))
            suppressInheritedMembers.set(true)

            dokkaSourceSets.configureEach {
                includeNonPublic.set(true)
                jdkVersion.set(11)
            }

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                footerMessage =
                    """made with <span style="color: $duckieOrange;">❤</span> by <a href="https://duckie.team/">Duckie</a>"""
            }
        }

        tasks.dokkaHtmlMultiModule.configure {
            moduleName.set("Duckie-QuackQuack")
            outputDirectory.set(file("$rootDir/documents/dokka"))

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                footerMessage =
                    """made with <span style="color: $duckieOrange;">❤</span> by <a href="https://duckie.team/">Duckie</a>"""
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

tasks.register("cleanAll", Delete::class) {
    allprojects.map { it.buildDir }.forEach(::delete)
}

apply(from = "gradle/projectDependencyGraph.gradle")
