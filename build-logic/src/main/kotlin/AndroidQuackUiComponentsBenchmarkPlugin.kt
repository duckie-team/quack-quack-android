/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
)

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.ApplicationConstants
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureApplication
import team.duckie.quackquack.convention.implementations
import team.duckie.quackquack.convention.invoke
import team.duckie.quackquack.convention.libs

/**
 * Android 프레임워크에 의존적인 밴치마크 모듈을 구성합니다.
 *
 * benchmark-ui-components-app 모듈의 baseline profiles 을 생성하기 위해
 * 사용됩니다.
 */
internal class AndroidQuackUiComponentsBenchmarkPlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            applyPlugins(
                PluginEnum.AndroidTest,
                PluginEnum.KotlinAndroid,
            )

            extensions.configure<TestExtension> {
                configureApplication(
                    extension = this,
                )

                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                targetProjectPath = ":ui-components-benchmark-app"
                experimentalProperties["android.experimental.self-instrumenting"] = true

                testOptions {
                    managedDevices {
                        devices {
                            create(
                                name = "pixel6Api31",
                                type = ManagedVirtualDevice::class,
                            ) {
                                device = "Pixel 6"
                                apiLevel = 31
                                systemImageSource = "aosp"
                            }
                        }
                    }
                }
            }

            dependencies {
                implementations(
                    libs.findBundle("benchmark").get(),
                )
            }
        }
    }
}
