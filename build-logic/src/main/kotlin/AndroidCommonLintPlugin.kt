/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.apis
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.compileOnlys
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupJunit
import team.duckie.quackquack.convention.setupLint

/**
 * Android 프레임워크에 의존적인 린트 개발 환경을 구성합니다.
 *
 * 의존성 순환 참조를 방지하기 위해
 * 린트 개발에 필요한 필수 의존성만을 사용하여 구성합니다.
 */
internal class AndroidCommonLintPlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            applyPlugins(
                PluginEnum.JavaLibrary,
                PluginEnum.KotlinCore,
                PluginEnum.AndroidLint,
            )

            extensions.configure<JavaPluginExtension> {
                val version = JavaVersion.VERSION_11
                sourceCompatibility = version
                targetCompatibility = version
            }

            dependencies {
                apis(
                    libs.findLibrary("kotlin-metadata").get(),
                )
                compileOnlys(
                    libs.findLibrary("kotlin-stdlib").get(),
                )
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get(),
                )
                setupLint(
                    core = libs.findLibrary("lint-api").get(),
                    test = libs.findBundle("test-lint").get(),
                )
            }
        }
    }
}
