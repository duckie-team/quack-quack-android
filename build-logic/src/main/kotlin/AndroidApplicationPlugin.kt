/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
)

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import team.duckie.quackquack.convention.ApplicationConstants
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureApplication
import team.duckie.quackquack.convention.getPlaygroundVersion

/**
 * Android 프레임워크의 Application 환경을 구성합니다.
 */
internal class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            val (versionName, versionCode) = getPlaygroundVersion()

            applyPlugins(
                PluginEnum.AndroidApplication,
                PluginEnum.KotlinAndroid,
            )

            extensions.configure<BaseAppModuleExtension> {
                configureApplication(
                    extension = this,
                )

                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    this.versionName = versionName
                    this.versionCode = versionCode
                }
            }
        }
    }
}
