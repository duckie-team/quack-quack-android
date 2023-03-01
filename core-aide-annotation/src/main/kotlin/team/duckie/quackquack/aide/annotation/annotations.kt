/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.aide.annotation

// TODO: 문서 제공
public enum class Type {
    Text,
}

// TODO: 문서 제공
public annotation class TypedComponent(val type: Type)

// TODO: 문서 제공
public annotation class TypedModifier(val type: Type)
