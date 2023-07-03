/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  `java-platform`
  quackquack("quack-publishing")
}

dependencies {
  constraints {
    rootProject.subprojects.forEach { project ->
      if (
        project != projects.bom.dependencyProject &&
        File(project.projectDir, "version.txt").exists()
      ) {
        api(
          ArtifactConfig.of(project).toString()
            .also { artifact ->
              println("BOM publishing: $artifact")
            },
        )
      }
    }
  }
}
