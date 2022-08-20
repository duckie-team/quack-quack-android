/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AndroidLintPlugin.kt] created by Ji Sungbin on 22. 8. 16. 오후 7:29
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.compileOnlys
import team.duckie.quackquack.convention.implementations
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupJunit
import team.duckie.quackquack.convention.setupLint
import team.duckie.quackquack.convention.testImplementations

/**
 * Android 프레임워크에 의존적인 린트 개발 환경을 구성합니다.
 *
 * [AndroidCommonLintPlugin] 와 달리
 * 린트 개발에 도움이 될 수 있는 추가적인 다른 의존성들도 같이 구성합니다.
 */
internal class AndroidLintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.JavaLibrary,
                PluginEnum.PureKotlin,
                PluginEnum.AndroidLint,
            )

            extensions.configure<JavaPluginExtension> {
                val version = JavaVersion.VERSION_1_8
                sourceCompatibility = version
                targetCompatibility = version
            }

            dependencies {
                dependencies {
                    compileOnlys(
                        libs.findLibrary("kotlin-stdlib").get(),
                    )
                    implementations(
                        project(":common-lint"),
                    )
                    testImplementations(
                        project(":common-lint-test"),
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
}
