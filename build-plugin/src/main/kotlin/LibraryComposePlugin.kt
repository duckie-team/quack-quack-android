@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import land.sungbin.duckie.quackquack.plugin.configureCompose
import land.sungbin.duckie.quackquack.plugin.libs
import land.sungbin.duckie.quackquack.plugin.setupCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class LibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureCompose(extension)
            dependencies {
                setupCompose(
                    core = libs.findBundle("compose-core").get(),
                    debug = libs.findBundle("compose-debug").get()
                )
            }
        }
    }
}