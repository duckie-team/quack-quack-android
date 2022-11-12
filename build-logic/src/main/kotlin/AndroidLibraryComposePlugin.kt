/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import team.duckie.quackquack.convention.configureCompose
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupCompose

/**
 * Android 프레임워크의 Library 환경에서 Jetpack Compose 를 사용할 준비를 합니다.
 */
internal class AndroidLibraryComposePlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            val extension = extensions.getByType<LibraryExtension>()

            configureCompose(
                extension = extension,
            )

            dependencies {
                setupCompose(
                    core = libs.findBundle("compose-core").get(),
                    debug = libs.findBundle("compose-debug").get(),
                )
            }
        }
    }
}
