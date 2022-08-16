/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [GradleScopeExtensions.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:53
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope
import org.gradle.api.artifacts.ProjectDependency

private const val Implementation = "implementation"
private const val LintPublish = "lintPublish"
private const val Api = "api"

fun DependencyScope.lintPublishs(vararg paths: Any) {
    delegate(
        method = LintPublish,
        paths = paths,
    )
}

fun DependencyScope.implementations(vararg paths: Any) {
    delegate(
        method = Implementation,
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

private fun DependencyScope.project(path: String) =
    project(mapOf("path" to path)) as ProjectDependency
