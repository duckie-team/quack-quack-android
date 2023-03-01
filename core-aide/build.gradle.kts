/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    `java-library`
}

GradleInstallation.with(project) {
    kotlin()
    junit()
    lint()
}

dependencies {
    testImplementation(libs.test.strikt)
}
