/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.test.stub

import org.intellij.lang.annotations.Language

public object AideStub {
    @Language("kotlin")
    public const val Annotation: String = """
package team.duckie.quackquack.aide.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class DecorateModifier
    """
}
