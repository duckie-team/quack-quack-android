/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.test.stub

import org.intellij.lang.annotations.Language

public object CasaStub {
  @Language("kotlin")
  public const val Annotations: String = """
package team.duckie.quackquack.casa.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Casa

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class CasaValue(val literal: String)
    """
}
