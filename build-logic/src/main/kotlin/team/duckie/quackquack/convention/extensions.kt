/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("SameParameterValue")

package team.duckie.quackquack.convention

import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * 그레이들에서 좀 더 쉽게 사용하기 위한 확장 함수들을 정의합니다.
 */
private const val Api = "api"
private const val LintPublish = "lintPublish"
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

internal fun DependencyScope.lintPublish(
    path: Any,
) {
    delegate(
        method = LintPublish,
        paths = arrayOf(path),
    )
}

internal fun DependencyScope.apis(
    vararg paths: Any,
) {
    delegate(
        method = Api,
        paths = paths,
    )
}

internal fun DependencyScope.compileOnlys(
    vararg paths: Any,
) {
    delegate(
        method = CompileOnly,
        paths = paths,
    )
}

internal fun DependencyScope.implementations(
    vararg paths: Any,
) {
    delegate(
        method = Implementation,
        paths = paths,
    )
}

internal fun DependencyScope.testImplementations(
    vararg paths: Any,
) {
    delegate(
        method = TestImplementation,
        paths = paths,
    )
}

internal fun DependencyScope.androidTestImplementations(
    vararg paths: Any,
) {
    delegate(
        method = AndroidTestImplementation,
        paths = paths,
    )
}

internal fun DependencyScope.debugImplementations(
    vararg paths: Any,
) {
    delegate(
        method = DebugImplementation,
        paths = paths,
    )
}

internal fun DependencyScope.setupJunit(
    core: Any,
    engine: Any,
) {
    delegate(
        method = TestImplementation,
        paths = arrayOf(core),
    )
    delegate(
        method = TestRuntimeOnly,
        paths = arrayOf(engine),
    )
}

internal fun DependencyScope.setupLint(
    core: Any,
    test: Any,
) {
    delegate(
        method = CompileOnly,
        paths = arrayOf(core),
    )
    delegate(
        method = TestImplementation,
        paths = arrayOf(test),
    )
}

internal fun DependencyScope.setupCompose(
    core: Any,
    debug: Any,
) {
    delegate(
        method = Implementation,
        paths = arrayOf(core),
    )
    delegate(
        method = DebugImplementation,
        paths = arrayOf(debug),
    )
}

private fun DependencyScope.delegate(
    method: String,
    vararg paths: Any,
) {
    paths.forEach { path ->
        add(method, path)
    }
}
