/*
 * Designed and developed by Duckie Team, 2022~2023
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
    override fun apply(
        target: Project,
    ) {
        target.tasks.register<DependencyGraphTask>(
            name = "dependencyGraph",
        )
    }
}

abstract class DependencyGraphTask : DefaultTask() {
    @TaskAction
    fun run() {
        val dot = File(project.rootProject.rootDir, "assets/project-dependency-graph/graph.dot")
        dot.parentFile.deleteRecursively()
        dot.parentFile.mkdirs()

        dot.appendText(
            text = """
                    |digraph {
                    |  graph [label="${project.rootProject.name}\n ",labelloc=t,fontsize=30,ranksep=1.4];
                    |  node [style=filled, fillcolor="#bbbbbb"];
                    |  rankdir=TB;
                    |
                   """.trimMargin(),
        )

        val rootProjects = mutableListOf<Project>()
        val queue = mutableListOf(project.rootProject)
        while (queue.isNotEmpty()) {
            val project = queue.removeAt(
                index = 0,
            )
            rootProjects.add(
                element = project,
            )
            queue.addAll(
                elements = project.childProjects.values,
            )
        }
        queue.add(
            element = project.rootProject,
        )

        val dependencyProjects = LinkedHashSet<Project>()
        val dependencies = LinkedHashMap<Pair<Project, Project>, MutableList<String>>()
        val androidApplication = mutableListOf<Project>()
        val androidLibraryProjects = mutableListOf<Project>()
        val androidLintProjects = mutableListOf<Project>()
        val jvmLibraryProjecst = mutableListOf<Project>()

        while (queue.isNotEmpty()) {
            val project = queue.removeAt(
                index = 0,
            )
            queue.addAll(
                elements = project.childProjects.values,
            )

            with(
                receiver = project.plugins,
            ) {
                when {
                    hasPlugin(PluginEnum.AndroidApplication) -> androidApplication.add(
                        element = project,
                    )
                    hasPlugin(PluginEnum.AndroidLibrary) -> androidLibraryProjects.add(
                        element = project,
                    )
                    hasPlugin(PluginEnum.AndroidLint) -> androidLintProjects.add(
                        element = project,
                    )
                    hasPlugin(PluginEnum.JavaLibrary) -> jvmLibraryProjecst.add(
                        element = project,
                    )
                    else -> Unit // Do nothing
                }
            }

            project.configurations.all {
                if (name.toLowerCase(Locale.getDefault()).contains("test")) {
                    return@all
                }
                getDependencies()
                    .withType(ProjectDependency::class.java)
                    .map(ProjectDependency::getDependencyProject)
                    .forEach { dependency ->
                        dependencyProjects.add(
                            element = project,
                        )
                        dependencyProjects.add(
                            element = dependency,
                        )
                        rootProjects.remove(
                            element = dependency,
                        )

                        val graphKey = project to dependency
                        val traits = dependencies.computeIfAbsent(graphKey) {
                            mutableListOf()
                        }

                        if (name.toLowerCase(Locale.getDefault()).endsWith("implementation")) {
                            traits.add(
                                element = "style=dotted",
                            )
                        }
                    }
            }
        }

        dependencyProjects.sortedBy(Project::getPath).also { sortedDependencyProjects ->
            dependencyProjects.clear()
            dependencyProjects.addAll(
                elements = sortedDependencyProjects,
            )
        }

        dot.appendText(
            text = "\n  # Projects\n\n",
        )
        for (project in dependencyProjects) {
            val traits = mutableListOf<String>()

            when (project) {
                in androidApplication -> {
                    traits.add(
                        element = "shape=box",
                    )
                    traits.add(
                        element = "fillcolor=\"#baffc9\"",
                    )
                }
                in androidLibraryProjects -> traits.add(
                    element = "fillcolor=\"#81D4FA\"",
                )
                in androidLintProjects -> traits.add(
                    element = "fillcolor=\"#c9baff\"",
                )
                in jvmLibraryProjecst -> traits.add(
                    element = "fillcolor=\"#ffebba\"",
                )
                else -> traits.add(
                    element = "fillcolor=\"#eeeeee\"",
                )
            }

            dot.appendText(
                text = "  \"${project.path}\" [${traits.joinToString(", ")}];\n",
            )
        }

        dot.appendText(
            text = "\n  {rank = same;",
        )
        for (project in dependencyProjects) {
            if (rootProjects.contains(project)) {
                dot.appendText(
                    text = " \"${project.path}\";",
                )
            }
        }
        dot.appendText(
            text = "}\n",
        )

        dot.appendText(
            text = "\n  # Dependencies\n\n",
        )
        dependencies.forEach { (key, traits) ->
            dot.appendText(
                text = "  \"${key.first.path}\" -> \"${key.second.path}\"",
            )
            if (traits.isNotEmpty()) {
                dot.appendText(
                    text = " [${traits.joinToString(", ")}]",
                )
            }
            dot.appendText(
                text = "\n",
            )
        }
        dot.appendText(
            text = "}\n",
        )

        project.rootProject.exec {
            commandLine = listOf(
                "dot",
                "-Tpng",
                "-O",
                dot.path,
            )
        }
        dot.delete()
        println(
            message = "Project module dependency graph created at ${dot.absolutePath}.png",
        )
    }
}
