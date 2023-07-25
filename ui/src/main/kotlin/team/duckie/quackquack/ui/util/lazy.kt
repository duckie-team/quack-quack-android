/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable

/** 동적으로 계산되는 값의 인스턴스를 보관하는 래퍼 클래스 */
@Stable
internal class LazyValue<T>(var value: T? = null)
