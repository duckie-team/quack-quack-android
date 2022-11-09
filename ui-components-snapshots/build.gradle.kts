/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage",
)

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "team.duckie.quackquack.ui.snapshot"

    lint {
        disable.apply {
            // 테스트용 컴포저블에 주석을 필수로 명시하는건 너무 과함
            add("KDocFields")
        }
    }
}

dependencies {
    implementation(projects.uiComponents)
    testImplementation(libs.test.parameter.injector)
    lintChecks(projects.lintCore)
    lintChecks(projects.lintCompose)
}

androidComponents {
    beforeVariants(selector().withBuildType("release")) { builder ->
        builder.enable = false
    }
}

tasks.withType<Test>().configureEach {
    maxHeapSize = "2g"
}
