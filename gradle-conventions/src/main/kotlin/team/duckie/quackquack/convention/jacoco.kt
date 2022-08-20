/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [jacoco.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

import java.io.File
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Jacoco 에서 커버리지 계산에 제외할 폴더들
 */
private val coverageExclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*"
)

/**
 * 모듈 단위 그레이들에서 Jacoco 환경을 설정해 줍니다.
 *
 * 산출된 exec 를 정해진 경로로 저장합니다.
 *
 * 현재 executionData 설정이 되지 않는 문제가 있습니다.
 * https://github.com/sungbinland/duckie-quack-quack/issues/11
 */
internal fun Project.configureJacocoForModule() {
    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("jacoco").get().toString()
    }

    tasks.create(
        "coverage",
        JacocoReport::class.java
    ) {
        group = "Once Coverage"

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val sourceFile = file("$projectDir/src/main/kotlin")
        val kClassFile = fileTree("$buildDir/tmp/kotlin-classes/debug") {
            exclude(coverageExclusions)
        }

        sourceDirectories.setFrom(sourceFile)
        classDirectories.setFrom(kClassFile)
        executionData.setFrom(file("$buildDir/jacoco/coverage.exec"))
    }.dependsOn("test")

    tasks.withType<Test>().configureEach {
        configure<JacocoTaskExtension> {
            // Required for JDK 11 with the above
            // https://github.com/gradle/gradle/issues/5184#issuecomment-391982009
            excludes = listOf("jdk.internal.*")
        }
    }
}

/**
 * 프로젝트 단위 그레이들에서 Jacoco 환경을 설정해 줍니다.
 *
 * 모든 모듈들의 exec 들을 모아서 하나의 커버리지로 계산하는데 사용됩니다.
 *
 * 현재 executionData 설정이 되지 않는 문제가 있습니다.
 * https://github.com/sungbinland/duckie-quack-quack/issues/11
 */
internal fun Project.configureJacocoForProject() {
    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("jacoco").get().toString()
        reportsDirectory.set(file(rootProject.file("documents/coverage")))
    }

    tasks.create(
        "allCoverage",
        JacocoReport::class.java
    ) {
        group = "All Coverage"

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val sourceFiles = files(subprojects.map { project ->
            "${project.projectDir}/src/main/kotlin"
        })
        val kClassFiles = subprojects.map { project ->
            fileTree("${project.projectDir}/tmp/kotlin-classes/debug") {
                exclude(coverageExclusions)
            }
        }
        val executions = subprojects.mapNotNull { project ->
            "${project.buildDir}/jacoco/coverage.exec".takeIf { dir ->
                File(dir).exists()
            }
        }

        sourceDirectories.setFrom(sourceFiles)
        classDirectories.setFrom(kClassFiles)
        executionData.setFrom(executions)
    }.dependsOn("coverage")

    tasks.withType<Test>().configureEach {
        configure<JacocoTaskExtension> {
            // Required for JDK 11 with the above
            // https://github.com/gradle/gradle/issues/5184#issuecomment-391982009
            excludes = listOf("jdk.internal.*")
        }
    }
}
