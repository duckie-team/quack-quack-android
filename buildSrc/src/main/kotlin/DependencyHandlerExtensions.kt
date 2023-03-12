/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kapt(dependencyNotation: Any) {
    add("kapt", dependencyNotation)
}

fun DependencyHandler.implementations(vararg dependencyNotation: Any) {
    dependencyNotation.forEach(::implementation)
}

fun DependencyHandler.testImplementations(vararg dependencyNotation: Any) {
    dependencyNotation.forEach(::testImplementation)
}

fun DependencyHandler.androidTestImplementations(vararg dependencyNotation: Any) {
    dependencyNotation.forEach(::androidTestImplementation)
}

private fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

private fun DependencyHandler.testImplementation(dependencyNotation: Any) {
    add("testImplementation", dependencyNotation)
}

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}
