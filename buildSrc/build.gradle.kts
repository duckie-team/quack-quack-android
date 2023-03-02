/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

private val buildLogicPrefix = "quackquack"

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.dokka)
}

gradlePlugin {
    val pluginClasses = listOf(
        "AndroidApplicationPlugin",
        "AndroidLibraryPlugin",
        "AndroidLintPlugin",
        "AndroidComposePlugin",
        "AndroidComposeMetricsPlugin",
        "JvmKotlinPlugin",
        "JvmJUnitPlugin",
        "JvmDokkaPlugin",
        "KotlinExplicitApiPlugin",
    )

    plugins {
        pluginClasses.forEach { pluginClass ->
            autoRegister(pluginClass)
        }
    }
}

dependencies {
    implementation(libs.gradle.android)
    implementation(libs.gradle.publish.maven)
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.dokka.base)
    implementation(libs.kotlin.dokka.plugin)
}

fun NamedDomainObjectContainer<PluginDeclaration>.autoRegister(className: String) {
    register(className.removeSuffix("Plugin")) {
        id = "$buildLogicPrefix.buildlogic.$className"
        implementationClass = className
    }
}

