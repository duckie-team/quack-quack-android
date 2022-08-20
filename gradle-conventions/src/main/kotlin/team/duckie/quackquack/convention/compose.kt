/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [compose.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

/**
 * Jetpack Compose 를 사용하기 위한 그레이들 환경을 설정합니다.
 *
 * @param commonExtension 설정할 그레이들의 [CommonExtension]
 */
internal fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose-compiler").get().toString()
        }
    }
}
