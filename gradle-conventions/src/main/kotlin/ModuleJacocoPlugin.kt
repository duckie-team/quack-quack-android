/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ModuleJacocoPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:51
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
import team.duckie.quackquack.convention.configureJacocoForModule
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupJunit

/**
 * Android 프레임워크에 의존적이지 않은 순수한 Jacoco 모듈을 구성합니다.
 *
 * 모듈 그레이들 단위에서 사용되므로 [configureJacocoForModule] 를 사용합니다.
 */
internal class ModuleJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.Jacoco,
            )

            configureJacocoForModule()

            dependencies {
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get(),
                )
            }
        }
    }
}
