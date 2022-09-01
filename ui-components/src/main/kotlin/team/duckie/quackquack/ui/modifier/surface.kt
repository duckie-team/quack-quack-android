/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [surface.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:00
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import team.duckie.quackquack.common.runIf
import team.duckie.quackquack.ui.color.QuackColor

/**
 * 컴포넌트에 기본 테마를 입힙니다. 최하위 Modifier 이므로 인자들의 기본값이 정의되지 않았습니다.
 *
 * @param shape 컴포넌트의 모양
 * @param backgroundColor 컴포넌트의 색상
 * @param border 컴포넌트의 테두리. 테두리는 없을 수 있음으로 null 을 허용합니다.
 * @param elevation 컴포넌트의 그림자 크기
 */
@Stable
internal fun Modifier.surface(
    shape: Shape,
    backgroundColor: QuackColor,
    border: BorderStroke?,
    elevation: Dp,
) = this
    .shadow(
        elevation = elevation,
        shape = shape,
        clip = false,
    )
    .runIf(border != null) {
        border(
            border = border!!,
            shape = shape,
        )
    }
    .background(
        color = backgroundColor.value,
        shape = shape,
    )
    .clip(
        shape = shape,
    )
