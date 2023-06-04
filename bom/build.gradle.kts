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
  val ignoreProjects = listOf(
    projects.bom.dependencyProject,
    projects.uiSample.dependencyProject,
    projects.utilBackendTest.dependencyProject,
    projects.rubberdoc.dependencyProject,
    projects.catalog.dependencyProject,
    projects.materialIconGenerator.dependencyProject,
  )

  constraints {
    rootProject.subprojects.forEach { project ->
      if (project !in ignoreProjects) {
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
