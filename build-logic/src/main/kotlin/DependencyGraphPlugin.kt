/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

import java.io.File
import java.util.Locale
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register
import team.duckie.quackquack.convention.PluginEnum

class DependencyGraphPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register<DependencyGraphTask>("dependencyGraph")
    }
}

abstract class DependencyGraphTask : DefaultTask() {
    @TaskAction
    fun run() {
        val dot = File(project.rootProject.rootDir, "assets/project-dependency-graph/graph.dot")
        dot.parentFile.deleteRecursively()
        dot.parentFile.mkdirs()

        dot.appendText(
            """
            |digraph {
            |  graph [label="${project.rootProject.name}\n ",labelloc=t,fontsize=30,ranksep=1.4];
            |  node [style=filled, fillcolor="#bbbbbb"];
            |  rankdir=TB;
            |
        """.trimMargin()
        )

        val rootProjects = mutableListOf<Project>()
        val queue = mutableListOf(project.rootProject)
        while (queue.isNotEmpty()) {
            val project = queue.removeAt(0)
            rootProjects.add(project)
            queue.addAll(project.childProjects.values)
        }
        queue.add(project.rootProject)

        val dependencyProjects = LinkedHashSet<Project>()
        val dependencies = LinkedHashMap<Pair<Project, Project>, MutableList<String>>()
        val androidApplication = mutableListOf<Project>()
        val androidLibraryProjects = mutableListOf<Project>()
        val androidLintProjects = mutableListOf<Project>()
        val jvmLibraryProjecst = mutableListOf<Project>()

        while (queue.isNotEmpty()) {
            val project = queue.removeAt(0)
            queue.addAll(project.childProjects.values)

            with(project.plugins) {
                when {
                    hasPlugin(PluginEnum.AndroidApplication) -> androidApplication.add(project)
                    hasPlugin(PluginEnum.AndroidLibrary) -> androidLibraryProjects.add(project)
                    hasPlugin(PluginEnum.AndroidLint) -> androidLintProjects.add(project)
                    hasPlugin(PluginEnum.JavaLibrary) -> jvmLibraryProjecst.add(project)
                    else -> Unit // Do nothing
                }
            }

            project.configurations.all {
                if (name.toLowerCase(Locale.getDefault()).contains("test")) return@all
                getDependencies()
                    .withType(ProjectDependency::class.java)
                    .map { project ->
                        project.dependencyProject
                    }
                    .forEach { dependency ->
                        dependencyProjects.add(project)
                        dependencyProjects.add(dependency)
                        rootProjects.remove(dependency)

                        val graphKey = Pair(project, dependency)
                        val traits = dependencies.computeIfAbsent(graphKey) {
                            mutableListOf()
                        }

                        if (name.toLowerCase(Locale.getDefault()).endsWith("implementation")) {
                            traits.add("style=dotted")
                        }
                    }
            }
        }

        dependencyProjects.sortedBy { project ->
            project.path
        }.also { sortedDependencyProjects ->
            dependencyProjects.clear()
            dependencyProjects.addAll(sortedDependencyProjects)
        }

        dot.appendText("\n  # Projects\n\n")
        for (project in dependencyProjects) {
            val traits = mutableListOf<String>()

            when (project) {
                in androidApplication -> {
                    traits.add("shape=box")
                    traits.add("fillcolor=\"#baffc9\"")
                }
                in androidLibraryProjects -> traits.add("fillcolor=\"#81D4FA\"")
                in androidLintProjects -> traits.add("fillcolor=\"#c9baff\"")
                in jvmLibraryProjecst -> traits.add("fillcolor=\"#ffebba\"")
                else -> traits.add("fillcolor=\"#eeeeee\"")
            }

            dot.appendText("  \"${project.path}\" [${traits.joinToString(", ")}];\n")
        }

        dot.appendText("\n  {rank = same;")
        for (project in dependencyProjects) {
            if (rootProjects.contains(project)) {
                dot.appendText(" \"${project.path}\";")
            }
        }
        dot.appendText("}\n")

        dot.appendText("\n  # Dependencies\n\n")
        dependencies.forEach { (key, traits) ->
            dot.appendText("  \"${key.first.path}\" -> \"${key.second.path}\"")
            if (traits.isNotEmpty()) {
                dot.appendText(" [${traits.joinToString(", ")}]")
            }
            dot.appendText("\n")
        }
        dot.appendText("}\n")

        project.rootProject.exec {
            commandLine = listOf(
                "dot",
                "-Tpng",
                "-O",
                dot.path,
            )
        }
        dot.delete()
        println("Project module dependency graph created at ${dot.absolutePath}.png")
    }
}
