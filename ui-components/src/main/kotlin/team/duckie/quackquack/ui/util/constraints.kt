/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [constraints.kt] created by Ji Sungbin on 22. 9. 3. 오후 12:34
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Constraints

/**
 * 주어진 사이즈로 고정된 [Constraints] 를 생성합니다.
 *
 * 각각 주어진 사이즈로 min 과 max 를 모두 설정하는 [Constraints] 의
 * 유틸 함수 입니다. 만약 인자로 주어진 값이 null 이라면
 * min 과 max 에 맞는 원래의 값을 그대로 사용합니다.
 *
 * @param width [Constraints] 의 고정된 width 사이즈
 * @param height [Constraints] 의 고정된 height 사이즈
 * @return 인자로 주어진 고정된 사이즈를 갖는 [Constraints]
 * @see Constraints
 */
@Stable
internal fun Constraints.fixedCopy(
    width: Int? = null,
    height: Int? = null,
) = Constraints(
    minWidth = width ?: this.minWidth,
    maxWidth = width ?: this.maxWidth,
    minHeight = height ?: this.minHeight,
    maxHeight = height ?: this.maxHeight,
)
