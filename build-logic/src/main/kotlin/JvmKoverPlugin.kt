/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupJunit

/**
 * Android 프레임워크에 의존적이지 않은 순수한 kover 모듈과
 * Junit4 테스트 환경을 구성합니다.
 */
internal class JvmKoverPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins(libs.findPlugin("kover").get().get().pluginId)
            dependencies {
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get(),
                )
            }
        }
    }
}
