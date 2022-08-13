@file:Suppress("unused")

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.configureJacoco
import land.sungbin.duckie.quackquack.plugin.libs
import land.sungbin.duckie.quackquack.plugin.setupJunit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ApplicationJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(PluginEnum.Jacoco)
            val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
            configureJacoco(extension)
            dependencies {
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get()
                )
            }
        }
    }
}