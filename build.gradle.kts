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

val quackVersioningTaskTypeArgument = "type"
val quackVersioningTaskBumpArgument = "bump"
val quackInitializeVersion = "0.1.0"

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

    tasks.create("versioning") {
        val type = getPropertyOrNull<String, VersioningType>(quackVersioningTaskTypeArgument) { type ->
            VersioningType.values().find { enum ->
                enum.name.equals(type, ignoreCase = true)
            } ?: error(
                """
                The value of the type property is invalid. (input: $type)
                Possible types: ${VersioningType.values().joinToString()}
                """.trimIndent(),
            )
        } ?: return@create
        val bump = getPropertyOrNull<String, BumpType>(quackVersioningTaskBumpArgument) { bump ->
            BumpType.values().find { enum ->
                enum.name.equals(bump, ignoreCase = true)
            } ?: error(
                """
                The value of the bump property is invalid. (input: $bump)
                Possible bumps: ${BumpType.values().joinToString()}
                """.trimIndent(),
            )
        }

        val versionFile = File(projectDir, "version.txt")
        when (type) {
            VersioningType.Init -> {
                versionFile.createNewFile()
                versionFile.writeText(quackInitializeVersion)
            }
            VersioningType.Bump -> {
                checkNotNull(bump) {
                    "The `VersioningType = Bump` was entered, but no bump target was given."
                }
                checkVersionFileIsValid(versionFile)
                val newVersion = versionFile.bump(bump)
                versionFile.writeText(newVersion)
            }
        }
    }
}

tasks.register(name = "cleanAll", type = Delete::class) {
    allprojects.map(Project::getBuildDir).forEach(::delete)
}

enum class VersioningType {
    Init, Bump,
}

enum class BumpType {
    Major, Minor, Patch,
}

inline fun <reified P, T> Project.getPropertyOrNull(key: String, parse: (value: P) -> T): T? {
    return ((properties[key] ?: return null) as P).let(parse)
}

fun File.bump(type: BumpType): String {
    val now = readText()
    val (major, minor, patch) = now.split(".").map(String::toInt)
    return when (type) {
        BumpType.Major -> "${major + 1}.$minor.$patch"
        BumpType.Minor -> "$major.${minor + 1}.$patch"
        BumpType.Patch -> "$major.$minor.${patch + 1}"
    }
}

fun checkVersionFileIsValid(file: File) {
    if (!file.exists() || !file.isFile) {
        error(
            """
            There is no version.txt file in the project path. 
            Try `./gradlew :project:versioning -Ptype=init` for version configuration.
            """.trimIndent(),
        )
    }
}
