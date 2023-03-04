/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

// @formatter:off

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import internal.ApplicationConstants
import internal.applyPlugins
import internal.composeSupportExtension
import internal.configureApplication
import internal.configureCompose
import internal.libs
import internal.setupJunit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val EXPLICIT_API = "-Xexplicit-api=strict"
private const val DOKKA_FOOTER_MESSAGE =
    "made with <span style=\"color: #ff8300;\">❤</span> by <a href=\"https://duckie.team/\">Duckie</a>"

internal abstract class BuildLogicPlugin(private val block: Project.() -> Unit) : Plugin<Project> {
    final override fun apply(target: Project) {
        with(target, block = block)
    }
}

internal class AndroidApplicationPlugin : BuildLogicPlugin({
    applyPlugins(Plugins.AndroidApplication, Plugins.KotlinAndroid)

    extensions.configure<BaseAppModuleExtension> {
        configureApplication(this)

        defaultConfig {
            targetSdk = ApplicationConstants.TargetSdk
            versionCode = ApplicationConstants.VersionCode
            versionName = ApplicationConstants.VersionName
        }
    }
})

internal class AndroidLibraryPlugin : BuildLogicPlugin({
    applyPlugins(Plugins.AndroidLibrary, Plugins.KotlinAndroid)

    extensions.configure<LibraryExtension> {
        configureApplication(this)

        defaultConfig.apply {
            targetSdk = ApplicationConstants.TargetSdk
        }
    }
})

internal class AndroidLintPlugin : BuildLogicPlugin({
    applyPlugins(Plugins.AndroidLint)

    dependencies {
        add("compileOnly", libs.findBundle("android-lint").get())
        add("testImplementation", libs.findBundle("test-android-lint").get())
    }
})

internal class AndroidComposePlugin : BuildLogicPlugin({
    configureCompose(composeSupportExtension)
})

internal class AndroidComposeMetricsPlugin : BuildLogicPlugin({
    composeSupportExtension.apply {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$projectDir/report/compose-metrics",
            )
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$projectDir/report/compose-reports",
            )
        }
    }
})

internal class JvmKotlinPlugin : BuildLogicPlugin({
    applyPlugins(Plugins.JavaLibrary, Plugins.KotlinCore)

    extensions.configure<JavaPluginExtension>() {
        sourceCompatibility = ApplicationConstants.JavaVersion
        targetCompatibility = ApplicationConstants.JavaVersion
    }

    extensions.configure<KotlinProjectExtension>() {
        jvmToolchain(ApplicationConstants.JavaVersionAsInt)
    }

    dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get())
})

internal class JvmJUnitPlugin : BuildLogicPlugin({
    dependencies {
        tasks.withType<Test> {
            useJUnitPlatform()
        }

        setupJunit(
            core = libs.findLibrary("test-junit-core").get(),
            engine = libs.findLibrary("test-junit-engine").get(),
        )
    }
})

internal class JvmDokkaPlugin : BuildLogicPlugin({
    applyPlugins(libs.findPlugin("kotlin-dokka").get().get().pluginId)

    tasks.withType<DokkaTask> {
        outputDirectory.set(file("$projectDir/document"))
        suppressInheritedMembers.set(true)

        dokkaSourceSets.configureEach {
            jdkVersion.set(ApplicationConstants.JavaVersionAsInt)
        }

        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = DOKKA_FOOTER_MESSAGE
            customAssets = listOf(file("$rootDir/assets/logo-icon.svg"))
        }
    }
})

internal class KotlinExplicitApiPlugin : BuildLogicPlugin({
    tasks
        .matching { it is KotlinCompile && !it.name.contains("test", ignoreCase = true) }
        .configureEach {
            if (!project.hasProperty("kotlin.optOutExplicitApi")) {
                val kotlinCompile = this as KotlinCompile
                if (EXPLICIT_API !in kotlinCompile.kotlinOptions.freeCompilerArgs) {
                    kotlinCompile.kotlinOptions.freeCompilerArgs += EXPLICIT_API
                }
            }
        }
})
