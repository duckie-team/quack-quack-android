/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import org.gradle.api.artifacts.dsl.DependencyHandler as DependencyScope
import java.io.File
import org.gradle.api.Project

private const val Api = "api"
private const val BundleInside = "bundleInside"
private const val Implementation = "implementation"
private const val TestImplementation = "testImplementation"

// bundleInside 는 하나의 아티팩트만 가능함
fun DependencyScope.bundleInside(
    path: Any,
) {
    delegate(
        method = BundleInside,
        paths = arrayOf(path),
    )
}

fun DependencyScope.implementations(
    vararg paths: Any,
) {
    delegate(
        method = Implementation,
        paths = paths,
    )
}

fun DependencyScope.testImplementations(
    vararg paths: Any,
) {
    delegate(
        method = TestImplementation,
        paths = paths,
    )
}

fun DependencyScope.apis(
    vararg paths: Any,
) {
    delegate(
        method = Api,
        paths = paths,
    )
}

val Project.isReleaseVariantAvailable: Boolean
    get() {
        val fileConfigurationFile = File(
            "$rootDir/buildSrc/src/main/kotlin/BuildConstants.kt",
        ).also {
            println("File: ${it.absolutePath}")
            println("Exists: ${it.exists()}")
        }
        return fileConfigurationFile.exists()
    }

private fun DependencyScope.delegate(
    method: String,
    vararg paths: Any,
) {
    paths.forEach { path ->
        add(method, path)
    }
}
