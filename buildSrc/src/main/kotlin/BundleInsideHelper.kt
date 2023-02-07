/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

// TAKEN FROM: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:buildSrc/public/src/main/kotlin/androidx/build/BundleInsideHelper.kt

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.named

/**
 * Allow java and Android libraries to bundle other projects inside the project jar/aar.
 */
object BundleInsideHelper {
    /**
     * Creates a configuration for users to use that will be used bundle these dependency
     * jars inside of this lint check's jar. This is required because lintPublish does
     * not currently support dependencies, so instead we need to bundle any dependencies with the
     * lint jar manually. (b/182319899)
     *
     * ```
     * dependencies {
     *     if (rootProject.hasProperty("android.injected.invoked.from.ide")) {
     *         compileOnly(LINT_API_LATEST)
     *     } else {
     *         compileOnly(LINT_API_MIN)
     *     }
     *     compileOnly(KOTLIN_STDLIB)
     *     // Include this library inside the resulting lint jar
     *     bundleInside(project(":foo-lint-utils"))
     * }
     * ```
     * @receiver the project that should bundle jars specified by these configurations
     */
    fun forInsideLintJar(project: Project) {
        with(project) {
            val bundle = configurations.create("bundleInside")
            val compileOnly = configurations.getByName("compileOnly")
            val testImplementation = configurations.getByName("testImplementation")
            compileOnly.setExtendsFrom(listOf(bundle))
            testImplementation.setExtendsFrom(listOf(bundle))

            tasks.named<Jar>("jar").configure {
                dependsOn(bundle)
                from({
                    bundle
                        .filter { file ->
                            !file.name.contains("kotlin-stdlib")
                        }
                        .map { file ->
                            if (file.isDirectory) {
                                file
                            } else {
                                zipTree(file)
                            }
                        }
                })
            }
        }
    }
}
