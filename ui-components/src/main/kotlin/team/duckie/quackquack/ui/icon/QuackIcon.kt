/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackIcon.kt] created by Ji Sungbin on 22. 8. 31. 오전 12:08
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import team.duckie.quackquack.ui.R

/**
 * 덕키에서 사용할 아이콘을 정의합니다. 추상화를 위해 아이콘 리소스를 바로 사용하는게 아닌
 * 이 클래스를 통해 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 아이콘 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * @param drawableId 아이콘 drawable 리소스 아이디
 */
@Immutable
@JvmInline
value class QuackIcon private constructor(
    @DrawableRes val drawableId: Int,
) {
    companion object {
        @Stable
        val Close = QuackIcon(
            drawableId = R.drawable.ic_close_24,
        )
    }
}
