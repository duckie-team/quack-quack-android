/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AndroidLibraryComposeUiTestPlugin.kt] created by Ji Sungbin on 22. 8. 18. 오전 2:25
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import team.duckie.quackquack.convention.androidTestImplementations
import team.duckie.quackquack.convention.configureCompose
import team.duckie.quackquack.convention.debugImplementations
import team.duckie.quackquack.convention.libs

/**
 * Android 프레임워크의 Library 환경에서 Jetpack Compose 의 UI 테스트를 진행할 준비를 합니다.
 */
internal class AndroidLibraryComposeUiTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()

            configureCompose(
                commonExtension = extension,
            )

            extension.apply {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                debugImplementations(
                    libs.findLibrary("test-compose-manifest").get(),
                )
                androidTestImplementations(
                    libs.findLibrary("test-compose-junit").get(),
                )
            }
        }
    }
}

