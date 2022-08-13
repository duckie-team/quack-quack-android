/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ApplicationPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import land.sungbin.duckie.quackquack.plugin.ApplicationConstants
import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.configureApplication
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(PluginEnum.Application, PluginEnum.Kotlin)
            extensions.configure<BaseAppModuleExtension> {
                configureApplication(this)
                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    versionCode = ApplicationConstants.versionCode
                    versionName = ApplicationConstants.versionName
                }
            }
        }
    }
}