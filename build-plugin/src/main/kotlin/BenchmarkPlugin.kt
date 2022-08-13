@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.api.variant.TestAndroidComponentsExtension
import com.android.build.gradle.TestExtension
import land.sungbin.duckie.quackquack.plugin.ApplicationConstants
import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.configureApplication
import land.sungbin.duckie.quackquack.plugin.implementations
import land.sungbin.duckie.quackquack.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class BenchmarkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(PluginEnum.Test, PluginEnum.Kotlin)
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
                beforeVariants(selector().all()) {
                    it.enable = it.buildType == "benchmark"
                }
            }
        }
    }
}