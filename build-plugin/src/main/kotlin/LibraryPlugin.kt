@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import land.sungbin.duckie.quackquack.plugin.ApplicationConstants
import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.configureApplication
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(PluginEnum.Library, PluginEnum.Kotlin)
            extensions.configure<LibraryExtension> {
                configureApplication(this)
                defaultConfig.targetSdk = ApplicationConstants.targetSdk
            }
        }
    }
}