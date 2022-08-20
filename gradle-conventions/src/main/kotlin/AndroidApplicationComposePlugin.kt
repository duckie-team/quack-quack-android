/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AndroidApplicationComposePlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import team.duckie.quackquack.convention.configureCompose
import team.duckie.quackquack.convention.libs
import team.duckie.quackquack.convention.setupCompose

/**
 * Android 프레임워크의 Application 환경에서 Jetpack Compose 를 사용할 준비를 합니다.
 */
internal class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<BaseAppModuleExtension>()
            configureCompose(extension)

            dependencies {
                setupCompose(
                    core = libs.findBundle("compose-core").get(),
                    debug = libs.findBundle("compose-debug").get(),
                )
            }
        }
    }
}
