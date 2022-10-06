/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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
    type = Delete::class,
) {
    allprojects.map { project ->
        project.buildDir
    }.forEach(::delete)
}

enum class VersionType {
    Major,
    Minor,
    Patch;
}

enum class BumpTarget {
    Playground,
    LintCore,
    LintQuack,
    LintCompose,
    UiComponents;
}

fun Project.getVersionPath(
    target: BumpTarget,
) = when (target) {
    BumpTarget.Playground -> "$rootDir/playground-version.txt"
    BumpTarget.UiComponents -> "$rootDir/quackquack-version/ui-components.txt"
    BumpTarget.LintCore -> "$rootDir/quackquack-version/lint-core.txt"
    BumpTarget.LintQuack -> "$rootDir/quackquack-version/lint-quack.txt"
    BumpTarget.LintCompose -> "$rootDir/quackquack-version/lint-compose.txt"
}

fun Project.bumpVersion(
    type: VersionType,
    target: BumpTarget,
): String {
    val versionFile = File(
        getVersionPath(
            target = target,
        )
    )
    val lines = versionFile.readLines().toMutableList()
    when (type) {
        VersionType.Major -> {
            val major = lines[0].split("=")[1].toInt()
            lines[0] = "major=${major + 1}"
            lines[1] = "minor=0"
            lines[2] = "patch=0"
        }
        VersionType.Minor -> {
            val minor = lines[1].split("=")[1].toInt()
            lines[1] = "minor=${minor + 1}"
            lines[2] = "patch=0"
        }
        VersionType.Patch -> {
            val patch = lines[2].split("=")[1].toInt()
            lines[2] = "patch=${patch + 1}"
        }
    }
    if (target == BumpTarget.Playground) {
        val code = lines[3].split("=")[1].toInt()
        lines[3] = "code=${code + 1}"
    }
    return lines.joinToString(
        separator = "\n",
    )
}

tasks.create(
    name = "bumpVersion",
) {
    val type = (properties["type"] ?: return@create).let { type ->
        VersionType.valueOf(type.toString())
    }
    val target = (properties["target"] ?: return@create).let { target ->
        BumpTarget.valueOf(target.toString())
    }
    val version = bumpVersion(
        type = type,
        target = target,
    )
    val versionFile = File(
        getVersionPath(
            target = target,
        )
    )
    versionFile.writeText(
        text = version,
    )
}

apply(
    from = "gradle/projectDependencyGraph.gradle",
)
