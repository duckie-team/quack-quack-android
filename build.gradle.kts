/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import Build_gradle.BumpType
import Build_gradle.BumpType.Major
import Build_gradle.BumpType.Minor
import Build_gradle.BumpType.Patch
import Build_gradle.ReleaseTarget
import Build_gradle.ReleaseTarget.LintCompose
import Build_gradle.ReleaseTarget.LintCore
import Build_gradle.ReleaseTarget.LintQuack
import Build_gradle.ReleaseTarget.LintWriting
import Build_gradle.ReleaseTarget.Playground
import Build_gradle.ReleaseTarget.UiComponents
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // alias(libs.plugins.detekt)
    // alias(libs.plugins.ktlint)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)
    alias(libs.plugins.gradle.maven.publish.core)
    id(ConventionEnum.JvmDependencyGraph)
}

koverMerged {
    enable()
    filters {
        projects {
            // only includes ui-components and lint modules
            // TODO: apply wild-pattern
            excludes += listOf(
                projects.playground,
                projects.uiComponentsBenchmark,
                projects.uiComponentsBenchmarkApp,
                projects.uxWritingModel,
                projects.uxWritingOverlay,
                projects.uxWritingRule,
                projects.commonLint,
                projects.commonLintTest,
                projects.quackPublishBom,
                projects.lintCorePublish,
                projects.lintComposePublish,
                projects.lintQuackPublish,
                projects.lintCustomRuleAnnotation,
                projects.lintCustomRuleProcessor,
            ).map(DelegatingProjectDependency::getName)
        }
    }
    xmlReport {
        reportFile.set(file("$rootDir/report/test-coverage/report.xml"))
    }
    htmlReport {
        reportDir.set(file("$rootDir/report/test-coverage/html"))
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.build.kotlin)
        classpath(libs.build.dokka.base)
        classpath(libs.build.oss.license)
        classpath(libs.build.gradle.agp)
        classpath(libs.build.gradle.maven.publish.core)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        // detekt {
        //     parallel = true
        //     buildUponDefaultConfig = true
        //     toolVersion = libs.versions.detekt.get()
        //     config.setFrom(files("$rootDir/detekt-config.yml"))
        // }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.OptIn",
                    "-opt-in=kotlin.RequiresOptIn",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$rootDir/report/compose-metrics",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$rootDir/report/compose-reports",
                )
            }
        }
    }

    if (pluginManager.hasPlugin(rootProject.libs.plugins.dokka.get().pluginId)) {
        tasks.dokkaHtmlMultiModule.configure {
            moduleName.set("QuackQuack")
            outputDirectory.set(file("$rootDir/documents/dokka"))

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                footerMessage =
                    """made with <span style="color: #ff8300;">❤</span> by <a href="https://duckie.team/">Duckie-QuackQuack</a>"""
                customAssets = listOf(file("assets/logo-icon.svg"))
            }
        }
    }

    apply {
        plugin(rootProject.libs.plugins.kover.get().pluginId)
    }

    // apply {
    //     plugin(rootProject.libs.plugins.detekt.get().pluginId)
    //     plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    // }
}

subprojects {
    // https://github.com/gradle/gradle/issues/4823#issuecomment-715615422
    @Suppress("UnstableApiUsage")
    if (
        gradle.startParameter.isConfigureOnDemand &&
        buildscript.sourceFile?.extension?.toLowerCase() == "kts" &&
        parent != rootProject
    ) {
        generateSequence(parent) { project ->
            project.parent.takeIf { parent ->
                parent != rootProject
            }
        }.forEach { project ->
            evaluationDependsOn(project.path)
        }
    }

    // configure<KtlintExtension> {
    //     version.set(rootProject.libs.versions.ktlint.source.get())
    //     android.set(true)
    //     outputToConsole.set(true)
    //     additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    // }
}

tasks.register(
    name = "cleanAll",
    type = Delete::class,
) {
    allprojects.map { project ->
        project.buildDir
    }.forEach(::delete)
}

/**
 * Bump 할 버전을 정의합니다.
 *
 * @property Major Major 버전을 bump 합니다.
 * @property Minor Minor 버전을 bump 합니다.
 * @property Patch Patch 버전을 bump 합니다.
 */
enum class BumpType {
    Major,
    Minor,
    Patch;
}

/**
 * Release 할 대상을 정의합니다.
 *
 * @property Playground ui-components 의 playground 를 release 합니다.
 * @property LintCore lint-core 를 release 합니다.
 * @property LintQuack lint-quack 를 release 합니다.
 * @property LintCompose lint-compose 를 release 합니다.
 * @property LintWriting lint-writing 를 release 합니다.
 * @property UiComponents ui-components 를 release 합니다.
 */
