/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ApplicationComposePlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import land.sungbin.duckie.quackquack.plugin.configureCompose
import land.sungbin.duckie.quackquack.plugin.libs
import land.sungbin.duckie.quackquack.plugin.setupCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class ApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<BaseAppModuleExtension>()

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
