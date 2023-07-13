/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("android-library")
  quackquack("android-compose")
  quackquack("quack-publishing")
}

android {
  namespace = "team.duckie.quackquack.ui.sugar"
}

dependencies {
  api(projects.ui.orArtifact())
}
