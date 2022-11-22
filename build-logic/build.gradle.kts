/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `kotlin-dsl`
    alias(libs.plugins.dokka)
}

group = "team.duckie.quackquack.convention"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Unresolved reference: implementations
    implementation(libs.build.kotlin)
    implementation(libs.build.dokka.base)
    implementation(libs.build.dokka.plugin)
    implementation(libs.build.gradle.agp)
    implementation(libs.build.gradle.maven.publish.core)
    implementation(libs.util.gfm.dsl)
}

gradlePlugin {
    val prefix = "quack"
    plugins {
        register("androidLintPlugin") {
            id = "$prefix.android.lint"
            implementationClass = "AndroidLintPlugin"
        }
        register("androidQuackPublishPlugin") {
            id = "$prefix.android.publish"
            implementationClass = "AndroidQuackPublishPlugin"
        }
        register("androidCommonLintPlugin") {
            id = "$prefix.android.common.lint"
            implementationClass = "AndroidCommonLintPlugin"
        }
        register("androidQuackUiComponentsBenchmarkPlugin") {
            id = "$prefix.android.quack.ui.components.benchmark"
            implementationClass = "AndroidQuackUiComponentsBenchmarkPlugin"
        }
        register("androidApplicationPlugin") {
            id = "$prefix.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationComposePlugin") {
            id = "$prefix.android.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("androidLibraryPlugin") {
            id = "$prefix.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryComposePlugin") {
            id = "$prefix.android.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidLibraryComposeUiTestPlugin") {
            id = "$prefix.android.library.compose.uitest"
            implementationClass = "AndroidLibraryComposeUiTestPlugin"
        }
        register("jvmJunit4Plugin") {
            id = "$prefix.jvm.junit4"
            implementationClass = "JvmJUnit4Plugin"
        }
        register("jvmLibraryPlugin") {
            id = "$prefix.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
        register("jvmDokkaPlugin") {
            id = "$prefix.jvm.dokka"
            implementationClass = "JvmDokkaPlugin"
        }
        register("dependencyGraphPlugin") {
            id = "$prefix.jvm.dependency.graph"
            implementationClass = "DependencyGraphPlugin"
        }
        register("artifactBumpPlugin") {
            id = "$prefix.jvm.artifact.bump"
            implementationClass = "ArtifactBumpPlugin"
        }
        register("artifactSnapshotPlugin") {
            id = "$prefix.jvm.artifact.snapshot"
            implementationClass = "ArtifactSnapshotPlugin"
        }
        register("uiComponentsDocumentation") {
            id = "$prefix.uicomponents.documentation"
            implementationClass = "UiComponentsDocumentationPlugin"
        }
        register("uiComponentSnapshotsDocumentation") {
            id = "$prefix.uicomponent.snapshots.documentation"
            implementationClass = "UiComponentSnapshotsDocumentation"
        }
    }
}
