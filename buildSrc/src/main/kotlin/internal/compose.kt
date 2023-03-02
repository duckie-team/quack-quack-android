/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package internal

import Plugins
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import kotlinOptions
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureCompose(extension: CommonExtension<*, *, *, *>) {
    extension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose-compiler").get().toString()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:liveLiteralsEnabled=false",
            )
        }

        dependencies {
            add("implementation", libs.findLibrary("compose-compiler").get())
        }
    }
}

internal val Project.composeSupportExtension: CommonExtension<*, *, *, *>
    get() {
        return if (pluginManager.hasPlugin(Plugins.AndroidApplication)) {
            extensions.getByType<BaseAppModuleExtension>()
        } else if (pluginManager.hasPlugin(Plugins.AndroidLibrary)) {
            extensions.getByType<LibraryExtension>()
        } else {
            error("현재는 ${Plugins.AndroidApplication} 혹은 ${Plugins.AndroidLibrary} 모듈만 지원합니다.")
        }
    }
