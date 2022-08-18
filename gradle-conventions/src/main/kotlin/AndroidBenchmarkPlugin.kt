/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AndroidBenchmarkPlugin.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "unused")

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

internal class AndroidBenchmarkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.AndroidTest,
                PluginEnum.AndroidKotlin,
            )

            extensions.configure<TestExtension> {
                configureApplication(
                    commonExtension = this,
                )

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
                beforeVariants(
                    selector = selector().all(),
                ) { testVariant ->
                    testVariant.enable = testVariant.buildType == "benchmark"
                }
            }
        }
    }
}
