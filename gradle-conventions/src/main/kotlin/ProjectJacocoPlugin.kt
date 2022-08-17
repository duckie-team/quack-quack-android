/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ProjectJacocoPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.configureJacocoForAllCoverage
import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.Jacoco,
            )

            configureJacocoForAllCoverage()
        }
    }
}
