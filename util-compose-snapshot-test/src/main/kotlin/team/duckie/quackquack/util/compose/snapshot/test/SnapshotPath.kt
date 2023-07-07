/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.compose.snapshot.test

import java.io.File
import org.junit.rules.TestWatcher
import org.junit.runner.Description

public const val BaseSnapshotPath: String = "src/test/snapshots"

public class SnapshotPathGeneratorRule(private val domain: String) : TestWatcher() {
  init {
    File("$BaseSnapshotPath/$domain").mkdirs()
  }

  private var realtimeTestMethodName: String? = null

  override fun starting(description: Description) {
    val snapshotName = description.getAnnotation(SnapshotName::class.java)?.name ?: description.methodName
    realtimeTestMethodName = snapshotName
  }

  public operator fun invoke(isGif: Boolean = false): File =
    File("$BaseSnapshotPath/$domain/${requireNotNull(realtimeTestMethodName)}.${if (isGif) "gif" else "png"}")
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class SnapshotName(public val name: String)
