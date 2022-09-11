/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope

private const val BundleInside = "bundleInside"
private const val Implementation = "implementation"
private const val TestImplementation = "testImplementation"
private const val Api = "api"
private const val LintChecks = "lintChecks"

fun DependencyScope.customLints(vararg paths: Any) {
    delegate(
        method = LintChecks,
        paths = paths
    )
}

fun DependencyScope.bundleInsides(vararg paths: Any) {
    delegate(
        method = BundleInside,
        paths = paths
    )
}

fun DependencyScope.implementations(vararg paths: Any) {
    delegate(
        method = Implementation,
        paths = paths,
    )
}

fun DependencyScope.testImplementations(vararg paths: Any) {
    delegate(
        method = TestImplementation,
        paths = paths,
    )
}

fun DependencyScope.apis(vararg paths: Any) {
    delegate(
        method = Api,
        paths = paths,
    )
}

private fun DependencyScope.delegate(method: String, vararg paths: Any) {
    paths.forEach { path ->
        add(method, path)
    }
}
