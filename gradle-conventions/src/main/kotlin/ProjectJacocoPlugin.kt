/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ProjectJacocoPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureJacocoForAllCoverage

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
