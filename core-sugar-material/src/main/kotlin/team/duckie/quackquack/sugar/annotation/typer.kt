/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.sugar.annotation

// TODO: 문서 제공
@SugarCompilerApi
public fun <T> sugar(): T {
    @Suppress("UNCHECKED_CAST")
    return Any() as T
}
