/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope
import org.gradle.api.artifacts.ProjectDependency

private const val Implementation = "implementation"
private const val DebugImplementation = "debugImplementation"
private const val TestRuntimeOnly = "testRuntimeOnly"
private const val TestImplementation = "testImplementation"
private const val CompileOnly = "compileOnly"
private const val LintChecks = "lintChecks"
private const val Api = "api"

private val lintModules = listOf(":lint-core", ":lint-compose", ":lint-writing")

fun DependencyScope.setupJunit(core: Any, engine: Any) {
    delegate(method = TestImplementation, core)
    delegate(method = TestRuntimeOnly, engine)
}

fun DependencyScope.setupLint(core: Any, test: Any) {
    delegate(method = CompileOnly, core)
    delegate(method = TestImplementation, test)
}

fun DependencyScope.setupCompose(core: Any, debug: Any) {
    delegate(method = Implementation, core)
    delegate(method = DebugImplementation, debug)
}

fun DependencyScope.lintChecksOverride(vararg paths: Any) {
    delegate(method = LintChecks, paths = paths)
}

fun DependencyScope.implementations(vararg paths: Any) {
    delegate(method = Implementation, paths = paths)
}

fun DependencyScope.debugImplementations(vararg paths: Any) {
    delegate(method = DebugImplementation, paths = paths)
}

fun DependencyScope.apis(vararg paths: Any) {
    delegate(method = Api, paths = paths)
}

private fun DependencyScope.delegate(method: String, vararg paths: Any) {
    paths.forEach { path -> add(method, path) }
}

private fun DependencyScope.project(path: String) =
    project(mapOf("path" to path)) as ProjectDependency