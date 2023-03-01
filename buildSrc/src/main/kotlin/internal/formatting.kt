/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package internal

import org.gradle.api.Project

internal fun Project.installFormattingPluginIfNeeded() {
    if (pluginManager.hasPlugin(libs.findPlugin("kotlin-detekt").get().get().pluginId)) {
        dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get().get())
    }
}
