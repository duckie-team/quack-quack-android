/*
 * Designed and developed by Duckie Team, 2022
 *
 * [label.kt] created by doro n on 22. 9. 3. 오후 9:40
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.color.QuackColor.Companion.SkyBlueColor

private val QuackLabelShape = RoundedCornerShape(
    size = 11.dp,
)
private val QuackLabelPadding = PaddingValues(
    horizontal = 6.dp,
    vertical = 4.dp,
)

/**
 * QuackSimpleLabel 을 구현합니다.
 * active 의 값에 따라서 색깔이 달리집니다.
 *
 * @param text 라벨에 표시될 텍스트
 * @param active 버튼 활성화 여부. 배경 색상에 영향을 미칩니다.
 *
 */

@Composable
public fun QuackSimpleLabel(
    text: String,
    active: Boolean,
) {
    val backgroundColor = when (active) {
        true -> SkyBlueColor
        else -> QuackColor.Gray3
    }
    val textColor = when (active) {
        true -> QuackColor.White
        else -> QuackColor.Gray1
    }

    Box(
        modifier = Modifier
            .clip(
                shape = QuackLabelShape,
            )
            .background(
                color = backgroundColor.composeColor,
            )
            .padding(
                paddingValues = QuackLabelPadding,
            ),
    ) {
        QuackBody3(
            text = text,
            color = textColor,
        )
    }
}
