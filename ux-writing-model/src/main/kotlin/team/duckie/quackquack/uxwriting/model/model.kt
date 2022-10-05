/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.uxwriting.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

/**
 * QuackText 로 부터 생성된 텍스트의 정보를 담고 있는 데이터 클래스 입니다.
 *
 * @param text 표시할 텍스트
 * @param offset 표시된 위치 (단위: [IntOffset])
 */
@Immutable
data class QuackWrting(
    val text: String,
    val offset: Offset,
) {
    /**
     * 주어진 [text] 가 올바른 writing 인지 확인합니다.
     *
     * @param rule 올바른 writing 규칙
     * @return 올바른 writing 인지 여부
     */
    fun check(
        rule: Triple<String, String, String>,
    ): Boolean {
        val (wrong, correct, because) = rule
        return false
    }
}
