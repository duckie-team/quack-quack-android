/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [snapshot.kt] created by Ji Sungbin on 22. 8. 31. 오후 3:19
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow

/**
 * State 를 Flow 로 변환합니다.
 *
 * @receiver 기존 State<T> 객체
 * @return Flow 로 바뀐 State<T> 객체
 */
fun <T> State<T>.toFlow() = snapshotFlow { this }
