/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.sugar.annotation

import kotlin.reflect.KClass

// TODO: 문서 제공
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarName(val name: String = DEFAULT_NAME) {
    public companion object {
        public const val DEFAULT_NAME: String = "<<DEFAULT_NAME>>"
        public const val TOKEN_NAME: String = "<<SUGAR_TOKEN>>"
    }
}

// TODO: 문서 제공
@SugarCompilerApi
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarRefer(val fqn: String)

// TODO: 문서 제공
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarToken

// TODO: 문서 제공
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.BINARY)
public annotation class Imports(vararg val clazz: KClass<*>)
