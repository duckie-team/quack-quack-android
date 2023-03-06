/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.annotation

import kotlin.reflect.KClass

// TODO: 문서 제공
public annotation class Sugar(val name: String = DEFAULT_NAME) {
    public companion object {
        public const val DEFAULT_NAME: String = "DefaultName"
    }
}

// TODO: 문서 제공
public annotation class SugarToken

// TODO: 문서 제공
public annotation class Import(val clazz: KClass<*>)
