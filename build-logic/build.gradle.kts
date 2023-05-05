/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.dependency.handler.extensions)
}

gradlePlugin {
    val pluginClasses = listOf(
        "AndroidApplicationPlugin" to "android-application",
        "AndroidLibraryPlugin" to "android-library",
        "AndroidGmdPlugin" to "android-gmd",
        "AndroidLintPlugin" to "android-lint",
        "AndroidComposePlugin" to "android-compose",
        "AndroidComposeMetricsPlugin" to "android-compose-metrics",
        "JvmKotlinPlugin" to "jvm-kotlin",
        "TestJUnitPlugin" to "test-junit",
        "TestKotestPlugin" to "test-kotest",
        "KotlinExplicitApiPlugin" to "kotlin-explicit-api",
        "QuackMavenPublishingPlugin" to "quack-publishing",
    )

    plugins {
        pluginClasses.forEach { pluginClass ->
            pluginRegister(pluginClass)
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
    getByName("test").java.srcDirs("src/test/kotlin")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    implementations(
        libs.gradle.android,
        libs.kotlin.gradle,
        libs.gradle.publish.maven,
    )
    testImplementation(libs.test.kotest.framework)
}

// Pair<ClassName, PluginName>
fun NamedDomainObjectContainer<PluginDeclaration>.pluginRegister(data: Pair<String, String>) {
    val (className, pluginName) = data
    register(pluginName) {
        implementationClass = className
        id = "quackquack.plugin.$pluginName"
    }
}
