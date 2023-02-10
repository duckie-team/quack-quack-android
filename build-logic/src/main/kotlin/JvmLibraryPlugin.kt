/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import team.duckie.quackquack.convention.ApplicationConstants
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins

/**
 * Android 프레임워크에 의존적이지 않은 순수한 자바 라이브러리 모듈을 구성합니다.
 */
internal class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            applyPlugins(
                PluginEnum.JavaLibrary,
                PluginEnum.KotlinCore,
            )

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = ApplicationConstants.javaVersion
                targetCompatibility = ApplicationConstants.javaVersion
            }
        }
    }
}
