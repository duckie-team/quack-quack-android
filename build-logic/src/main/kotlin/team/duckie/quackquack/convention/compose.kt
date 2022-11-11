/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

/**
 * Jetpack Compose 를 사용하기 위한 그레이들 환경을 설정합니다.
 *
 * @param extension 설정할 그레이들의 [CommonExtension]
 */
internal fun Project.configureCompose(
    extension: CommonExtension<*, *, *, *>,
) {
    extension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose-compiler").get().toString()
        }
    }
}
