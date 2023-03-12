/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package internal

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

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

        extensions.configure<KotlinProjectExtension>() {
            jvmToolchain(ApplicationConstants.JavaVersionAsInt)
        }

        dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get())
    }
}
