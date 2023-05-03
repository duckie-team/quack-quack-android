/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE")

package team.duckie.quackquack.ui.util

import java.io.File

inline fun snapshotPath(domain: String): File {
    return File("src/test/snapshots/$domain/${getCurrentMethodName()}.png").also { file ->
        file.parentFile?.mkdirs()
    }
}

inline fun getCurrentMethodName(): String {
    // https://stackoverflow.com/a/32329165/14299073
    return Thread.currentThread().stackTrace[1].methodName
}
