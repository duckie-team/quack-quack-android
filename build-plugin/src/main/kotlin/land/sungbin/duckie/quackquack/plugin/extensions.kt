/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [extensions.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("SameParameterValue")

package land.sungbin.duckie.quackquack.plugin

import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

private const val CompileOnly = "compileOnly"
private const val Implementation = "implementation"
private const val DebugImplementation = "debugImplementation"
private const val TestRuntimeOnly = "testRuntimeOnly"
private const val TestImplementation = "testImplementation"
private const val AndroidTestImplementation = "androidTestImplementation"

internal fun Project.applyPlugins(vararg plugins: String) {
    plugins.forEach { plugin ->
        pluginManager.apply(plugin)
    }
}

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

internal fun DependencyScope.implementations(vararg paths: Any) {
    delegate(
        method = Implementation,
        paths = paths,
    )
}

internal fun DependencyScope.androidTestImplementations(vararg paths: Any) {
    delegate(
        method = AndroidTestImplementation,
        paths = paths,
    )
}

internal fun DependencyScope.debugImplementations(vararg paths: Any) {
    delegate(
        method = DebugImplementation,
        paths = paths,
    )
}

internal fun DependencyScope.setupJunit(core: Any, engine: Any) {
    delegate(
        method = TestImplementation,
        core,
    )
    delegate(
        method = TestRuntimeOnly,
        engine,
    )
}

fun DependencyScope.setupLint(core: Any, test: Any) {
    delegate(
        method = CompileOnly,
        core,
    )
    delegate(
        method = TestImplementation,
        test,
    )
}

internal fun DependencyScope.setupCompose(core: Any, debug: Any) {
    delegate(
        method = Implementation,
        core,
    )
    delegate(
        method = DebugImplementation,
        debug,
    )
}

private fun DependencyScope.delegate(method: String, vararg paths: Any) {
    paths.forEach { path ->
        add(method, path)
    }
}
