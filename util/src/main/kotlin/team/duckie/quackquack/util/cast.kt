/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util

/** 주어진 값을 [T]로 unsafe casting 합니다. */
public inline fun <reified T : Any> Any?.cast(): T = this as T
