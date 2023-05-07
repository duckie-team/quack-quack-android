/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.util

import androidx.compose.ui.util.fastFirstOrNull

/**
 * 주어진 리스트에서 [T]로 casting 가능한 첫 번째 원소를 반환합니다.
 * 모든 원소가 [T]로 casting이 불가하면 null을 반환합니다.
 */
public inline fun <reified T> List<*>.fastFirstIsInstanceOrNull(): T? {
  return fastFirstOrNull { it is T } as? T
}
