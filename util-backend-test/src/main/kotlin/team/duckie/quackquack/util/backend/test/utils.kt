/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.test

import java.io.File

public fun File.findGeneratedFileOrNull(fileName: String): File? =
    walkTopDown()
        .find { it.name == fileName }

public fun String.removePackageLine(): String =
    split("\n")
        .toMutableList()
        .apply {
            removeIf { line ->
                line.startsWith("package ")
            }
        }
        .joinToString("\n")
