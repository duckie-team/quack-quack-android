import com.android.build.gradle.LibraryExtension
import land.sungbin.duckie.quackquack.plugin.androidTestImplementations
import land.sungbin.duckie.quackquack.plugin.configureCompose
import land.sungbin.duckie.quackquack.plugin.debugImplementations
import land.sungbin.duckie.quackquack.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [LibraryComposeUiTestPlugin.kt] created by Ji Sungbin on 22. 8. 17. 오후 11:39
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

internal class LibraryComposeUiTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()

            configureCompose(commonExtension = extension)

            extension.apply {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                debugImplementations(libs.findLibrary("test-compose-manifest").get())
                androidTestImplementations(libs.findLibrary("test-compose-junit").get())
            }
        }
    }
}

