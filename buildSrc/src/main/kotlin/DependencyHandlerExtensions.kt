/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementations(vararg dependencyNotation: Any) {
    dependencyNotation.forEach(::implementation)
}

private fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}
