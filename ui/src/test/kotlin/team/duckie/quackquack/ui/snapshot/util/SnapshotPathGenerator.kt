/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE")

package team.duckie.quackquack.ui.snapshot.util

import java.io.File

const val BaseSnapshotPath = "src/test/snapshots"

inline fun snapshotPath(domain: String, isGif: Boolean = false): File {
  return File("$BaseSnapshotPath/$domain/${getCurrentMethodName()}.${if (isGif) "gif" else "png"}")
    .also { file -> file.parentFile?.mkdirs() }
}

inline fun getCurrentMethodName(): String {
  // https://stackoverflow.com/a/32329165/14299073
  return Thread.currentThread().stackTrace[1].methodName
}
