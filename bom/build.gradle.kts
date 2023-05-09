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
  // 빌드 순서(알파벳순) 문제로 bom 까지만 플러그인 조회됨
  // val publishableProjects = rootProject.allprojects.filter { project ->
  //     project.plugins.hasPlugin(libs.plugins.gradle.publish.maven.core.get().pluginId)
  // }

  val publishableProjects = with(projects) {
    listOf(
      util,
      utilBackend,
      // TODO: publish utilBackendKsp,
      utilBackendKotlinc,
      // TODO: publish utilBackendTest,
      runtime,
      material,
      animation,
      ui,
      aide,
      aideAnnotation,
      aideProcessor,
      sugarMaterial,
      sugarProcessor,
      casaUi,
      casaAnnotation,
      casaMaterial,
      casaProcessor,
    )
  }.map { project ->
    ArtifactConfig.of(project.dependencyProject).toString()
  }

  constraints {
    publishableProjects.forEach(::api)
  }
}
