import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [JvmLibraryPlugin.kt] created by Ji Sungbin on 22. 8. 18. 오후 8:02
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/**
 * Android 프레임워크에 의존적이지 않은 순수한 자바 라이브러리 모듈을 구성합니다.
 */
internal class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.JavaLibrary,
                PluginEnum.PureKotlin,
            )

            extensions.configure<JavaPluginExtension> {
                val version = JavaVersion.VERSION_1_8
                sourceCompatibility = version
                targetCompatibility = version
            }
        }
    }
}
