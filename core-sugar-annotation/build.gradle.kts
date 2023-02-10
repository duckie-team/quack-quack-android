/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

GradleInstallation.with(project) {
    kotlin {
        explicitApi = ExplicitApiMode.Strict
    }
}
