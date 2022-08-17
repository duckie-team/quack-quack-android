/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ApplicationJacocoPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureJacocoForOnceCoverage
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupJunit

internal class ApplicationJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.Jacoco,
            )

            configureJacocoForOnceCoverage()

            dependencies {
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get(),
                )
            }
        }
    }
}
