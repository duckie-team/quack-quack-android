/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "UnusedReceiverParameter",
)

package team.duckie.quackquack.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

/**
 * 기본 Application 그레이들 환경으로 설정합니다.
 *
 * @param commonExtension 설정할 그레이들의 [CommonExtension]
 */
internal fun Project.configureApplication(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        compileSdk = ApplicationConstants.compileSdk

        defaultConfig {
            minSdk = ApplicationConstants.minSdk
        }

        // 기본 폴더인 java 대신에 kotlin 폴더를 사용합니다.
        sourceSets {
            getByName("main").java.srcDirs("src/main/kotlin/")
        }

        compileOptions {
            sourceCompatibility = ApplicationConstants.javaVersion
            targetCompatibility = ApplicationConstants.javaVersion
        }

        kotlinOptions {
            jvmTarget = ApplicationConstants.jvmTarget
        }
    }
}