enum class ReleaseTarget {
    UiComponents,
    Playground,
    LintCore,
    LintQuack,
    LintCompose,
    LintWriting;
}

/**
 * 특정 [ReleaseTarget] 에 맞는 versioning [파일][File] 인스턴스를 반환합니다.
 *
 * @param target versioning 파일을 얻어오고자 하는 [ReleaseTarget]
 * @return [ReleaseTarget] 에 맞는 versioning [파일][File] 인스턴스
 */
fun Project.getVersionFile(
    target: ReleaseTarget,
) = File(
    when (target) {
        UiComponents -> "$rootDir/versions/ui-components.txt"
        Playground -> "$rootDir/versions/playground.txt"
        LintCore -> "$rootDir/versions/lint-core.txt"
        LintQuack -> "$rootDir/versions/lint-quack.txt"
        LintCompose -> "$rootDir/versions/lint-compose.txt"
        LintWriting -> "$rootDir/versions/lint-writing.txt"
    }
)

/**
 * 주어진 [BumpType] 과 [ReleaseTarget] 에 해당하는 버전을 bump 하여 반환합니다.
 *
 * @param type Bump 할 버전
 * @param target Bump 할 대상
 * @return Bump 된 버전
 */
fun Project.bumpVersion(
    type: BumpType,
    target: ReleaseTarget,
): String {
    val versionFile = getVersionFile(
        target = target,
    )
    val lines = versionFile.readLines().toMutableList()
    when (type) {
        Major -> {
            val major = lines[0].split("=")[1].toInt()
            lines[0] = "major=${major + 1}"
            lines[1] = "minor=0"
            lines[2] = "patch=0"
        }
        Minor -> {
            val minor = lines[1].split("=")[1].toInt()
            lines[1] = "minor=${minor + 1}"
            lines[2] = "patch=0"
        }
        Patch -> {
            val patch = lines[2].split("=")[1].toInt()
            lines[2] = "patch=${patch + 1}"
        }
    }
    if (target == Playground) {
        val code = lines[3].split("=")[1].toInt()
        lines[3] = "code=${code + 1}"
    }
    return lines.joinToString(
        separator = "\n",
    )
}

/**
 * 주어진 [ReleaseTarget] 에 해당하는 버전을 스냅샷으로 설정하여 반환합니다.
 *
 * 스냅샷 버전은 versions 폴더 안에 있는 현재 버전에서 `y` 값을 +1 하고,
 * `z` 값을 0 으로 교체한 값을 사용합니다. **즉, `z` 는 항상 0 으로 고정됩니다.**
 * 이는 "스냅샷" 버전임을 강조하기 위함입니다.
 *
 * @param target 스냅샷 버전을 사용할 대상
 * @return 스냅샷 설정된 버전
 */
fun Project.setSnapshotVersion(
    target: ReleaseTarget,
): String {
    val versionFile = getVersionFile(
        target = target,
    )
    val lines = versionFile.readLines().toMutableList()
    val minor = lines[1].split("=")[1].toInt()
    lines[1] = "minor=${minor + 1}"
    lines[2] = "patch=0"
    return lines.joinToString(
        separator = "\n",
        postfix = "-SNAPSHOT",
    )
}

/**
 * 주어진 label 을 파싱하여 설정된 [BumpType] 과 [ReleaseTarget] 에 맞게
 * bump 를 진행합니다.
 */
tasks.create(
    name = "bump",
) {
    val type = (properties["type"] ?: return@create) as String
    val target = (properties["target"] ?: return@create) as String
    val bumpType = BumpType.valueOf(type)
    val releaseTarget = ReleaseTarget.valueOf(target)
    val version = bumpVersion(
        type = bumpType,
        target = releaseTarget,
    )
    val versionFile = getVersionFile(
        target = releaseTarget,
    )
    versionFile.writeText(
        text = version,
    )
}

/**
 * 주어진 label 을 파싱하여 설정된 [ReleaseTarget] 의 버전을
 * 스냅샷 버전으로 설정합니다.
 */
tasks.create(
    name = "setSnapshotVersion",
) {
    val target = (properties["target"] ?: return@create) as String
    val releaseTarget = ReleaseTarget.valueOf(target)
    val snapshotVersion = setSnapshotVersion(
        target = releaseTarget,
    )
    val versionFile = getVersionFile(
        target = releaseTarget,
    )
    versionFile.writeText(
        text = snapshotVersion,
    )
}
