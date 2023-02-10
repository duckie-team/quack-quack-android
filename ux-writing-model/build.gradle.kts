/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
}

android {
    namespace = "team.duckie.quackquack.uxwriting.model"
}

dependencies {
    lintChecks(projects.lintCore)
}
