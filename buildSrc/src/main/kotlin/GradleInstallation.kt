/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import internal.ApplicationConstants
import internal.applyPlugins
import internal.configureApplication
import internal.configureCompose
import internal.libs
import internal.setupJunit
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val EXPLICIT_API = "-Xexplicit-api=strict"

class GradleInstallationScope internal constructor(private val project: Project) {
    fun android(block: BaseAppModuleExtension.() -> Unit) {
        with(project) {
            applyPlugins(
                Plugins.AndroidApplication,
                Plugins.KotlinAndroid,
            )

            extensions.configure<BaseAppModuleExtension> {
                configureApplication(this)

                buildFeatures {
                    buildConfig = false
                }

                defaultConfig {
                    targetSdk = ApplicationConstants.TargetSdk
                    versionName = ApplicationConstants.VersionName
                    versionCode = ApplicationConstants.VersionCode
                }

                block()
            }
        }
    }

    fun android(namespace: String) {
        android {
            this.namespace = namespace
        }
    }

    fun library(block: LibraryExtension.() -> Unit) {
        with(project) {
            applyPlugins(
                Plugins.AndroidLibrary,
                Plugins.KotlinAndroid,
            )

            extensions.configure<LibraryExtension> {
                configureApplication(this)

                buildFeatures {
                    buildConfig = false
                }

                defaultConfig.apply {
                    targetSdk = ApplicationConstants.TargetSdk
                }

                block()
            }
        }
    }

    fun library(namespace: String) {
        library {
            this.namespace = namespace
        }
    }

    fun lint() {
        with(project) {
            applyPlugins(Plugins.AndroidLint)

            dependencies {
                add("compileOnly", libs.findLibrary("kotlin-stdlib").get())
                add("compileOnly", libs.findBundle("android-lint").get())
                add("testImplementation", libs.findBundle("test-android-lint").get())
            }
        }
    }

    fun compose() {
        with(project) {
            val extension = if (pluginManager.hasPlugin(Plugins.AndroidApplication)) {
                extensions.getByType<BaseAppModuleExtension>()
            } else if (pluginManager.hasPlugin(Plugins.AndroidLibrary)) {
                extensions.getByType<LibraryExtension>()
            } else {
                error("현재는 ${Plugins.AndroidApplication} 혹은 ${Plugins.AndroidLibrary} 모듈만 지원합니다.")
            }

            configureCompose(extension)
        }
    }

    fun kotlin(block: KotlinJvmProjectExtension.() -> Unit = {}) {
        with(project) {
            applyPlugins(
                Plugins.KotlinCore,
                Plugins.JavaLibrary,
            )

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = ApplicationConstants.JavaVersion
                targetCompatibility = ApplicationConstants.JavaVersion
            }

            extensions.configure<KotlinJvmProjectExtension>(block)

            dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get())
        }
    }

    fun junit() {
        with(project) {
            dependencies {
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get(),
                )
            }
        }
    }

    fun dokka() {
        val footerMessage =
            "made with <span style=\"color: #ff8300;\">❤</span> by <a href=\"https://duckie.team/\">Duckie</a>"
        with(project) {
            applyPlugins(libs.findPlugin("dokka").get().get().pluginId)

            tasks.withType<DokkaTaskPartial> {
                suppressInheritedMembers.set(true)

                dokkaSourceSets.configureEach {
                    jdkVersion.set(ApplicationConstants.JavaVersionAsInt)
                }

                pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                    this.footerMessage = footerMessage
                }
            }
        }
    }

    fun explicitApi() {
        project.tasks
            .matching { it is KotlinCompile && !it.name.contains("test", ignoreCase = true) }
            .configureEach {
                if (!project.hasProperty("kotlin.optOutExplicitApi")) {
                    val kotlinCompile = this as KotlinCompile
                    if (EXPLICIT_API !in kotlinCompile.kotlinOptions.freeCompilerArgs) {
                        kotlinCompile.kotlinOptions.freeCompilerArgs += EXPLICIT_API
                    }
                }
            }
    }
}

object GradleInstallation {
    fun with(project: Project, block: GradleInstallationScope.() -> Unit) {
        GradleInstallationScope(project).block()
    }
}
