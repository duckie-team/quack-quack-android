/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.casa.material

import kotlin.reflect.KClass
import org.intellij.lang.annotations.Language

// TODO: 문서화
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class Casa

// TODO: 문서화
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
public annotation class CasaValue(@Language("kotlin") val literal: String)

// TODO: 문서화
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
public annotation class CasaValues(vararg val providers: KClass<out CasaValueProvider<*>>)
