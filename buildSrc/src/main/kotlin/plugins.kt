/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

@PublishedApi
internal const val BuildLogicPrefix = "quackquack"

@PublishedApi
internal object Plugins {
    const val JavaLibrary = "java-library"

    const val KotlinJvm = "org.jetbrains.kotlin.jvm"
    const val KotlinAndroid = "org.jetbrains.kotlin.android"

    const val AndroidApplication = "com.android.application"
    const val AndroidLibrary = "com.android.library"
    const val AndroidLint = "com.android.lint"
}

@PublishedApi
internal inline fun generatePluginRegisterId(id: String): String {
    return "$BuildLogicPrefix.buildlogic.$id"
}

inline val PluginDependenciesSpec.`android-application`: PluginDependencySpec
    get() = id(Plugins.AndroidApplication)

inline val PluginDependenciesSpec.`android-library`: PluginDependencySpec
    get() = id(Plugins.AndroidLibrary)

inline val PluginDependenciesSpec.`buildlogic-android-application`: PluginDependencySpec
    get() = id(generatePluginRegisterId(AndroidApplicationPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-android-library`: PluginDependencySpec
    get() = id(generatePluginRegisterId(AndroidLibraryPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-android-lint`: PluginDependencySpec
    get() = id(generatePluginRegisterId(AndroidLintPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-android-compose`: PluginDependencySpec
    get() = id(generatePluginRegisterId(AndroidComposePlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-android-compose-metrics`: PluginDependencySpec
    get() = id(generatePluginRegisterId(AndroidComposeMetricsPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-jvm-kotlin`: PluginDependencySpec
    get() = id(generatePluginRegisterId(JvmKotlinPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-test-junit`: PluginDependencySpec
    get() = id(generatePluginRegisterId(TestJUnitPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-test-kotest`: PluginDependencySpec
    get() = id(generatePluginRegisterId(TestKotestPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-jvm-dokka`: PluginDependencySpec
    get() = id(generatePluginRegisterId(JvmDokkaPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-kotlin-explicitapi`: PluginDependencySpec
    get() = id(generatePluginRegisterId(KotlinExplicitApiPlugin::class.simpleName!!))

inline val PluginDependenciesSpec.`buildlogic-quack-mavenpublishing`: PluginDependencySpec
    get() = id(generatePluginRegisterId((QuackMavenPublishingPlugin::class.simpleName!!)))
