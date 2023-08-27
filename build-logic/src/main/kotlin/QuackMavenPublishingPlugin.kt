/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import internal.applyPlugins
import internal.libs
import internal.parseArtifactVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.kotlin.dsl.configure

private const val RepositoryName = "duckie-team/quack-quack-android"
private const val QuackBaseGroupId = "team.duckie.quackquack"

class QuackMavenPublishingPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    with(project) {
      applyPlugins(
        libs.findPlugin("gradle-publish-maven-core").get().get().pluginId,
        libs.findPlugin("gradle-publish-maven-base").get().get().pluginId,
      )

      tasks.matching { task ->
        task.name.contains("signMavenPublication")
      }.configureEach {
        notCompatibleWithConfigurationCache("https://github.com/vanniktech/gradle-maven-publish-plugin/issues/259")
      }

      val (group, module, version) = ArtifactConfig.of(this).also { artifact ->
        logger.warn("Loading $artifact...")
      }

      extensions.configure<MavenPublishBaseExtension> {
        coordinates(
          groupId = group,
          artifactId = module,
          version = version,
        )

        publishToMavenCentral(
          host = SonatypeHost.S01,
          automaticRelease = true,
        )

        signAllPublications()

        pom {
          configureMavenPom(artifactId = module)
        }
      }
    }
  }
}

private fun MavenPom.configureMavenPom(artifactId: String) {
  name.set(artifactId)
  description.set("https://github.com/$RepositoryName")
  inceptionYear.set("2023")
  url.set("https://github.com/$RepositoryName")
  licenses {
    license {
      name.set("MIT License")
      url.set("https://github.com/$RepositoryName/blob/main/LICENSE")
    }
  }
  developers {
    developer {
      id.set("duckie")
      name.set("io.github.duckie-team")
      url.set("https://blog.duckie.team/")
      email.set("duckieteam.hi@gmail.com")
    }
  }
  scm {
    url.set("https://github.com/$RepositoryName")
    connection.set("scm:git:github.com/$RepositoryName.git")
    developerConnection.set("scm:git:ssh://github.com/$RepositoryName.git")
  }
}

data class ArtifactConfig(
  val group: String,
  val module: String,
  val version: String,
) {
  companion object {
    fun of(project: Project): ArtifactConfig {
      val projects = buildList {
        tailrec fun Project.addTailrecParentsToListIfNeeded() {
          val parent = parent
          if (parent != null && parent != parent.rootProject) {
            add(parent)
            parent.addTailrecParentsToListIfNeeded()
          }
        }
        add(project)
        project.addTailrecParentsToListIfNeeded()
      }.asReversed()
      val rootProject = projects.first()

      val groupSuffix = rootProject.name.split("-").first()
      val module = projects.joinToString("-", transform = Project::getName)
      val version = project.parseArtifactVersion()

      return ArtifactConfig(
        group = "$QuackBaseGroupId.$groupSuffix",
        module = module,
        version = version,
      )
    }
  }

  override fun toString() = "$group:$module:$version"
}
