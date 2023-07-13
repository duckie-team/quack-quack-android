/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.test.stub

import org.intellij.lang.annotations.Language

public object SugarStub {
  @Language("kotlin")
  public const val Typer: String =
    """
package team.duckie.quackquack.sugar.material

fun <T> sugar(): T {
  throw NotImplementedError()
}   
    """

  @Language("kotlin")
  public const val Annotations: String =
    """
package team.duckie.quackquack.sugar.material

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class SugarName(val name: String = DEFAULT_NAME) {
  companion object {
    const val PREFIX_NAME: String = "Quack"
    const val DEFAULT_NAME: String = "<<DEFAULT_NAME>>"
    const val TOKEN_NAME: String = "<<SUGAR_TOKEN>>"
  }
}

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.BINARY)
annotation class SugarToken

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class SugarRefer(val fqn: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class NoSugar

@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.BINARY)
annotation class GeneratedFile

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.BINARY)
annotation class Imports(vararg val clazz: KClass<*>)

@RequiresOptIn
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class SugarCompilerApi
    """
}
