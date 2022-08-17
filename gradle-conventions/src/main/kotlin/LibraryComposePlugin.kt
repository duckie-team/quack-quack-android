/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [LibraryComposePlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import team.duckie.quackquack.convention.configureCompose
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupCompose

internal class LibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()

            configureCompose(
                commonExtension = extension,
            )

            dependencies {
                setupCompose(
                    core = libs.findBundle("compose-core").get(),
                    debug = libs.findBundle("compose-debug").get()
                )
            }
        }
    }
}
