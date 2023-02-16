/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    `android-application`
}

GradleInstallation.with(project) {
    android(namespace = "team.duckie.quackquack.playground")
    compose()
}

dependencies {
    implementations(
        libs.compose.activity,
        projects.core,
    )
}