/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package internal

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureApplication(extension: CommonExtension<*, *, *, *>) {
    extension.apply {
        compileSdk = ApplicationConstants.CompileSdk

        defaultConfig {
            minSdk = ApplicationConstants.MinSdk
        }

        sourceSets {
            getByName("main").java.srcDirs("src/main/kotlin/")
        }

        compileOptions {
            sourceCompatibility = ApplicationConstants.JavaVersion
            targetCompatibility = ApplicationConstants.JavaVersion
        }

        lint {
            checkTestSources = true
        }

        installFormattingPluginIfNeeded()
    }
}
