/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [application.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "unused")

package team.duckie.quackquack.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureApplication(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        compileSdk = ApplicationConstants.compileSdk

        defaultConfig {
            minSdk = ApplicationConstants.minSdk
        }

        lint {
            abortOnError = false
        }

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
