/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.test.stub

import org.intellij.lang.annotations.Language

public object ComposeStub {
  @Language("kotlin")
  public const val Modifier: String = """
package androidx.compose.ui

interface Modifier { companion object : Modifier }
    """

  @Language("kotlin")
  public const val Composable: String = """
package androidx.compose.runtime

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER,
)
annotation class Composable
    """
}
