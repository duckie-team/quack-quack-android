/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

private val buildLogicPrefix = "quackquack"

plugins {
    `kotlin-dsl`
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

gradlePlugin {
    val pluginClasses = listOf(
        "AndroidApplicationPlugin",
        "AndroidLibraryPlugin",
        "AndroidGmdPlugin",
        "AndroidLintPlugin",
        "AndroidComposePlugin",
        "AndroidComposeMetricsPlugin",
        "JvmKotlinPlugin",
        "TestJUnitPlugin",
        "TestKotestPlugin",
        "KotlinExplicitApiPlugin",
        "QuackMavenPublishingPlugin",
    )

    plugins {
        pluginClasses.forEach { pluginClass ->
            pluginAutoRegister(pluginClass)
        }
    }
}

dependencies {
    implementation(libs.gradle.android)
    implementation(libs.kotlin.gradle)
    implementation(libs.gradle.publish.maven)
    testImplementation(libs.test.strikt)
    testImplementation(libs.test.kotest.framework)
}

fun NamedDomainObjectContainer<PluginDeclaration>.pluginAutoRegister(className: String) {
    register(className.removeSuffix("Plugin")) {
        id = "$buildLogicPrefix.buildlogic.$className"
        implementationClass = className
    }
}
