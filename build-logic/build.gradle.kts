/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.dsl)
    alias(libs.plugins.kotlin.detekt)
    alias(libs.plugins.kotlin.ktlint)
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

detekt {
    parallel = true
    buildUponDefaultConfig = true
    toolVersion = libs.versions.kotlin.detekt.get()
    config.setFrom(files("${rootDir.parentFile}/detekt-config.yml"))
}

ktlint {
    version.set(libs.versions.kotlin.ktlint.source.get())
    verbose.set(true)
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
    testImplementations(
        libs.test.kotest.framework,
        libs.test.strikt,
    )
    detektPlugins(libs.detekt.plugin.formatting)
}

// Pair<ClassName, PluginName>
fun NamedDomainObjectContainer<PluginDeclaration>.pluginRegister(data: Pair<String, String>) {
    val (className, pluginName) = data
    register(pluginName) {
        implementationClass = className
        id = "quackquack.plugin.$pluginName"
    }
}
