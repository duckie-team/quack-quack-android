/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

// @formatter:off

@file:Suppress("UnstableApiUsage","unused")

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import internal.ApplicationConstants
import internal.applyPlugins
import internal.configureAndroid
import internal.configureCompose
import internal.configureGmd
import internal.libs
import internal.androidExtensions
import internal.isAndroidProject
import internal.setupJunit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestResult
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.kotlin.dsl.KotlinClosure2
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val EXPLICIT_API = "-Xexplicit-api=strict"

internal abstract class BuildLogicPlugin(private val block: Project.() -> Unit) : Plugin<Project> {
    final override fun apply(project: Project) {
        with(project, block = block)
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

internal class AndroidGmdPlugin : BuildLogicPlugin({
    configureGmd(androidExtensions)
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

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = ApplicationConstants.JavaVersion
        targetCompatibility = ApplicationConstants.JavaVersion
    }

    extensions.configure<KotlinProjectExtension> {
        jvmToolchain(ApplicationConstants.JavaVersionAsInt)
    }

    extensions.configure<SourceSetContainer> {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
    }

    dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get())
})

// prefix가 `Jvm`이 아니라 `Test`인 이유:
// 적용 타켓(android or pure)에 따라 `useJUnitPlatform()` 방식이 달라짐
internal class TestJUnitPlugin : BuildLogicPlugin({
    useTestPlatformForTarget()
    dependencies {
        setupJunit(
            core = libs.findLibrary("test-junit-core").get(),
            engine = libs.findLibrary("test-junit-engine").get(),
        )
    }
})
internal class TestKotestPlugin : BuildLogicPlugin({
    useTestPlatformForTarget()
    dependencies.add("testImplementation", libs.findLibrary("test-kotest-framework").get())
})

internal class KotlinExplicitApiPlugin : BuildLogicPlugin({
    tasks
        .matching { task ->
            task is KotlinCompile &&
                    !task.name.contains("test", ignoreCase = true)
        }
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
private fun Project.useTestPlatformForTarget() {
    fun AbstractTestTask.setTestConfiguration() {
        testLogging {
            events = setOf(
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.FAILED,
            )
        }
        afterSuite(
            KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
                if (desc.parent == null) { // will match the outermost suite
                    val output = "Results: ${result.resultType} " +
                            "(${result.testCount} tests, " +
                            "${result.successfulTestCount} passed, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped)"
                    println(output)
                }
            })
        )
    }

    if (isAndroidProject) {
        androidExtensions.testOptions {
            unitTests.all { test ->
                test.useJUnitPlatform()
            }
        }
        tasks.withType<Test>().configureEach {
            setTestConfiguration()
        }
    } else {
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
            setTestConfiguration()
        }
    }
}
