/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

// @formatter:off

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import internal.ApplicationConstants
import internal.applyPlugins
import internal.configureAndroid
import internal.configureCompose
import internal.libs
import internal.androidExtensions
import internal.isAndroidProject
import internal.setupJunit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.SourceSetContainer
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
        configureAndroid(this)

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
        configureAndroid(this)

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
    configureCompose(androidExtensions)
})

internal class AndroidComposeMetricsPlugin : BuildLogicPlugin({
    androidExtensions.apply {
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
    applyPlugins(Plugins.JavaLibrary, Plugins.KotlinJvm)

    extensions.configure<JavaPluginExtension>() {
        sourceCompatibility = ApplicationConstants.JavaVersion
        targetCompatibility = ApplicationConstants.JavaVersion
    }

    extensions.configure<KotlinProjectExtension>() {
        jvmToolchain(ApplicationConstants.JavaVersionAsInt)
    }

    extensions.configure<SourceSetContainer>() {
        getByName("main").java.srcDirs("src/main/kotlin/")
        getByName("test").java.srcDirs("src/test/kotlin/")
    }

    dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get())
})

// prefix가 `Jvm`이 아니라 `Test`인 이유:
// 적용 타켓(android or pure)에 따라 `useJUnitPlatform()` 방식이 달라짐
internal class TestJUnitPlugin : BuildLogicPlugin({
    useJUnitPlatformForTarget()

    dependencies {
        setupJunit(
            core = libs.findLibrary("test-junit-core").get(),
            engine = libs.findLibrary("test-junit-engine").get(),
        )
    }
})

// prefix가 `Jvm`이 아니라 `Test`인 이유:
// 적용 타켓(android or pure)에 따라 `useJUnitPlatform()` 방식이 달라짐
internal class TestKotestPlugin : BuildLogicPlugin({
    useJUnitPlatformForTarget()
    dependencies.add("implementation", libs.findLibrary("test-kotest-framework").get())
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

// ref: https://kotest.io/docs/quickstart#test-framework
internal fun Project.useJUnitPlatformForTarget() {
    if (isAndroidProject) {
        androidExtensions.testOptions {
            unitTests.all { test ->
                test.useJUnitPlatform()
            }
        }
    } else {
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}
