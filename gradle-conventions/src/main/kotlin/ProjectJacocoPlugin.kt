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
import team.duckie.quackquack.convention.configureJacocoForProject

/**
 * Android 프레임워크에 의존적이지 않은 순수한 Jacoco 모듈을 구성합니다.
 *
 * 프로젝트 그레이들 단위에서 사용되므로 [configureJacocoForProject] 를 사용합니다.
 */
class ProjectJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.Jacoco,
            )

            configureJacocoForProject()
        }
    }
}
