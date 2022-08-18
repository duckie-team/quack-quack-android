/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AndroidApplicationPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import team.duckie.quackquack.convention.ApplicationConstants
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureApplication

internal class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.Application,
                PluginEnum.AndroidKotlin,
            )

            extensions.configure<BaseAppModuleExtension> {
                configureApplication(
                    commonExtension = this,
                )

                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    versionCode = ApplicationConstants.versionCode
                    versionName = ApplicationConstants.versionName
                }
            }
        }
    }
}
