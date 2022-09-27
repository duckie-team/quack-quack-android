/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
)

import com.android.build.api.variant.TestAndroidComponentsExtension
import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.ApplicationConstants
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureApplication
import team.duckie.quackquack.convention.implementations
import team.duckie.quackquack.convention.libs

/**
 * Android 프레임워크에 의존적인 밴치마크 모듈을 구성합니다.
 *
 * 밴치마크 모듈은 baseline profiles 를 산출하기 위해 사용됩니다.
 */
internal class AndroidBenchmarkPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins(
                PluginEnum.AndroidTest,
                PluginEnum.AndroidKotlin,
            )

            extensions.configure<TestExtension> {
                configureApplication(this)

                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    create("benchmark") {
                        isDebuggable = true
                        signingConfig = getByName("debug").signingConfig
                        matchingFallbacks += listOf("release")
                    }
                }

                targetProjectPath = ":playground"
                experimentalProperties["android.experimental.self-instrumenting"] = true
            }

            dependencies {
                implementations(libs.findBundle("benchmark").get())
            }

            extensions.configure<TestAndroidComponentsExtension> {
                beforeVariants(selector().all()) { testVariant ->
                    testVariant.enable = testVariant.buildType == "benchmark"
                }
            }
        }
    }
}
