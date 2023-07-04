/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE")

package team.duckie.quackquack.util.compose.snapshot.test

import java.io.File
import org.junit.rules.TestWatcher
import org.junit.runner.Description

public const val BaseSnapshotPath: String = "src/test/snapshots"

public inline fun snapshotPath(
  domain: String,
  snapshotName: String = getCurrentMethodName(),
  isGif: Boolean = false,
): File =
  File("$BaseSnapshotPath/$domain/$snapshotName.${if (isGif) "gif" else "png"}")

// https://stackoverflow.com/a/32329165/14299073
public inline fun getCurrentMethodName(): String = Thread.currentThread().stackTrace[1].methodName

public class SnapshotPathGeneratorRule(private val domain: String) : TestWatcher() {
  init {
    File("$BaseSnapshotPath/$domain").mkdirs()
  }

  private var realtimeTestMethodName: String? = null

  override fun starting(description: Description) {
    realtimeTestMethodName = description.methodName
  }

  public operator fun invoke(isGif: Boolean = false): File =
    File("$BaseSnapshotPath/$domain/$realtimeTestMethodName.${if (isGif) "gif" else "png"}")
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class SnapshotName(public val name: String)
