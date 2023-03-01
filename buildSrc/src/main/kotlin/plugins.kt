/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

object Plugins {
    const val JavaLibrary = "java-library"

    const val KotlinCore = "kotlin"
    const val KotlinAndroid = "kotlin-android"

    const val AndroidApplication = "com.android.application"
    const val AndroidLibrary = "com.android.library"
    const val AndroidLint = "com.android.lint"
}

val PluginDependenciesSpec.`android-application`: PluginDependencySpec
    get() = id(Plugins.AndroidApplication)

val PluginDependenciesSpec.`android-library`: PluginDependencySpec
    get() = id(Plugins.AndroidLibrary)
